package com.example.swiftly.swiftly;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Adapter extends BaseAdapter {

    CartActivity activity;
    TextView name;
    TextView price;
    Button delete;

    public Adapter(CartActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return activity.shoppingCartJson.size();
    }

    @Override
    public Object getItem(int position) {
        return activity.shoppingCartJson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            name = (TextView) convertView.findViewById(R.id.name);
            price = (TextView) convertView.findViewById(R.id.price);
            delete = (Button) convertView.findViewById(R.id.delete);
        }

        System.out.println(position);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer)v.getTag();
                System.out.println(position);
                JSONObject item = activity.shoppingCartJson.get(position);
                try {
                    int count = (int)item.get("count");
                    if (count == 1) {
                        activity.shoppingCartJson.remove(position);
                    }
                    else {
                        item.put("count", count - 1);
                        activity.shoppingCartJson.set(position, item);
                    }
                    notifyDataSetChanged();
                    activity.updateTotal();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // set count, name, and price on list
            JSONObject item = activity.shoppingCartJson.get(position);
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
