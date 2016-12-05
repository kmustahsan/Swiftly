package com.example.swiftly.swiftly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity implements View.OnClickListener {

    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        TextView pReceipt = (TextView) findViewById(R.id.receipt);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        Receipt receipt = (Receipt) extras.getSerializable("Receipt");
        String receiptStr = "";
        receiptStr += receipt.getDate() + "\n";
        receiptStr += receipt.toString();
        pReceipt.setText(receiptStr);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}