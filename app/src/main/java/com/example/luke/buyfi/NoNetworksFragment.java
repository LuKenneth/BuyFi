package com.example.luke.buyfi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Luke on 5/3/2018.
 * Shows when there is no networks within range.
 */

public class NoNetworksFragment extends Fragment {
    private NetworkManager nm;
    private SwipeRefreshLayout refresh;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.no_networks_fragment, container, false);

        nm = NetworkManager.getSharedInstance(getActivity().getApplicationContext());
        addPullToRefresh(view);
        return view;
    }



    public void addPullToRefresh(View v) {
        refresh = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity main = (MainActivity) getActivity();
                nm.obtainNetworks();
                if(!nm.getNetworks().isEmpty()) {
                    main.loadBuyFiListFragment();
                }
                else {
                    stopRefreshing();
                }
            }
        });
    }

    public void stopRefreshing() {
        refresh.setRefreshing(false);
    }

}
