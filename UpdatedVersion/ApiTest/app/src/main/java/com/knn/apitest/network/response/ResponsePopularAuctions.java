package com.knn.apitest.network.response;

import com.knn.apitest.model.Auction;

import java.util.List;

/**
 * Created by USER on 18.02.2016.
 */
public class ResponsePopularAuctions  implements IResponseWithList<Auction> {

   private List<Auction> results;

    public ResponsePopularAuctions(List<Auction> results) {
        this.results = results;
    }

    @Override
    public List<Auction> getResults() {
        return results;
    }

   public void setResults(List<Auction> results) {
        this.results = results;
    }
}
