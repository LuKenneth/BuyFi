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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        loadBuyFiListFragment();

        initializeObjects();

        addPullToRefresh();

        loadNetworks();

        googleSignIn();
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
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

    public void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

       GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void loadBuyFiListFragment() {
        loadFragment(new BuyFiListFragment());
    }

    public void initializeObjects() {
        nm = NetworkManager.getSharedInstance(this);
        fm = new FirebaseManager(this);
        networkListings = new ArrayList<NetworkListing>();
        list = (ListView)findViewById(R.id.list);
    }

    public void addPullToRefresh() {
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetworks();
            }
        });
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

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }


    /*From previous tutorial*/
    public void signIn() {
        fm.signIn(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FirebaseManager.RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                Log.v("FirebaseUI response: ", response.getError().getMessage());
            }
        }
    }

}
