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
public class UngLuong {
    private int ID;
    private Date NgayUng;
    private float SoTien;
    private boolean TrangThai;
    private String MaNV;

    public UngLuong() {
    }

   

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getNgayUng() {
        return NgayUng;
    }

    public void setNgayUng(Date NgayUng) {
        this.NgayUng = NgayUng;
    }

   

    public float getSoTien() {
        return SoTien;
    }

    public void setSoTien(float SoTien) {
        this.SoTien = SoTien;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }
}
