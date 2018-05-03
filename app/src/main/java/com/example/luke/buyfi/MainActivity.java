package com.example.luke.buyfi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private BuyFiAdapter adapter;
    private NetworkManager nm;
    private ArrayList<BuyFiNetwork> networks;
    private ArrayList<NetworkListing> networkListings;
    private NetworkTransactionHandler nth;
    private FirebaseManager fm;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        nm = NetworkManager.getSharedInstance(this);
        fm = new FirebaseManager(this);
        networkListings = new ArrayList<NetworkListing>();
        list = (ListView)findViewById(R.id.list);

        //pull to refresh
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetworks();
            }
        });

        loadNetworks();

        //Logs
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("OnClick", "clicked: " + view.toString());
            }
        });
        Log.v("NetworkList", "List of Networks: \n" + networks);

    }

    public void loadNetworks() {
        nm.obtainNetworks();
        networks = nm.getNetworks();
        networkListings.clear();
        for(int i = 0; i < networks.size(); i++) {
            //default when creating a new network list
            NetworkListing networkList = new NetworkListing(networks.get(i), false, "N/A", "Phone number");
            networkListings.add(networkList);
//            nth = new NetworkTransactionHandler(networkList);
//            fm.getReference().runTransaction(nth);
        }
        fm.setNetworkListing(networkListings);
        fm.getNetworks();
    }

    public void showNetworks(ArrayList<NetworkListing> networkListings) {
        this.networkListings = networkListings;
        adapter = new BuyFiAdapter(networkListings, getApplicationContext());
        list.setAdapter(adapter);
        refresh.setRefreshing(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(getApplicationContext(), "Location is required to find nearby wireless networks. Please grant location access.", Toast.LENGTH_LONG).show();
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 87);
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 87);
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
        Log.v("PermissionsResult: ", grantResults.toString());
    }



}
