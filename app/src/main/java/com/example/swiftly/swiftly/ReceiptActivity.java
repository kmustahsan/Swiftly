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
        TextView pDate = (TextView) findViewById(R.id.date);
        TextView pTime = (TextView) findViewById(R.id.time);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        Receipt receipt = (Receipt) extras.getSerializable("Receipt");
        pDate.setText(receipt.getDate());
        pTime.setText(receipt.getTime());
        pReceipt.setText(receipt.toString());
    }

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(this, MainActivity.class);
        ArrayList<String> completeCart = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.ITEMS, completeCart);
        intent.putExtras(bundle);
        setResult(1, intent);

        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }
}