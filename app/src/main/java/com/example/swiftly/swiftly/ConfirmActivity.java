package com.example.swiftly.swiftly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    Button cancel;
    Button submit;
    TextView items;
    TextView payment;
    Receipt receipt;
    PaymentInformation paymentInformation;
    int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);
        items = (TextView) findViewById(R.id.receipt);
        payment = (TextView) findViewById(R.id.payment);
        Bundle extras = getIntent().getExtras();
        receipt = (Receipt) extras.getSerializable("Receipt");
        paymentInformation = (PaymentInformation) extras.getSerializable("Payment");
        items.setText(receipt.toString());
        payment.setText(paymentInformation.toString());
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    @Override
    public void onClick(View view) {
        String clicked = ((TextView) view).getText().toString();
        if (clicked.equals("Cancel")) {
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("Receipt", receipt);
            startActivity(intent);
        }
        else {
            FileInputStream fis;
            ArrayList<Receipt> purchaseHistory = new ArrayList<>();
            try {
                fis = openFileInput("ReceiptData");
                ObjectInputStream oi = new ObjectInputStream(fis);
                purchaseHistory = (ArrayList<Receipt>) oi.readObject();
                oi.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            purchaseHistory.add(receipt);
            try {
                FileOutputStream fos = openFileOutput("ReceiptData", MODE_PRIVATE);
                ObjectOutputStream of = new ObjectOutputStream(fos);
                of.writeObject(purchaseHistory);
                of.flush();
                of.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, ReceiptActivity.class);
            intent.putExtra("Receipt", receipt);
            startActivity(intent);
        }
    }
}
