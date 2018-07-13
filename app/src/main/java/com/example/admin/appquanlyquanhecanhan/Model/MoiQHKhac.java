package com.example.admin.appquanlyquanhecanhan.Model;

/**
 * Created by Admin on 14-Apr-18.
 */

public class MoiQHKhac {
    int IDQH;
    String hoTen;
    String SDTQH;
    String kieuQH;
    String ngaySinh;
    String diaChi;
    int IDN;

    public int getIDQH() {
        return IDQH;
    }

    public void setIDQH(int IDQH) {
        this.IDQH = IDQH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDTQH() {
        return SDTQH;
    }

    public void setSDTQH(String SDTQH) {
        this.SDTQH = SDTQH;
    }

    public String getKieuQH() {
        return kieuQH;
    }

    public void setKieuQH(String kieuQH) {
        this.kieuQH = kieuQH;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getIDN() {
        return IDN;
    }

    public void setIDN(int IDN) {
        this.IDN = IDN;
    }

    public MoiQHKhac(int IDQH, String hoTen, String SDTQH, String kieuQH, String ngaySinh, String diaChi, int IDN) {
        this.IDQH = IDQH;
        this.hoTen = hoTen;
        this.SDTQH = SDTQH;
        this.kieuQH = kieuQH;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.IDN = IDN;
    }
}
