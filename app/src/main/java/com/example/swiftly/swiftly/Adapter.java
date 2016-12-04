package com.example.swiftly.swiftly;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Kevin on 11/25/16.
 */

public class Adapter extends ArrayAdapter {

    CartActivity activity;
    ArrayList<JSONObject> shoppingCart;
    TextView name;
    TextView price;
    Button delete;
    static Handler UIHandler = null;



    public Adapter(final CartActivity activity, ArrayList<JSONObject> shoppingCart) {
        super(activity, 0, shoppingCart);
        this.activity = activity;
        this.shoppingCart = shoppingCart;

        UIHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("HIT 2");
                if (msg.what == 0) {
                    System.out.println("HIT 1");
                    activity.showDeleteButtons(View.VISIBLE);
                    activity.updateTotal();
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public int getCount() {
        return shoppingCart.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            name = (TextView) convertView.findViewById(R.id.name);
            price = (TextView) convertView.findViewById(R.id.price);
            delete = (Button) convertView.findViewById(R.id.delete);
        }

        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                JSONObject item = shoppingCart.get(position);
                try {
                    int count = (int)item.get("count");
                    if (count == 1) {
                        shoppingCart.remove(position);
                    }
                    else {
                        item.put("count", count - 1);
                        shoppingCart.set(position, item);
                    }
                    activity.setAdapter(shoppingCart);
//                        notifyDataSetChanged();
                    Message msg = UIHandler.obtainMessage(0, position);
                    msg.sendToTarget();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // set count, name, and price on list
            JSONObject item = shoppingCart.get(position);
            name.setText(item.get("count") + " " + item.get("name").toString());
            float cost = Float.parseFloat(item.get("price").toString());
            cost *= Float.parseFloat(item.get("count").toString());
            price.setText("$" + String.format("%.2f", cost));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
