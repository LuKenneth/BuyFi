package com.example.luke.buyfi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.luke.buyfi.BuyFiAdapter.EXTRA_NETWORK_TAPPED;

/*
This class is for editing the information of a network
when claiming to be the host.
 */
public class ClaimNetworkActivity extends AppCompatActivity {

    private TextView nameTextView;
    private EditText networkPriceTextView;
    private EditText networkContactTextView;
    private Button claimButton;
    private NetworkListing mNetworkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_network);

        Intent i = getIntent();
        mNetworkList = (NetworkListing) i.getSerializableExtra(EXTRA_NETWORK_TAPPED);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        networkPriceTextView = (EditText) findViewById(R.id.networkPrice);
        networkContactTextView = (EditText) findViewById(R.id.networkContact);
        claimButton = (Button) findViewById(R.id.claimButton);

        nameTextView.setText(mNetworkList.getNetwork().getSSID());
        claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!networkPriceTextView.getText().toString().isEmpty() && !networkContactTextView.getText().toString().isEmpty()) {
                    FirebaseManager fm = new FirebaseManager();
                    mNetworkList.setClaimed(true);
                    mNetworkList.setContact(networkContactTextView.getText().toString());
                    mNetworkList.setPrice(networkPriceTextView.getText().toString());
                    fm.claimNetwork(mNetworkList);
                    Toast.makeText(getApplicationContext(), "Network claimed.", Toast.LENGTH_LONG).show();
                    openNetworkDetailsActivity(mNetworkList);
                    close(v);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Price and contact information are required", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void close(View v) {
        finish();
    }

    private void openNetworkDetailsActivity(NetworkListing networkList) {
        Intent i = new Intent(this, NetworkDetailsActivity.class);
        i.putExtra(EXTRA_NETWORK_TAPPED, networkList);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
    }
}
