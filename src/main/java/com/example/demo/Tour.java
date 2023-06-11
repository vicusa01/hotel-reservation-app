package com.example.demo;

import java.io.Serializable;

public class Tour implements Serializable {
    private String name;
    private String customer;
    private String check_in_date;
    private String  check_out_date;
    private int price;

    public Tour(String name, String customer, String check_in_date, String check_out_date, int price) {
        this.name = name;
        this.customer = customer;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
