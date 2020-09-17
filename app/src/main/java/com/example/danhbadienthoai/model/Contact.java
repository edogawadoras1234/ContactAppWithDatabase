package com.example.danhbadienthoai.model;

public class Contact {
    private String id, name, phone, avatar;
    private int viewType;
    public Contact(){

    }

    public Contact(String id, String name, String phone, String avatar, int viewType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
        this.viewType = viewType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
