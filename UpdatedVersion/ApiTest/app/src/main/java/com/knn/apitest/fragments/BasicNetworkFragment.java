package com.knn.apitest.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knn.apitest.IControlEnable;
import com.knn.apitest.events.IEventSuccessCheckable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by USER on 16.02.2016.
 */
public abstract class BasicNetworkFragment<EVENT extends IEventSuccessCheckable> extends Fragment {

    private IControlEnable mIControlEnableActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        controlDisable();
        if (mIControlEnableActivity !=null){
            mIControlEnableActivity.controlDisable();
        }
        if (savedInstanceState == null) {
            init();
        } else 
        {
            loadDateFromBundle(savedInstanceState);
        }
    }

    protected abstract void loadDateFromBundle(Bundle savedInstanceState);

    @Override
    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IControlEnable){
            mIControlEnableActivity = (IControlEnable)activity;
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResponse(EVENT event) {
        if (event.isSuccess()) {
            makeActionsOnSuccess(event);
        } else {
            makeActionsOnFail(event);
        }
        controlEnable();
        if (mIControlEnableActivity !=null){
            mIControlEnableActivity.controlEnable();
        }
    }

    protected void init() {
        sendRequestFromHelper();
    }

    abstract public void makeActionsOnFail(EVENT event);

    abstract public void makeActionsOnSuccess(EVENT event);

    abstract public void controlDisable();

    abstract public void controlEnable();

    abstract public void sendRequestFromHelper(String[] paramsForAPI);
    public void sendRequestFromHelper(){
        sendRequestFromHelper(null);
    }

}
