package com.example.luke.buyfi;

import android.net.Network;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Luke on 4/15/2018.
 */

public class FirebaseManager {

    private DatabaseReference ref;
    private final MainActivity caller;
    private ArrayList<NetworkListing> networkLists;
    private NetworkManager nm;

    public FirebaseManager(final MainActivity caller) {
        ref = FirebaseDatabase.getInstance().getReference();
        this.caller = caller;
        nm = NetworkManager.getSharedInstance();
    }

    public void getNetworks() {

        ValueEventListener networkListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<NetworkListing> newListing = new ArrayList<NetworkListing>();
                updateLocalNetworks();
                ArrayList<NetworkListing> removeValues = new ArrayList<NetworkListing>();
                for (NetworkListing networkList : networkLists) {
                    boolean match = false;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        NetworkListing existingNetworkList = snapshot.getValue(NetworkListing.class);
                        if (networkList.getNetworkID().equals(existingNetworkList.getNetworkID())) {
                            newListing.add(existingNetworkList);
                            removeValues.add(networkList);
                            match = true;
                            break;
                        }
                    }
                    if(!match) {
                        ref.child(networkList.getNetworkID()).setValue(networkList);
                    }
                }
                networkLists.removeAll(removeValues);
                newListing.addAll(networkLists);
                caller.showNetworks(newListing);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("FirebaseManager.java", "onCancelled: Message: " + databaseError.getMessage() + " \n Details: " +
                        databaseError.getDetails());
            }
        };
        ref.addValueEventListener(networkListener);
    }

    public DatabaseReference getReference() {
        return this.ref;
    }

    public void setNetworkListing(ArrayList<NetworkListing> networkLists) {
        this.networkLists = networkLists;
    }

    public void updateLocalNetworks() {
        nm.obtainNetworks(caller);
        networkLists = convertToDefaultListing(nm.getNetworks());
    }

    public ArrayList<NetworkListing> convertToDefaultListing(ArrayList<BuyFiNetwork> buyFiList) {
        ArrayList<NetworkListing> networkList = new ArrayList<NetworkListing>();
        for(int i = 0; i < buyFiList.size(); i++) {
            networkList.add(new NetworkListing(buyFiList.get(i), false, "N/A", "Phone number"));
        }
        return networkList;
    }

}
