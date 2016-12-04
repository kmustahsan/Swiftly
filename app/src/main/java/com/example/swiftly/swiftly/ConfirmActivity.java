package com.example.swiftly.swiftly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    Button cancel;
    Button submit;
    TextView items;
    TextView payment;
    Receipt receipt;
    PaymentInformation paymentInformation;

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
    }

    @Override
    public void onClick(View view) {
        String clicked = ((TextView) view).getText().toString();
        if (clicked.equals("Cancel")) {
            // TODO
        }
        else {
            Intent intent = new Intent(this, Receipt.class);
            intent.putExtra("Receipt", receipt);
            startActivity(intent);
        }
    }
}
