package com.knn.apitest.events;

import com.knn.apitest.model.Auction;

import java.util.List;

/**
 * Created by Никита on 14.02.2016.
 */
public class EventAuctionsFavourite {
    private boolean isSuccess;
    private List<Auction> list;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public List<Auction> getList() {
        return list;
    }

    public void setList(List<Auction> list) {
        this.list = list;
    }

}
