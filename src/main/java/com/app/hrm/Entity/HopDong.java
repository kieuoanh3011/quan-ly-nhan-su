/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Entity;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class HopDong {
    private int SoHD;
    private Date NgayBD;
    private Date NgayKT;
    private Date NgayKy;
    private String Noidung;
    private int LanKy;
    
    private String MaNV;
    private float luongCB;

    public HopDong() {
    }

    public float getLuongCB() {
        return luongCB;
    }

    public void setLuongCB(float luongCB) {
        this.luongCB = luongCB;
    }

   

    public int getSoHD() {
        return SoHD;
    }

    public void setSoHD(int SoHD) {
        this.SoHD = SoHD;
    }

    public Date getNgayBD() {
        return NgayBD;
    }

    public void setNgayBD(Date NgayBD) {
        this.NgayBD = NgayBD;
    }

    public Date getNgayKT() {
        return NgayKT;
    }

    public void setNgayKT(Date NgayKT) {
        this.NgayKT = NgayKT;
    }

    public Date getNgayKy() {
        return NgayKy;
    }

    public void setNgayKy(Date NgayKy) {
        this.NgayKy = NgayKy;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String Noidung) {
        this.Noidung = Noidung;
    }

    public int getLanKy() {
        return LanKy;
    }

    public void setLanKy(int LanKy) {
        this.LanKy = LanKy;
    }

   

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }
    
    
}
