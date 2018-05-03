package com.example.luke.buyfi;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Luke on 5/3/2018.
 */

public interface Callable {

    public void showNetworks(ArrayList<NetworkListing> networkListings);
    public Context getCallableContext();
}
