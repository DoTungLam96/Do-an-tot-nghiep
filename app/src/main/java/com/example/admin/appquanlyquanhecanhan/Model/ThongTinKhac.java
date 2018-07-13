package com.example.admin.appquanlyquanhecanhan.Model;

/**
 * Created by Admin on 5/4/2018.
 */

public class ThongTinKhac {
    private int hinhAnh;
    private String chiTiet;

    public ThongTinKhac(int hinhAnh, String chiTiet) {
        this.hinhAnh = hinhAnh;
        this.chiTiet = chiTiet;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }
}
