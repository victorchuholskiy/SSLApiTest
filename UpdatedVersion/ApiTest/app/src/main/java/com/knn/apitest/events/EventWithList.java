package com.knn.apitest.events;

import com.knn.apitest.model.Auction;

import java.util.List;

/**
 * Created by Никита on 16.02.2016.
 */
public class EventWithList implements IEventSuccessCheckable {
    private List<Auction> mAuctionList;
    private boolean isSuccess = true;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<Auction> getAuctionList() {
        return mAuctionList;
    }

    public void setAuctionList(List<Auction> auctionList) {
        mAuctionList = auctionList;
    }
}
