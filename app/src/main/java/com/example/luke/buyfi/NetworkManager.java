package com.example.luke.buyfi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luke on 4/10/2018.
 */

public class NetworkManager {

    private static NetworkManager sharedInstance = null;
    private ArrayList<BuyFiNetwork> networks = new ArrayList<BuyFiNetwork>();
    private Context mContext;
    private NetworkManager() {}

    public enum BUYFI_SORT {
        alphabetically,
        signal_strength
    }

    public static NetworkManager getSharedInstance(Context context) {
        if(sharedInstance == null) {
            sharedInstance = new NetworkManager(context);
        }
        return sharedInstance;
    }

    public NetworkManager(Context context) {
        this.mContext = context;
    }

    public void obtainNetworks() {

        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifiManager.getConnectionInfo();
        List<ScanResult> results = wifiManager.getScanResults();

        ScanResult bestSignal = null;
        int count = 1;
        String etWifiList = "";
        for (ScanResult result : results) {
            etWifiList += count++ + ". " + result.SSID + " : " + result.level + "\n" +
                    result.BSSID + "\n" + result.capabilities +"\n" +
                    "\n=======================\n";
            if(!result.SSID.toString().isEmpty()) {
                //addNetwork(new BuyFiNetwork(result.SSID, result.level, result.BSSID, result.capabilities));
                networks.add(new BuyFiNetwork(result.SSID, result.level, result.BSSID, result.capabilities));
            }
        }
        Log.v("MainActivity", "results: \n" + etWifiList);

        /*
        signal strength levels:
        -73 to -80 Strong
        -81 to -89 Good
        -90 to -100 Weak
         */
    }

    /*
    Custom add method to only add the strongest signal
    of the duplicate networks. Duplicates occur when
    the same network has multiple access points within range
    (I.E. eduroam, RESNET, etc.)
     */
    public void addNetwork(BuyFiNetwork addNetwork) {
        BuyFiNetwork duplicate = null;
        BuyFiNetwork original = null;
        for(BuyFiNetwork n : networks) {
            if(n.getSSID().equals(addNetwork.getSSID())) {
                duplicate = addNetwork;
                original = n;
                break;
            }
        }
        if(duplicate != null) {
            BuyFiNetwork weaker = original.weakerSignal(duplicate);
            BuyFiNetwork stronger = original.strongerSignal(duplicate);
            if(networks.contains(weaker)) {
                networks.remove(weaker);
                networks.add(stronger);
            }
        }
        else {
            networks.add(addNetwork);
        }
    }

    public ArrayList<BuyFiNetwork> getNetworks () {
        return networks;
    }

    public ArrayList<BuyFiNetwork> sortBy(BUYFI_SORT s) {
        if(s.equals(BUYFI_SORT.alphabetically)) {
            return sortAlphabetically();
        }
        if(s.equals(BUYFI_SORT.signal_strength)) {
            return sortSignalStrength();
        }
        return null;
    }

    public String getCurrentNetworkID() {

        String id = null;
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager != null) {
            NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(mobileInfo != null) {
                if(mobileInfo.isConnected()) {
                    return null;
                }
            }
            if(networkInfo != null) {
                if (networkInfo.isConnected()) {
                    final WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                    final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
                        //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
                        id = connectionInfo.getSSID();
                    }
                    // Get WiFi status MARAKANA
                    if(wifiManager != null) {
                        WifiInfo info = wifiManager.getConnectionInfo();
                        String textStatus = "";
                        textStatus += "\n\nWiFi Status: " + info.toString();
                        String BSSID = info.getBSSID();
                        String MAC = info.getMacAddress();
                        id += ":" + BSSID;
                    }
                }
            }
        }

        return id;
    }

    private ArrayList<BuyFiNetwork> sortAlphabetically() {

        return null;
    }

    private ArrayList<BuyFiNetwork> sortSignalStrength() {

        return null;
    }

}
