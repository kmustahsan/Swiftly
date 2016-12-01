package com.example.swiftly.swiftly;

/**
 * Created by cpark on 11/30/16.
 */

public class PaymentInformation {

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
}
