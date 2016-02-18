package com.knn.apitest.network;

import android.util.Log;

import com.knn.apitest.events.EventAuctionsFavourite;
import com.knn.apitest.events.EventAuctionsPopular;
import com.knn.apitest.model.Auction;
import com.knn.apitest.network.api.IApiService;
import com.knn.apitest.network.response.ResponseFavouriteAuctions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Никита on 13.02.2016.
 */
public class ServiceHelper {

    private static ServiceHelper ourInstance = new ServiceHelper();
    private Retrofit mRetrofit;

    public static ServiceHelper getInstance(){
        return ourInstance;
    }

    private ServiceHelper(){}

    private Retrofit getRetrofit(){
        if(mRetrofit != null) return mRetrofit;

        mRetrofit = new Retrofit.Builder()
                //.baseUrl("https://maesens.by/")
                .baseUrl("https://staging.tvojmir.com/")
                .client(HttpClientHelper.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }

    public void getAuctionsPopular(){
        IApiService api = getRetrofit().create(IApiService.class);
        Call<List<Auction>> call = api.getAuctionsPopular();

        call.enqueue(new Callback<List<Auction>>() {
            @Override
            public void onResponse(Call<List<Auction>> call, Response<List<Auction>> response) {
                List<Auction> list = response.body();

                if(response.isSuccess() && list != null){
                    Log.d("ServiceHelper", "isSuccess");
                    EventAuctionsPopular event = new EventAuctionsPopular();
                    event.setIsSuccess(true);
                    event.setList(list);
                    EventBus.getDefault().post(event);
                } else {
                    Log.d("ServiceHelper", "NOT isSuccess");
                    EventAuctionsPopular event = new EventAuctionsPopular();
                    event.setIsSuccess(false);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<List<Auction>> call, Throwable t) {
                Log.d("ServiceHelper", "onFailure");
                Log.d("ServiceHelper", t.getMessage());
                EventAuctionsPopular event = new EventAuctionsPopular();
                event.setIsSuccess(false);
                EventBus.getDefault().post(event);
            }
        });
    }

    public void getAuctionsFavourite(){
        IApiService api = getRetrofit().create(IApiService.class);
        Call<ResponseFavouriteAuctions> call = api.getAuctionsFavourite(getAuthToken());

        call.enqueue(new Callback<ResponseFavouriteAuctions>() {
            @Override
            public void onResponse(Call<ResponseFavouriteAuctions> call, Response<ResponseFavouriteAuctions> response) {
                ResponseFavouriteAuctions responseFavourite = response.body();
                List<Auction> list = responseFavourite.getResults();
                if(response.isSuccess() && list != null){
                    Log.d("ServiceHelper", "isSuccess");
                    EventAuctionsFavourite event = new EventAuctionsFavourite();
                    event.setIsSuccess(true);
                    event.setList(list);
                    EventBus.getDefault().post(event);
                } else {
                    Log.d("ServiceHelper", "NOT isSuccess");
                    EventAuctionsFavourite event = new EventAuctionsFavourite();
                    event.setIsSuccess(false);
                    EventBus.getDefault().post(event);
                }
            }

            @Override
            public void onFailure(Call<ResponseFavouriteAuctions> call, Throwable t) {
                Log.d("ServiceHelper", "onFailure");
                Log.d("ServiceHelper", t.getMessage());
                EventAuctionsFavourite event = new EventAuctionsFavourite();
                event.setIsSuccess(false);
                EventBus.getDefault().post(event);
            }
        });
    }

    private String getAuthToken(){
        return "Token 8e7b6321f804849266ef98ac63cb366d769330c5";
        //return "Token 87a80eaa14706cdad01684ba0fb3d80b08cb0bca";
    }

}
