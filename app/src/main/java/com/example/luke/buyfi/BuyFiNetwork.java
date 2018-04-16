package com.example.luke.buyfi;

/**
 * Created by Luke on 4/12/2018.
 */

public class BuyFiNetwork {
    private String SSID;
    private int signalStrength;
    private String BSSID;
    private String capabilities;
    enum signal_strength {
        strong,
        good,
        weak
    }

    public BuyFiNetwork(String SSID, int signalStrength, String BSSID, String capabilities) {
        this.SSID = SSID;
        this.signalStrength = signalStrength;
        this.BSSID = BSSID;
        this.capabilities = capabilities;
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
