package com.example.luke.buyfi;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Luke on 5/3/2018.
 * This interface is for use with the command pattern to wrap
 * an object and its showNetworks function to display the networks
 * within range, AFTER the call to firebase has finished grabbing any
 * possible information for each network.
 *
 * You can find usage inside FirebaseManager.java
 * BuyFiListFragment implements this interface.
 */

public interface Callable {

    public void showNetworks(ArrayList<NetworkListing> networkListings);
    public Context getCallableContext();
}
