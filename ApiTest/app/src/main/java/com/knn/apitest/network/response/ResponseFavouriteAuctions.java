package com.knn.apitest.network.response;

import com.knn.apitest.model.Auction;

import java.util.List;

/**
 * Created by Никита on 14.02.2016.
 */
public class ResponseFavouriteAuctions {
    private int count;
    private String next;
    private String previous;
    private List<Auction> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Auction> getResults() {
        return results;
    }

    public void setResults(List<Auction> results) {
        this.results = results;
    }
}
