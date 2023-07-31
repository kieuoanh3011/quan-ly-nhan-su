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
public class NhanVien {
    private String maNV,hoTen,dienThoai,diaChi,CCCD,hinh,MKTK;
    private Date ngaySinh;
    private boolean gioiTinh;
    private int IDPB,IDCV,IDTD;

    public NhanVien() {
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getIDPB() {
        return IDPB;
    }

    public void setIDPB(int IDPB) {
        this.IDPB = IDPB;
    }


    public int getIDCV() {
        return IDCV;
    }

    public void setIDCV(int IDCV) {
        this.IDCV = IDCV;
    }

    public int getIDTD() {
        return IDTD;
    }

    public void setIDTD(int IDTD) {
        this.IDTD = IDTD;
    }

    public String getMKTK() {
        return MKTK;
    }

    public void setMKTK(String MKTK) {
        this.MKTK = MKTK;
    }
    
    
}
