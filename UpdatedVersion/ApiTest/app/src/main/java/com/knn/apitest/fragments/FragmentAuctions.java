package com.knn.apitest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.knn.apitest.events.EventAuctions;
import com.knn.apitest.network.ServiceHelper;
import com.knn.apitest.network.response.ResponseAuctions;

/**
 * Created by Никита on 16.02.2016.
 */
public class FragmentAuctions extends BaseRecyclerFragment<EventAuctions> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPagination(true);
    }



    @Override
    public void controlDisable() {

    }

    @Override
    public void controlEnable() {

    }

    @Override
    public void sendRequestFromHelper(String[] paramsForAPI) {
        try {
            ServiceHelper.getInstance().getDate(EventAuctions.class, ResponseAuctions.class,paramsForAPI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
