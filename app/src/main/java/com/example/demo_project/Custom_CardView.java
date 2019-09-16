package com.example.demo_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Custom_CardView {

    private String group_name;
    private String item_name;
    private String oty;
    private String rate;
    private String amount;

    public Custom_CardView(String group_name, String item_name, String oty, String rate, String amount) {
        this.group_name = group_name;
        this.item_name = item_name;
        this.oty = oty;
        this.rate = rate;
        this.amount = amount;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getOty() {
        return oty;
    }

    public String getRate() {
        return rate;
    }

    public String getAmount() {
        return amount;
    }
}
