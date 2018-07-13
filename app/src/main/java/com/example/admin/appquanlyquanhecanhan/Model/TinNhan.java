package com.example.admin.appquanlyquanhecanhan.Model;

/**
 * Created by Admin on 12-Apr-18.
 */

public class TinNhan {
    private int IDTN;
    String SDT;
    private String Ngay;
    private String noiDung;
    int IDN;

    public TinNhan(int IDTN, String SDT, String ngay, String noiDung, int IDN) {
        this.IDTN = IDTN;
        this.SDT = SDT;
        Ngay = ngay;
        this.noiDung = noiDung;
        this.IDN = IDN;
    }

    public int getIDTN() {
        return IDTN;
    }

    public void setIDTN(int IDTN) {
        this.IDTN = IDTN;
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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getIDN() {
        return IDN;
    }

    public void setIDN(int IDN) {
        this.IDN = IDN;
    }
}
