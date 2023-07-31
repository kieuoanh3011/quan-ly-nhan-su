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
public class TangCa {
    private int ID;
    private Date Ngay;
    private int SoGio;
    private String MaNV;
    private int IDloaiCa;
    private float Tien;

    public TangCa() {
    }

    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

   

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date Ngay) {
        this.Ngay = Ngay;
    }

    public int getSoGio() {
        return SoGio;
    }

    public void setSoGio(int SoGio) {
        this.SoGio = SoGio;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public int getIDloaiCa() {
        return IDloaiCa;
    }

    public void setIDloaiCa(int IDloaiCa) {
        this.IDloaiCa = IDloaiCa;
    }

    public float getTien() {
        return Tien;
    }

    public void setTien(float Tien) {
        this.Tien = Tien;
    }
    
    
}
