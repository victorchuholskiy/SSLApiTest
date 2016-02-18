package com.knn.apitest.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.knn.apitest.R;
import com.knn.apitest.adapters.BaseRecyclerAdapter;
import com.knn.apitest.events.EventWithList;
import com.knn.apitest.model.Auction;

import java.util.ArrayList;

/**
 * Created by knn on 16.02.2016.
 */
public abstract class BaseRecyclerFragment<EVENT_WITH_LIST extends EventWithList> extends BasicNetworkFragment<EVENT_WITH_LIST> {

    protected final static String TAG = "by.htp.maesens";

    private int nextPage = 1;
    private boolean pagination = false;
    private FragmentSetter mParentActivity;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected BaseRecyclerAdapter mAdapter;
    protected EndlessScrollListener mScrollListener;
    protected RecyclerView mRecyclerView;
    protected int mScrollPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        //TODO возможно стоит тоже это добавить?
        /*if (getArguments().containsKey(ARG_HEADER_LAYOUT_ID)) {
            inflater.inflate(getArguments().getInt(ARG_HEADER_LAYOUT_ID), mHeaderContainer, true);
        }*/

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new BaseRecyclerAdapter(new ArrayList<Auction>(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mScrollListener = new EndlessScrollListener((GridLayoutManager) mRecyclerView.getLayoutManager());
        mRecyclerView.addOnScrollListener(mScrollListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.orange, R.color.green, R.color.blue);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void loadDateFromBundle(Bundle savedInstanceState) {
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mScrollPosition);
    }

    //TODO найти не deprecated вариант
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentSetter) {
            mParentActivity = (FragmentSetter) getActivity();
        } else {
            Log.w(TAG, "activity " + activity + " can not set another fragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapter = null;
        mParentActivity = null;
        mScrollPosition = ((GridLayoutManager) mRecyclerView.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
    }

    @Override
    protected void init() {
        mAdapter.reset();
        setNextPage(1);
        fetchData();
    }

    public void callREST(int nextPage)

    {
        String[] paramsForAPI = {String.valueOf(nextPage)};
        sendRequestFromHelper(paramsForAPI);
    }

//    @Override
//    public void sendRequestFromHelper(String[] paramsForAPI) {
//        try {
//            ServiceHelper.getInstance().getDate((new EVENT_WITH_LIST).getClass() ,paramsForAPI);//getAuctionsFavourite();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    protected boolean fetchData() {
        if (!isPagination() && getNextPage() > 1) {
            return false;
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            callREST(nextPage);
        }
        return true;
    }

    @Override
    public void makeActionsOnFail(EVENT_WITH_LIST eventWithList) {
        Log.d(TAG, "ERROR");
        Toast.makeText((Context) mParentActivity, "Response error", Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void makeActionsOnSuccess(EVENT_WITH_LIST eventWithList) {
        Log.d(TAG, "success. list size = " + eventWithList.getAuctionList().size());
        mAdapter.addData(eventWithList.getAuctionList());
        nextPage++;
        if (mSwipeRefreshLayout.isRefreshing()) {
            mScrollListener.reset();
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void onAuctionsResponse(EventAuctions event) {
//        if (event.isSuccess()) {
//            Log.d(TAG, "success. list size = " + event.getAuctionList().size());
//            mAdapter.addData(event.getAuctionList());
//            nextPage++;
//            if (mSwipeRefreshLayout.isRefreshing()) {
//                mScrollListener.reset();
//                mRecyclerView.addOnScrollListener(mScrollListener);
//            }
//        } else {
//            Log.d(TAG, "ERROR");
//            Toast.makeText((Context) mParentActivity, "Response error", Toast.LENGTH_SHORT).show();
//        }
//        mSwipeRefreshLayout.setRefreshing(false);
//    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    //host activity will change fragments if necessary
    public interface FragmentSetter {
        void setFragment(Fragment fragment);
    }

    /**
     * Class makes partial load when current scroll position close to end
     */
    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private int previousTotal;
        private int firstVisibleItem;
        private int visibleItemCount;
        private int totalItemCount;
        private final int visibleThreshold = 25;

        private boolean loading = true;

        private final GridLayoutManager mGridLayoutManager;

        public EndlessScrollListener(GridLayoutManager gridLayoutManager) {
            this.mGridLayoutManager = gridLayoutManager;
        }

        public void reset() {
            previousTotal = firstVisibleItem = visibleItemCount = totalItemCount = 0;
            loading = true;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mGridLayoutManager.getItemCount();
            firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                loading = true;
                loadMore();
            }
        }

        public void loadMore() {
            Log.d(TAG, "loadMore");
            fetchData();
        }
    }

    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mRecyclerView.removeOnScrollListener(mScrollListener);
            init();
        }
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public int getNextPage() {
        return nextPage;
    }
}
