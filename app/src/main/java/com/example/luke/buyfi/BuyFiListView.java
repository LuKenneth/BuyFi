package com.example.luke.buyfi;

import android.widget.ListView;

import java.io.Serializable;

/**
 * Created by Luke on 5/2/2018.
 * uhhh, i think I had to create this to wrap a ListView
 * into a serializable object, but I may have gone a different route
 * that did not need this. cant remember to be honest.
 */

public class BuyFiListView implements Serializable {
    private ListView list;

    public BuyFiListView(ListView list) {
        this.list = list;
    }

    public ListView getList() {
        return list;
    }

}
