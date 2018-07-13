package com.example.admin.appquanlyquanhecanhan.Model;

/**
 * Created by Admin on 15-Apr-18.
 */

public class Nhom {
    int IDNhom;
    String tenNhom;
    byte[] Icon;

    public Nhom(int IDNhom, String tenNhom, byte[] icon) {
        this.IDNhom = IDNhom;
        this.tenNhom = tenNhom;
        Icon = icon;
    }

    public int getIDNhom() {
        return IDNhom;
    }

    public void setIDNhom(int IDNhom) {
        this.IDNhom = IDNhom;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public byte[] getIcon() {
        return Icon;
    }

    public void setIcon(byte[] icon) {
        Icon = icon;
    }
}
