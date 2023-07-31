/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Entity;

import com.app.hrm.Utils.XDate;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class KyCong {
    private int maKC;
    private int thang;
    private int nam;
    private boolean khoa;
    private Date ngayTinh;
    private int maPB;
    private boolean trangThai;

    public KyCong() {
    }

    public int getMaKC() {
        return maKC;
    }

    public void setMaKC(int maKC) {
        this.maKC = maKC;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public boolean isKhoa() {
        return khoa;
    }

    public void setKhoa(boolean khoa) {
        this.khoa = khoa;
    }

    public Date getNgayTinh() {
        return ngayTinh;
    }

    public void setNgayTinh(Date ngayTinh) {
        this.ngayTinh = ngayTinh;
    }

    public int getMaPB() {
        return maPB;
    }

    public void setMaPB(int maPB) {
        this.maPB = maPB;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    @Override
    public String toString(){
        String ngay = XDate.toString(ngayTinh);
        return this.maKC+" ("+ngay+" )";
    }
    
}
