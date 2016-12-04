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

        purchaseHistory = new ArrayList<>();
        Receipt receipt1 = new Receipt();
        receipt1.setTotal(3.6f);
        receipt1.setSubtotal(3.6f);
        receipt1.setTax(3.6f);
        Receipt receipt2 = new Receipt();
        receipt2.setTotal(4.2f);
        receipt2.setSubtotal(5.6f);
        receipt2.setTax(9.6f);
        purchaseHistory.add(receipt1);
        purchaseHistory.add(receipt2);
        purchaseList = (ListView) findViewById(R.id.purchaseList);

        touchHandler = new TouchHandler(this);
        purchaseList.setOnItemClickListener(this);
        purchaseList.setOnTouchListener(touchHandler);

        pAdapter = new PurchaseAdapter(this, R.id.purchaseList, purchaseHistory);
        purchaseList.setAdapter(pAdapter);
    }

    public void onSwipeLeft() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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