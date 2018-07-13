package com.example.admin.appquanlyquanhecanhan.Model;

/**
 * Created by Admin on 06-Apr-18.
 */

public class NguoiQH {
    int IDN;
    String SDT;
    String hoTen;
    String email;
    String faceBook;
    String diaChi;
    String ngaySinh;
    byte[] anhDaiDien;
    int uaThich;

    public NguoiQH(int IDN, String SDT, String hoTen, String email, String faceBook, String diaChi, String ngaySinh, byte[] anhDaiDien, int uaThich) {
        this.IDN = IDN;
        this.SDT = SDT;
        this.hoTen = hoTen;
        this.email = email;
        this.faceBook = faceBook;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.anhDaiDien = anhDaiDien;
        this.uaThich = uaThich;
    }

    public int getIDN() {
        return IDN;
    }

    public void setIDN(int IDN) {
        this.IDN = IDN;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaceBook() {
        return faceBook;
    }

    public void setFaceBook(String faceBook) {
        this.faceBook = faceBook;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public byte[] getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(byte[] anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public int getUaThich() {
        return uaThich;
    }

    public void setUaThich(int uaThich) {
        this.uaThich = uaThich;
    }
}
