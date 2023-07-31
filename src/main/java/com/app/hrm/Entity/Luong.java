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
public class Luong {
    private int id,maKC,gio;
    private String Manv;
    private double bacLuong,LuongCB,UngLuong,KTKL,TangCa,Luong, phuCap,baohiem,thue;
    Date ngayCham;

    public Luong() {
    }

    public Date getNgayCham() {
        return ngayCham;
    }

    public void setNgayCham(Date ngayCham) {
        this.ngayCham = ngayCham;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaKC() {
        return maKC;
    }

    public void setMaKC(int maKC) {
        this.maKC = maKC;
    }

    public int getGio() {
        return gio;
    }

    public void setGio(int gio) {
        this.gio = gio;
    }

    public String getManv() {
        return Manv;
    }

    public void setManv(String Manv) {
        this.Manv = Manv;
    }

    public double getBacLuong() {
        return bacLuong;
    }

    public void setBacLuong(double bacLuong) {
        this.bacLuong = bacLuong;
    }

    public double getLuongCB() {
        return LuongCB;
    }

    public void setLuongCB(double LuongCB) {
        this.LuongCB = LuongCB;
    }

    public double getUngLuong() {
        return UngLuong;
    }

    public void setUngLuong(double UngLuong) {
        this.UngLuong = UngLuong;
    }

    public double getKTKL() {
        return KTKL;
    }

    public void setKTKL(double KTKL) {
        this.KTKL = KTKL;
    }

    public double getTangCa() {
        return TangCa;
    }

    public void setTangCa(double TangCa) {
        this.TangCa = TangCa;
    }

    public double getLuong() {
        return Luong;
    }

    public void setLuong(double Luong) {
        this.Luong = Luong;
    }

    public double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(double phuCap) {
        this.phuCap = phuCap;
    }

    public double getBaohiem() {
        return baohiem;
    }

    public void setBaohiem(double baohiem) {
        this.baohiem = baohiem;
    }

    public double getThue() {
        return thue;
    }

    public void setThue(double thue) {
        this.thue = thue;
    }

  
    
    
}
