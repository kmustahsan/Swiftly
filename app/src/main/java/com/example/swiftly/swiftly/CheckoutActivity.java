package com.example.swiftly.swiftly;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cpark on 11/30/16.
 */

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup selection;
    EditText number;
    EditText date;
    EditText cvc;
    EditText name;
    EditText address;
    Button cancel;
    Button next;
    PaymentInformation info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        selection = (RadioGroup) findViewById(R.id.selection);
        number = (EditText) findViewById(R.id.number);
        date = (EditText) findViewById(R.id.date);
        cvc = (EditText) findViewById(R.id.cvc);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        cancel = (Button) findViewById(R.id.cancel);
        next = (Button) findViewById(R.id.next);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
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
            }
            else if (text.equals("xx64 Stephen Curry")) {
                info = new PaymentInformation("7881257650851264", "05/22", "722", "Stephen Curry",
                                              "1011 Broadway, Oakland, CA 94607");
            }
            else {
                info = new PaymentInformation(number.getText().toString(), date.getText().toString(),
                                               cvc.getText().toString(), name.getText().toString(),
                                               address.getText().toString());
            }
        }
    }
}
