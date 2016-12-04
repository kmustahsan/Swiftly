package com.example.swiftly.swiftly;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/26/16.
 */

public class Receipt {
    ArrayList<JSONObject> items;
    float subtotal;
    float tax;
    float total;

    public Receipt() {
        items = new ArrayList<>();
        subtotal = 0f;
        tax = 0f;
        total = 0f;
    }

    public ArrayList<JSONObject> getItems() {
        return items;
    }

    public void setItems(ArrayList<JSONObject> items) {
        this.items = items;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
