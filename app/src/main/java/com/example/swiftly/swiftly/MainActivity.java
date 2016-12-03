package com.example.swiftly.swiftly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    TextView summary;
    static int count = 0;
    private JSONObject itemsDB; // Shopping Cart DB
    static ArrayList<JSONObject> shoppingCart = new ArrayList<>();
    TouchHandler touchHandler;

    public final static String ITEMS = "items";

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            lastText = result.getText();
            barcodeView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();

            // lastText contains the number for the barcode, so make call to Shopping Cart
            // DB here
            try {
                JSONObject item = itemsDB.getJSONObject(lastText);
                Boolean itemInCart = false;

                // will go to catch if item is not within list
                for (int i = 0; i < shoppingCart.size(); i++) {
                    JSONObject cartItem = shoppingCart.get(i);
                    if (cartItem.get("name").equals(item.get("name"))) {
                        int cartCount = (int)cartItem.get("count");
                        cartItem.put("count", cartCount + 1);
                        shoppingCart.set(i, cartItem);
                        itemInCart = true;
                        break;
                    }
                }
                if (!itemInCart) {
                    item.put("count", 1);
                    shoppingCart.add(item);
                }
                summary.setText("Items in Shopping Cart: " + ++count);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Item not found within database.", Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        summary = (TextView) findViewById(R.id.summary);

        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);

        beepManager = new BeepManager(this);
        touchHandler = new TouchHandler(this);
        barcodeView.setOnTouchListener(touchHandler);

        // Creating Fake Shopping Cart DB
        try {
            itemsDB = new JSONObject();

            JSONObject details1 = new JSONObject();
            details1.put("name", "Colgate Toothpaste");
            details1.put("price", "10.00");
            JSONObject details2 = new JSONObject();
            details2.put("name", "Cup");
            details2.put("price", "5.00");
            JSONObject details3 = new JSONObject();
            details3.put("name", "Crackers");
            details3.put("price", "3.00");

            itemsDB.put("3500074140", details1);
            itemsDB.put("90311017", details2);
            itemsDB.put("90311024", details3);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void onSwipeRight() {
        Intent intent = new Intent(this, PurchaseActivity.class);
        startActivity(intent);
    }

    public void onSwipeLeft() {
        Intent intent = new Intent(this, CartActivity.class);
        ArrayList<String> completeCart = new ArrayList<>();
        for (int i = 0; i < shoppingCart.size(); i++) {
            JSONObject item = shoppingCart.get(i);
            completeCart.add(item.toString());
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, completeCart);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        shoppingCart = new ArrayList<>();
        Bundle bundle = data.getExtras();
        ArrayList<String> cartString = (ArrayList<String>) bundle.getSerializable(ITEMS);

        try {
            for (int i = 0; i < cartString.size(); i++) {
                JSONObject item = new JSONObject(cartString.get(i));
                shoppingCart.add(item);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
