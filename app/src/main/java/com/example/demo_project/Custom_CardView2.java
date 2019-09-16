package com.example.demo_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Custom_CardView2 {

    private String order_no;
    private String item_name;
    private String amount;

    public Custom_CardView2(String order_no, String item_name, String amount) {
        this.order_no = order_no;
        this.item_name = item_name;
        this.amount = amount;
    }

    public String getOrder_no() {
        return order_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getAmount() {
        return amount;
    }
}
