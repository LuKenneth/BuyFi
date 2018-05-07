package com.example.luke.buyfi;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

/**
 * Created by lpatterson18 on 4/23/2018.
 * Not using this class currently.
 */

public class NetworkTransactionHandler implements Transaction.Handler {

    private NetworkListing networkList;
    private MainActivity main;

    public NetworkTransactionHandler(NetworkListing networkList) {
        super();
        this.networkList = networkList;
    }

    @Override
    public Transaction.Result doTransaction(MutableData mutableData) {
        NetworkListing network = mutableData.child(networkList.getNetworkID()).getValue(NetworkListing.class);

        Log.v("NetworkTransaction:", "doTransaction called");
        return Transaction.success(mutableData);
    }

    @Override
    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
        //Now create a listing if one does not exist
        Log.v("NetworkTransaction:", "onComplete called");
        if(!dataSnapshot.child(networkList.getNetworkID()).exists()) {
        }
        else {

        }
    }


}
