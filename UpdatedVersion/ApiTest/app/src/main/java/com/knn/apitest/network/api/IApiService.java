package com.knn.apitest.network.api;

import com.knn.apitest.model.Auction;
import com.knn.apitest.network.response.ResponseAuctions;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Никита on 13.02.2016.
 */
public interface IApiService {
    @GET("api/auctions/popular/")
    Call<List<Auction>> getAuctionsPopular();

    /*@GET("api/auctions/popular/")
    Call<List<Auction>> getAuctionsPopular();*/

    @GET("api/auctions/favourite/")
    Call<ResponseAuctions> getAuctionsFavourite(@Header("Authorization") String authorization);

    @GET("api/auctions/")
    Call<ResponseAuctions> getAuctions(@Query("page") String pageNumber);

}
