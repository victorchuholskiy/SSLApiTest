package com.knn.apitest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.knn.apitest.R;
import com.knn.apitest.loaders.PicassoLoader;
import com.knn.apitest.model.Auction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by knn on 16.02.2016.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerAdapter.AuctionHolder> {

    private final static String TAG = "by.htp.maesens";

    private List<Auction> mList;
    private Context mContext;

    public void setList(List<Auction> list) {
        mList = list;
    }

    public BaseRecyclerAdapter(List<Auction> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void addData(List<Auction> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public AuctionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
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

    public void reset(){
        mList = new ArrayList<>();
    }

    public class AuctionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;
        private Auction mAuction;

        public AuctionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.auctionImageView);
        }

        public void bindAuction(Auction auction){
            mAuction = auction;
            PicassoLoader.getInstance(mContext)
                    .load(mAuction.getSmall_image())
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, mAuction.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

}
