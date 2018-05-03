package com.example.luke.buyfi;

import android.widget.ListView;

import java.io.Serializable;

/**
 * Created by Luke on 5/2/2018.
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
