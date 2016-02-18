package com.knn.apitest.network;

import android.util.Log;

import com.knn.apitest.events.EventAuctions;
import com.knn.apitest.events.EventWithList;
import com.knn.apitest.model.Auction;
import com.knn.apitest.network.api.IApiService;
import com.knn.apitest.network.response.IResponseWithList;

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
    protected final static String TAG = "by.htp.maesens";
    private static ServiceHelper ourInstance = new ServiceHelper();
    private Retrofit mRetrofit;


    public static ServiceHelper getInstance() {
        return ourInstance;
    }

    private ServiceHelper() {
    }

    private Retrofit getRetrofit() {
        if (mRetrofit != null) return mRetrofit;

        mRetrofit = new Retrofit.Builder()
                //.baseUrl("https://maesens.by/")
                .baseUrl("https://staging.tvojmir.com/")
                .client(TrustedHttpClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }

    public <RESPONSE, EVENT> void getDate(final Class<EVENT> eventClass, Class<RESPONSE> responseClass, String[] params) throws Exception {
        IApiService api = getRetrofit().create(IApiService.class);
        Call<RESPONSE> call = (Call<RESPONSE>) getCallApi(eventClass, api, params);

        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(Call<RESPONSE> call, Response<RESPONSE> response) {

                RESPONSE resp = response.body();
                Log.d("ServiceHelper", "onResponse  " + resp);
                Log.d("ServiceHelper", "onResponse  resp instanceof IResponseWithList= " + (resp instanceof IResponseWithList));

                if (resp instanceof IResponseWithList) {
                    Log.d("ServiceHelper", "onResponse  resp.getClass()= " + resp.getClass());
                    executeActionWithList(((IResponseWithList) resp).getResults(), response.isSuccess());
                } else if (resp instanceof List) {
                    Log.d("ServiceHelper", "onResponse  resp.getClass()= " + resp.getClass());
                    executeActionWithList((List<Auction>) resp, response.isSuccess());
                }

            }

            @Override
            public void onFailure(Call<RESPONSE> call, Throwable t) {
                Log.d("ServiceHelper", "onFailure");
                Log.d("ServiceHelper", t.getMessage());
                EventAuctions event = new EventAuctions();
                event.setIsSuccess(false);
                EventBus.getDefault().post(event);
            }

            private void executeActionWithList(List<Auction> list, boolean responseSuccess) {

                Log.d("ServiceHelper", "executeActionWithList  " + list.toString());
                Log.d(TAG, "executeActionWithList   " + list.size());
                if (responseSuccess && list != null) {
                    Log.d("ServiceHelper", "isSuccess");
                    EventWithList event = new EventWithList();
                    event.setIsSuccess(true);
                    event.setAuctionList(list);
                    EventBus.getDefault().post((EVENT) event);
                    Log.d(TAG, "executeActionWithList  " + ((EVENT) event).getClass());
                } else {
                    Log.d("ServiceHelper", "NOT isSuccess");
                    EventWithList event = new EventWithList();
                    event.setIsSuccess(false);
                    EventBus.getDefault().post((EVENT) event);
                }

            }

        });


    }


    public Call getCallApi(Class eventClass, IApiService api, String[] params) throws Exception {
        Call call = null;
        Log.d(TAG, "getCallApi  " + eventClass.getSimpleName());
        switch (eventClass.getSimpleName()) {

            case "EventAuctionsFavourite": {
                call = api.getAuctionsFavourite(getAuthToken());
                break;
            }
            case "EventAuctionsPopular": {
                call = api.getAuctionsPopular();
                break;
            }
            case "EventAuctions": {
                call = api.getAuctions(params[0]);
                break;
            }

            default:
                throw new ClassNotFoundException("Event do not define ");
        }
        return call;
    }


//    public void getAuctionsPopular() {
//        IApiService api = getRetrofit().create(IApiService.class);
//        Call<List<Auction>> call = api.getAuctionsPopular();
//
//        call.enqueue(new Callback<List<Auction>>() {
//            @Override
//            public void onResponse(Call<List<Auction>> call, Response<List<Auction>> response) {
//                List<Auction> list = response.body();
//
//                if (response.isSuccess() && list != null) {
//                    Log.d("ServiceHelper", "isSuccess");
//                    EventAuctions event = new EventAuctions();
//                    event.setIsSuccess(true);
//                    event.setAuctionList(list);
//                    EventBus.getDefault().post(event);
//                } else {
//                    Log.d("ServiceHelper", "NOT isSuccess");
//                    EventAuctions event = new EventAuctions();
//                    event.setIsSuccess(false);
//                    EventBus.getDefault().post(event);
//                }
//            }

//            @Override
//            public void onFailure(Call<List<Auction>> call, Throwable t) {
//                Log.d("ServiceHelper", "onFailure");
//                Log.d("ServiceHelper", t.getMessage());
//                EventAuctions event = new EventAuctions();
//                event.setIsSuccess(false);
//                EventBus.getDefault().post(event);
//            }
//        });
//    }

//    public void getAuctionsFavourite() {
//        IApiService api = getRetrofit().create(IApiService.class);
//        Call<ResponseAuctions> call = api.getAuctionsFavourite(getAuthToken());
//
//        call.enqueue(new Callback<ResponseAuctions>() {
//            @Override
//            public void onResponse(Call<ResponseAuctions> call, Response<ResponseAuctions> response) {
//                ResponseAuctions responseFavourite = response.body();
//                List<Auction> list = responseFavourite.getResults();
//                if (response.isSuccess() && list != null) {
//                    Log.d("ServiceHelper", "isSuccess");
//                    EventAuctions event = new EventAuctions();
//                    event.setIsSuccess(true);
//                    event.setAuctionList(list);
//                    EventBus.getDefault().post(event);
//                } else {
//                    Log.d("ServiceHelper", "NOT isSuccess");
//                    EventAuctions event = new EventAuctions();
//                    event.setIsSuccess(false);
//                    EventBus.getDefault().post(event);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseAuctions> call, Throwable t) {
//                Log.d("ServiceHelper", "onFailure");
//                Log.d("ServiceHelper", t.getMessage());
//                EventAuctions event = new EventAuctions();
//                event.setIsSuccess(false);
//                EventBus.getDefault().post(event);
//            }
//        });
//    }

//    public void getAuctions(String pageNumber) {
//        IApiService api = getRetrofit().create(IApiService.class);
//        Call<ResponseAuctions> call = api.getAuctions(pageNumber);
//
//        call.enqueue(new Callback<ResponseAuctions>() {
//            @Override
//            public void onResponse(Call<ResponseAuctions> call, Response<ResponseAuctions> response) {
//                ResponseAuctions responseAll = response.body();
//                if (responseAll != null) {
//                    List<Auction> list = responseAll.getResults();
//
//                    if (response.isSuccess() && list != null) {
//                        Log.d("ServiceHelper", "isSuccess");
//                        EventAuctions event = new EventAuctions();
//                        event.setIsSuccess(true);
//                        event.setAuctionList(list);
//                        EventBus.getDefault().post(event);
//                    } else {
//                        Log.d("ServiceHelper", "NOT isSuccess");
//                        EventAuctions event = new EventAuctions();
//                        event.setIsSuccess(false);
//                        EventBus.getDefault().post(event);
//                    }
//                }
//
//            }

//            @Override
//            public void onFailure(Call<ResponseAuctions> call, Throwable t) {
//                Log.d("ServiceHelper", "onFailure");
//                Log.d("ServiceHelper", t.getMessage());
//                EventAuctions event = new EventAuctions();
//                event.setIsSuccess(false);
//                EventBus.getDefault().post(event);
//            }
//        });
//    }

    private String getAuthToken() {
        return "Token 7e2ffdf3bc375a13a4460747ac254e59ab26328a";
    }

}
