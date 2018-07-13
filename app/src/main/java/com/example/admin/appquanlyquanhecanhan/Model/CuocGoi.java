package com.example.admin.appquanlyquanhecanhan.Model;

/**
 * Created by Admin on 13-Apr-18.
 */

public class CuocGoi {
    int IDCD;
    String SDT;
    String Ngay;
    String tinhTrang;
    String thoiGian;
    int IDN;

    public CuocGoi(int IDCD, String SDT, String ngay, String tinhTrang, String thoiGian, int IDN) {
        this.IDCD = IDCD;
        this.SDT = SDT;
        Ngay = ngay;
        this.tinhTrang = tinhTrang;
        this.thoiGian = thoiGian;
        this.IDN = IDN;
    }

    public int getIDCD() {
        return IDCD;
    }

    public void setIDCD(int IDCD) {
        this.IDCD = IDCD;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getIDN() {
        return IDN;
    }

    public void setIDN(int IDN) {
        this.IDN = IDN;
    }
}
