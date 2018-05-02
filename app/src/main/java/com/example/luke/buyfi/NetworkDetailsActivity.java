package com.example.luke.buyfi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.luke.buyfi.BuyFiAdapter.EXTRA_NETWORK_TAPPED;

public class NetworkDetailsActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView networkSignalStrengthTextView;
    private TextView networkStatusTextView;
    private TextView networkPriceTextView;
    private TextView networkContactTextView;
    private Button claimButton;
    private NetworkListing mNetworkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_details);

        Intent i = getIntent();
        mNetworkList = (NetworkListing) i.getSerializableExtra(EXTRA_NETWORK_TAPPED);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        networkSignalStrengthTextView = (TextView) findViewById(R.id.networkSignalStrength);
        networkStatusTextView = (TextView) findViewById(R.id.networkStatus);
        networkPriceTextView = (TextView) findViewById(R.id.networkPrice);
        networkContactTextView = (TextView) findViewById(R.id.networkContact);
        claimButton = (Button) findViewById(R.id.claimButton);

        nameTextView.setText(mNetworkList.getNetwork().getSSID());
        networkSignalStrengthTextView.setText(mNetworkList.getNetwork().convertSignalLevel());
        networkStatusTextView.setText(mNetworkList.getStatus());
        networkPriceTextView.setText(mNetworkList.getPrice());
        networkContactTextView.setText(mNetworkList.getContact());

        if(mNetworkList.getClaimed()) {
            claimButton.setVisibility(View.INVISIBLE);
        }
        claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isConnected()) {
                    openClaimNetworkActivity(mNetworkList);
                    close(v);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please connect to this network to claim as the owner of the network.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void close(View v) {
        finish();
    }

    public boolean isConnected() {
        NetworkManager nm = NetworkManager.getSharedInstance(this);
        Log.v("CONNECTED TO: ", nm.getCurrentNetworkID());
        Log.v("NETWORK ID: ", mNetworkList.getNetworkID());
        if(nm.getCurrentNetworkID() != null) {
            return (nm.getCurrentNetworkID().equals(mNetworkList.getNetworkID()));
        }
        else {
            return false;
        }
    }

    private void openClaimNetworkActivity(NetworkListing networkList) {
        Intent i = new Intent(this, ClaimNetworkActivity.class);
        i.putExtra(EXTRA_NETWORK_TAPPED, networkList);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
    }
}
