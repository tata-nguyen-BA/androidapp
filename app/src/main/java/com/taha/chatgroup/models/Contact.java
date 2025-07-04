package com.taha.chatgroup.models;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName()  { return name; }
    public String getPhone() { return phone; }
}