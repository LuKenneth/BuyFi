package com.example.luke.buyfi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Luke on 4/15/2018.
 */

public class FirebaseManager {

    private DatabaseReference ref;

    public FirebaseManager() {
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public void writeNetwork(NetworkListing network) {
        //the key of a network will be a combination of SSID and BSSID
        String networkID = network.getNetwork().getSSID() + ":" + network.getNetwork().getBSSID();
        ref.child(networkID).setValue(network);
    }

}
