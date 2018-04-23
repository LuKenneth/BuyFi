package com.example.luke.buyfi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

/**
 * Created by lpatterson18 on 4/23/2018.
 */

public class NetworkTransactionHandler implements Transaction.Handler {

    private NetworkListing networkList;
    private String networkID;

    public NetworkTransactionHandler(NetworkListing networkList, String networkID) {
        super();
        this.networkList = networkList;
        this.networkID = networkID;
    }

    @Override
    public Transaction.Result doTransaction(MutableData mutableData) {
        NetworkListing network = mutableData.child(networkID).getValue(NetworkListing.class);
        if(network == null) {
            return Transaction.success(mutableData);
        }
        
        return null;
    }

    @Override
    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

    }
}
