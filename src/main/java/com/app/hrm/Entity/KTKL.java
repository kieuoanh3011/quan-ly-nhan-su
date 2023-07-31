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
public class KTKL {
    private int id;
    private float Tien;
    private String SoKTKL;
    private String Noidung;
    private Date Ngay;
    private String MaNV;
    private boolean Loai;

    public KTKL() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public float getTien() {
        return Tien;
    }

    public void setTien(float Tien) {
        this.Tien = Tien;
    }

    public String getSoKTKL() {
        return SoKTKL;
    }

    public void setSoKTKL(String SoKTKL) {
        this.SoKTKL = SoKTKL;
    }

    public String getNoidung() {
        return Noidung;
    }

    public void setNoidung(String Noidung) {
        this.Noidung = Noidung;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public boolean isLoai() {
        return Loai;
    }

    public void setLoai(boolean Loai) {
        this.Loai = Loai;
    }

    
}
