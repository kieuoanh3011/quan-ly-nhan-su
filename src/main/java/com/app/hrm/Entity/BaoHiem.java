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
public class BaoHiem {
    private String soBH,noiCap,noiKham,maNV;
    private Date ngayCap;
    private float tien;

    public BaoHiem() {
    }

    public String getSoBH() {
        return soBH;
    }

    public void setSoBH(String soBH) {
        this.soBH = soBH;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getNoiKham() {
        return noiKham;
    }

    public void setNoiKham(String noiKham) {
        this.noiKham = noiKham;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(Date ngayCap) {
        this.ngayCap = ngayCap;
    }

    public float getTien() {
        return tien;
    }

    public void setTien(float tien) {
        this.tien = tien;
    }
    
    
    
}
