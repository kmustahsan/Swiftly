package com.example.swiftly.swiftly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Khoa on 11/28/2016.
 */
public class PurchaseAdapter extends ArrayAdapter<Receipt> {

    ArrayList<Receipt> receipts;
    ViewHolder viewHolder;

    public PurchaseAdapter(Context context, int resource, ArrayList<Receipt> receipts) {
        super(context, resource, receipts);
        this.receipts = receipts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Receipt receipt = getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.purchase_list, parent, false);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.history = (TextView) convertView.findViewById(R.id.history);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String dateStr = receipt.getDate() + " - " + receipt.getTime();
        viewHolder.date.setText(dateStr);
        viewHolder.history.setText(receipt.toString());
        return convertView;
    }

    private static class ViewHolder {
        TextView date;
        TextView history;
    }
}