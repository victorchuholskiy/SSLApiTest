package com.knn.apitest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.knn.apitest.MainActivity;
import com.knn.apitest.R;
import com.knn.apitest.events.EventAuctionsFavourite;
import com.knn.apitest.events.EventAuctionsPopular;
import com.knn.apitest.network.ServiceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Никита on 14.02.2016.
 */
public class FragmentAuctionsFavourite extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        Button btnWatchPopular = (Button)view.findViewById(R.id.btnWatchPopular);
        btnWatchPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFragment(new FragmentAuctionsPopular());
            }
        });

        Button btnGetFavourite = (Button) view.findViewById(R.id.btnGetFavourite);
        btnGetFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceHelper.getInstance().getAuctionsFavourite();
            }
        });

        return view;
    }

    @Subscribe
    public void onResponseAuctionsPopular(EventAuctionsFavourite event){
        if(!event.isSuccess()){
            Toast.makeText(getActivity(), "ERROR REST", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "success Favourite", Toast.LENGTH_SHORT).show();
            Log.d("aaa", "размер списка = " + event.getList().size());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
