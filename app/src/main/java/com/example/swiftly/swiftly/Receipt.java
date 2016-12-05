package com.example.swiftly.swiftly;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kevin on 11/26/16.
 */

public class Receipt implements Serializable {
    ArrayList<String> items;
    float subtotal;
    float tax;
    float total;
    int day, month, year;

    public Receipt() {
        items = new ArrayList<>();
        subtotal = 0f;
        tax = 0f;
        total = 0f;
        day = month = year = 0;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
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

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int d) {
        day = d;
    }

    public void setMonth(int m) {
        month = m;
    }

    public void setYear(int y) {
        year = y;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        try {
            for (int i = 0; i < getItems().size(); i++) {
                JSONObject item = new JSONObject(items.get(i));
                String name = item.get("count") + " " + item.get("name").toString();
                float cost = Float.parseFloat(item.get("price").toString());
                cost *= Float.parseFloat(item.get("count").toString());
                String price = "$" + String.format("%-10.2f", cost);
                String temp = String.format("%10s", name);
                str.append(temp);
                str.append("                  ");
                str.append(price);
                str.append("\n");
            }
            str.append("Subtotal:                   ");
            str.append("$");
            str.append(String.format("%.2f", subtotal));
            str.append("\n");
            str.append("Tax:                        ");
            str.append("$");
            str.append(String.format("%.2f", tax));
            str.append("\n");
            str.append("Total:                      ");
            str.append("$");
            str.append(String.format("%.2f", total));

            return str.toString();
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return super.toString();
    }
}
