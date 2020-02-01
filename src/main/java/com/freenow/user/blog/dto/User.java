package com.freenow.user.blog.dto;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address AddressObject;
    private Company company;
    private String phone;
    private String website;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return AddressObject;
    }

    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address addressObject) {
        this.AddressObject = addressObject;
    }

    public Address getAddressObject() {
        return AddressObject;
    }

    public void setAddressObject(Address addressObject) {
        AddressObject = addressObject;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}