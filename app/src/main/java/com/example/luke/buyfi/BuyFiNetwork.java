package com.example.luke.buyfi;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Luke on 4/12/2018.
 */

@IgnoreExtraProperties
public class BuyFiNetwork implements Serializable {
    private String SSID;
    private int signalStrength;
    private String BSSID;
    private String capabilities;

    enum signal_strength {
        strong,
        good,
        weak
    }

    /*
        signal strength levels:
        -73 to -80 Strong
        -81 to -89 Good
        -90 to -100 Weak
     */

    public BuyFiNetwork(String SSID, int signalStrength, String BSSID, String capabilities) {
        this.SSID = SSID;
        this.signalStrength = signalStrength;
        this.BSSID = BSSID;
        this.capabilities = capabilities;
    }

    public BuyFiNetwork() {

    }

    @Override
    public String toString() {
        return "BuyFiNetwork{" +
                "SSID='" + SSID + '\'' +
                ", signalStrength=" + signalStrength +
                ", BSSID='" + BSSID + '\'' +
                ", capabilities='" + capabilities + '\'' +
                '}';
    }

    public BuyFiNetwork weakerSignal(BuyFiNetwork duplicate) {
        return this.getSignalStrength() <= duplicate.getSignalStrength() ? this : duplicate;
    }

    public BuyFiNetwork strongerSignal(BuyFiNetwork duplicate) {
        return this.getSignalStrength() >= duplicate.getSignalStrength() ? this : duplicate;
    }

    public signal_strength getSignalLevel() {
        if(signalStrength <= -90) {
            return signal_strength.weak;
        }
        else if(signalStrength <= -81 && signalStrength >= -89) {
            return signal_strength.good;
        }
        else {
            return signal_strength.strong;
        }
    }

    public String convertSignalLevel() {
        switch (getSignalLevel()) {
            case good: return "Good";
            case strong: return "Strong";
            case weak: return "Weak";
            default: return "Good";
        }
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }
}
