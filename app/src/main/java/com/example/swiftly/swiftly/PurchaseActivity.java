package com.example.swiftly.swiftly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<Receipt> purchaseHistory;
    PurchaseAdapter pAdapter;
    ListView purchaseList;
    TouchHandler touchHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        FileInputStream fis;
        try {
            fis = openFileInput("ReceiptData");
            ObjectInputStream oi = new ObjectInputStream(fis);
            purchaseHistory = (ArrayList<Receipt>) oi.readObject();
            System.out.println("purchase history");
            System.out.println(purchaseHistory);
            oi.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        purchaseList = (ListView) findViewById(R.id.purchaseList);

        touchHandler = new TouchHandler(this);
        purchaseList.setOnItemClickListener(this);
        purchaseList.setOnTouchListener(touchHandler);

        pAdapter = new PurchaseAdapter(this, R.id.purchaseList, purchaseHistory);
        purchaseList.setAdapter(pAdapter);
    }

    public void onSwipeLeft() {
        finish();
    }

    public void onSwipeRight() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView history = (TextView) view.findViewById(R.id.history);
        if (history.getVisibility() == View.GONE) {
            history.setVisibility(View.VISIBLE);
        }
        else {
            history.setVisibility(View.GONE);
        }
    }
}