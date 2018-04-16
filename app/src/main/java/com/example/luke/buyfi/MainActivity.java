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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private BuyFiAdapter adapter;
    private NetworkManager nm;
    private ArrayList<BuyFiNetwork> networks;
    private ArrayList<NetworkListing> networkListings;
    private FirebaseManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = new FirebaseManager();
        nm = NetworkManager.getSharedInstance();
        nm.obtainNetworks(this);
        networks = nm.getNetworks();
        list = (ListView)findViewById(R.id.list);
        networkListings = new ArrayList<NetworkListing>();
        for(int i = 0; i < networks.size(); i++) {
            NetworkListing networkList = new NetworkListing(networks.get(i), i%2==0, "$50", "(216)-225-4193");
            networkListings.add(networkList);
            fm.writeNetwork(networkList);
        }
        adapter = new BuyFiAdapter(networkListings, getApplicationContext());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("OnClick", "clicked: " + view.toString());
            }
        });


        Log.v("NetworkList", "List of Networks: \n" + networks);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 87);
            }
        }
    }



}
