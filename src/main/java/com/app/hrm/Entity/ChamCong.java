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
public class ChamCong {

    private int id, gioiVao, phutVao, gioVe, phutVe, SoGioLam;
    private Date ngay;
    private String maNV;

    public ChamCong() {
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGioiVao() {
        return gioiVao;
    }

    public void setGioiVao(int gioiVao) {
        this.gioiVao = gioiVao;
    }

    public int getPhutVao() {
        return phutVao;
    }

    public void setPhutVao(int phutVao) {
        this.phutVao = phutVao;
    }

    public int getGioVe() {
        return gioVe;
    }

    public void setGioVe(int gioVe) {
        this.gioVe = gioVe;
    }

    public int getPhutVe() {
        return phutVe;
    }

    public void setPhutVe(int phutVe) {
        this.phutVe = phutVe;
    }

  

    public int getSoGioLam() {
        return SoGioLam;
    }

    public void setSoGioLam(int SoGioLam) {
        this.SoGioLam = SoGioLam;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

   

}
