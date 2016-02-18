package com.knn.apitest.fragments;

import android.util.Log;

import com.knn.apitest.events.EventAuctionsPopular;
import com.knn.apitest.network.ServiceHelper;
import com.knn.apitest.network.response.ResponsePopularAuctions;

/**
 * Created by Никита on 14.02.2016.
 */

public class FragmentAuctionsPopular extends BaseRecyclerFragment <EventAuctionsPopular>{

//    @Override
//    public void callREST(int nextPage) {
//        Log.d("FragmentAuctionsPopular", "callREST");
//        try {
//            ServiceHelper.getInstance().getDate(EventAuctionsPopular.class,null);//getAuctionsPopular();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    @Override
    public void controlDisable() {

    }

    @Override
    public void controlEnable() {

    }

    @Override
    public void sendRequestFromHelper(String[] paramsForAPI) {
        Log.d("FragmentAuctionsPopular", "sendRequest");
        try {
            ServiceHelper.getInstance().getDate(EventAuctionsPopular.class, ResponsePopularAuctions.class,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*public class FragmentAuctionsPopular extends Fragment {

    private AuctionAdapter mAuctionAdapter;
    private List<Auction> mAuctionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        Button btnWatchFavourite = (Button) view.findViewById(R.id.btnWatchFavourite);
        btnWatchFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setFragment(new FragmentAuctionsFavourite());
            }
        });

        Button btnGetPopular = (Button) view.findViewById(R.id.btnGetPopular);
        btnGetPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceHelper.getInstance().getAuctionsPopular();
            }
        });

        mAuctionList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerPopular);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAuctionAdapter = new AuctionAdapter(mAuctionList);
        recyclerView.setAdapter(mAuctionAdapter);

        return view;
    }


    @Subscribe
    public void onResponseAuctionsPopular(EventAuctionsPopular event) {
        if (event.isSuccess()) {
            Toast.makeText(getActivity(), "success Popular", Toast.LENGTH_SHORT).show();
            mAuctionAdapter.setList(event.getList());
            mAuctionAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "ERROR REST", Toast.LENGTH_SHORT).show();
        }
    }

    private class AuctionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private Auction mAuction;

        public AuctionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.auctionImageView);
        }

        public void bindAuction(Auction auction){
            mAuction = auction;
            Picasso.with(getActivity())
                    .load(mAuction.getSmall_image())
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mAuction.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private class AuctionAdapter extends RecyclerView.Adapter<AuctionHolder> {

        private List<Auction> mList;

        public void setList(List<Auction> list) {
            mList = list;
        }

        public AuctionAdapter(List<Auction> list) {
            mList = list;
        }

        @Override
        public AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.item_auction, parent, false);
            return new AuctionHolder(view);
        }

        @Override
        public void onBindViewHolder(AuctionHolder holder, int position) {
            Auction auction = mList.get(position);
            holder.bindAuction(auction);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}*/
