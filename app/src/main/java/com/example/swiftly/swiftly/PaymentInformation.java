package com.example.swiftly.swiftly;

import java.io.Serializable;

/**
 * Created by cpark on 11/30/16.
 */

public class PaymentInformation implements Serializable {

    String cardNum;
    String date;
    String cvc;
    String name;
    String address;

    public PaymentInformation(String cardNum, String date, String cvc, String name, String address) {
        this.cardNum = cardNum;
        this.date = date;
        this.cvc = cvc;
        this.name = name;
        this.address = address;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getDate() {
        return date;
    }

    public String getCvc() {
        return cvc;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        String num = String.format("xx%s", cardNum.substring(cardNum.length() - 2, cardNum.length()));
        String str = String.format("Payment method:\n%s   %s", num, name);
        return str;
    }
}
