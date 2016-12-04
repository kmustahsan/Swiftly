package com.example.swiftly.swiftly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> shoppingCart;
    ArrayList<JSONObject> shoppingCartJson;
    Adapter adapter;
    ListView listView;
    TextView subtotal;
    TextView tax;
    TextView total;
    Button checkout;
    LinearLayout page;
    Receipt receipt;

    float subtotal_amt;
    TouchHandler touchHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        receipt = new Receipt();

        listView = (ListView) findViewById(R.id.list);
        subtotal = (TextView) findViewById(R.id.subtotal);
        tax = (TextView) findViewById(R.id.tax);
        total = (TextView) findViewById(R.id.total);
        checkout = (Button) findViewById(R.id.checkout);
        page = (LinearLayout) findViewById(R.id.page);

        checkout.setOnClickListener(this);
        touchHandler = new TouchHandler(this);
        page.setOnTouchListener(touchHandler);
        listView.setOnTouchListener(touchHandler);

        // get intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        shoppingCart = (ArrayList<String>) bundle.getSerializable(MainActivity.ITEMS);

        shoppingCartJson = new ArrayList<>();
        subtotal_amt = 0;

        try {
            for (int i = 0; i < shoppingCart.size(); i++) {
                JSONObject item = new JSONObject(shoppingCart.get(i));
                shoppingCartJson.add(item);
                float cost = Float.parseFloat(item.get("price").toString());
                cost *= Float.parseFloat(item.get("count").toString());
                subtotal_amt += cost;
            }
            receipt.setItems(shoppingCartJson);
            float tax_amt = .05f * subtotal_amt;
            float total_amt = tax_amt + subtotal_amt;
            receipt.setSubtotal(subtotal_amt);
            receipt.setTax(tax_amt);
            receipt.setTotal(total_amt);
            subtotal.setText("$" + String.format("%.2f", subtotal_amt));
            tax.setText("$" + String.format("%.2f", tax_amt));
            total.setText("$" + String.format("%.2f", total_amt));
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        // set list
        adapter = new Adapter(this, shoppingCartJson);
        listView.setAdapter(adapter);
    }

    public void setAdapter(ArrayList<JSONObject> aList) {
        adapter = new Adapter(this, aList);
        listView.setAdapter(adapter);
        shoppingCartJson = aList;
    }

    public void updateTotal() {
        try {
            subtotal_amt = 0;
            for (int i = 0; i < shoppingCartJson.size(); i++) {
                JSONObject item = shoppingCartJson.get(i);
                float cost = Float.parseFloat(item.get("price").toString());
                cost *= Float.parseFloat(item.get("count").toString());
                subtotal_amt += cost;
            }
            float tax_amt = .05f * subtotal_amt;
            float total_amt = tax_amt + subtotal_amt;

            receipt.setItems(shoppingCartJson);
            receipt.setSubtotal(subtotal_amt);
            receipt.setTax(tax_amt);
            receipt.setTotal(total_amt);

            subtotal.setText("$" + String.format("%.2f", subtotal_amt));
            tax.setText("$" + String.format("%.2f", tax_amt));
            total.setText("$" + String.format("%.2f", total_amt));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        // Checkout button action here
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_edit:
                int visibility = View.VISIBLE;
                if (item.getTitle().equals("Edit")) {
                    item.setTitle("Done");
                }
                else {
                    visibility = View.INVISIBLE;
                    item.setTitle("Edit");
                }
                showDeleteButtons(visibility);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDeleteButtons(int visibility) {
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = listView.getChildAt(i);
            Button deleteButton = (Button)view.findViewById(R.id.delete);
            deleteButton.setVisibility(visibility);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_button, menu);
        menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
        Intent intent = new Intent();
        ArrayList<String> completeCart = new ArrayList<>();
        for (int i = 0; i < shoppingCartJson.size(); i++) {
            JSONObject item = shoppingCartJson.get(i);
            completeCart.add(item.toString());
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.ITEMS, completeCart);
        intent.putExtras(bundle);
        setResult(1, intent);
        finish();
    }
}
