package com.example.luke.buyfi;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Luke on 4/15/2018.
 */

@IgnoreExtraProperties
public class NetworkListing {

    private boolean claimed;
    private String price;
    private String contact;
    private BuyFiNetwork network;
    private String networkID;

    public NetworkListing(BuyFiNetwork network, boolean claimed, String price, String contact) {
        this.network = network;
        this.claimed = claimed;
        this.price = price;
        this.contact = contact;
        //the key of a network will be a combination of SSID and BSSID
        this.networkID = network.getSSID() + ":" + network.getBSSID();
    }

    public NetworkListing() {

    }

    public boolean getClaimed() { return claimed; }

    public String getActionName() {
        return claimed ? "RENT" : "CLAIM";
    }

    public String getStatus() { return claimed ? "CLAIMED" : "UNCLAIMED"; }

    public int getActionColor() { return claimed ? android.R.color.holo_green_light : android.R.color.darker_gray ; }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public BuyFiNetwork getNetwork() {
        return network;
    }

    public void setNetwork(BuyFiNetwork network) {
        this.network = network;
    }

    public String getNetworkID() { return this.networkID; }
}
