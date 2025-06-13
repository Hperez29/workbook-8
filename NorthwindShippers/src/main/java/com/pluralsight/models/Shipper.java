package com.pluralsight.models;

public class Shipper {
    private int id;
    private String name;
    private String phone;

    public Shipper() {}

    public Shipper(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Shipper(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Phone: %s", id, name, phone);
    }
}