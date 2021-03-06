package com.example.luke.buyfi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Luke on 4/15/2018.
 */

public class BuyFiAdapter extends ArrayAdapter<NetworkListing> implements View.OnClickListener {

    private ArrayList<NetworkListing> list;
    Context mContext;
    private int lastPosition = -1;
    public static final String EXTRA_NETWORK_TAPPED = "com.example.buyfi.network_tapped";

    //cache
    private static class ViewHolder {
        TextView SSID;
        TextView price;
        Button action_button;
        ImageView signal_strength;
        TextView status;
    }

    public BuyFiAdapter(ArrayList<NetworkListing> list, Context context) {
        super(context, R.layout.row_item, list);
        this.list = list;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        //Not sure how to call this yet
        //openNetworkDetailsActivity();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NetworkListing network = getItem(position);
        final ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.SSID = (TextView) convertView.findViewById(R.id.SSID);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            viewHolder.action_button = (Button) convertView.findViewById(R.id.action_button);
            viewHolder.signal_strength = (ImageView) convertView.findViewById(R.id.signal_strength);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.SSID.setText(network.getNetwork().getSSID());
        viewHolder.price.setText(network.getPrice());
        viewHolder.status.setText(network.getStatus());
        viewHolder.action_button.setText(network.getActionName());
//        viewHolder.action_button.setBackgroundColor(network.getActionColor());
        viewHolder.signal_strength.setImageResource(getWifiImage(network.getNetwork().getSignalLevel()));
        viewHolder.action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNetworkDetailsActivity(network);
            }
        });
        return convertView;
    }

    private int getWifiImage(BuyFiNetwork.signal_strength signal_strength) {
        switch (signal_strength) {
            case weak: return R.drawable.wifi_weak;
            case good: return R.drawable.wifi_good;
            case strong: return R.drawable.wifi_full;
            default: return R.drawable.wifi_full;
        }
    }

    private void openNetworkDetailsActivity(NetworkListing networkList) {
        Intent i = new Intent(mContext, NetworkDetailsActivity.class);
        i.putExtra(EXTRA_NETWORK_TAPPED, networkList);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }
}
