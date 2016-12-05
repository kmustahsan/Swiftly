package com.example.swiftly.swiftly;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup selection;
    EditText number;
    EditText date;
    EditText cvc;
    EditText name;
    EditText address;
    Button cancel;
    Button next;
    PaymentInformation info;
    Receipt receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        selection = (RadioGroup) findViewById(R.id.selection);
        number = (EditText) findViewById(R.id.number);
        date = (EditText) findViewById(R.id.date);
        cvc = (EditText) findViewById(R.id.cvc);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        cancel = (Button) findViewById(R.id.back);
        next = (Button) findViewById(R.id.next);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
        Intent intent = getIntent();
        receipt = (Receipt) intent.getSerializableExtra("Receipt");
    }

    @Override
    public void onClick(View view) {
        String clicked = ((TextView) view).getText().toString();
        if (clicked.equals("Cancel")) {
            // TODO
        }
        else {
            int selected = selection.getCheckedRadioButtonId();
            if (selected == -1) {
                Toast toast = Toast.makeText(this, "Please select a payment option", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            RadioButton buttonSelected = (RadioButton) findViewById(selected);
            String text = (String) buttonSelected.getText();
            if (text.equals("xx45 Taylor Swift")) {
                info = new PaymentInformation("6281157490851245", "12/22", "524", "Taylor Swift",
                        "242 West Main Street, Hendersonville, TN 37075");
                Intent intent = new Intent(this, ConfirmActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("Receipt", receipt);
                extras.putSerializable("Payment", info);
                intent.putExtras(extras);
                startActivity(intent);
            }
            else if (text.equals("xx64 Stephen Curry")) {
                info = new PaymentInformation("7881257650851264", "05/22", "722", "Stephen Curry",
                        "1011 Broadway, Oakland, CA 94607");
                Intent intent = new Intent(this, ConfirmActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("Receipt", receipt);
                extras.putSerializable("Payment", info);
                intent.putExtras(extras);
                startActivity(intent);
            }
            else {
                if (number.getText().toString().equals("") || date.getText().toString().equals("") ||
                        cvc.getText().toString().equals("") || name.getText().toString().equals("") ||
                        address.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                info = new PaymentInformation(number.getText().toString(), date.getText().toString(),
                        cvc.getText().toString(), name.getText().toString(),
                        address.getText().toString());
                Intent intent = new Intent(this, ConfirmActivity.class);
                Bundle extras = new Bundle();
                extras.putSerializable("Receipt", receipt);
                extras.putSerializable("Payment", info);
                intent.putExtras(extras);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int selected = selection.getCheckedRadioButtonId();
        outState.putInt("selected", selected);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int saved = savedInstanceState.getInt("selected");
        RadioButton buttonSelected = (RadioButton) findViewById(saved);
        buttonSelected.setChecked(true);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
