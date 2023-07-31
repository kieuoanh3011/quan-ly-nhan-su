/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Entity;

/**
 *
 * @author ADMIN
 */
public class ChucVu {
    private int idCV;
    private String tenCV;
    private double bacLuong;

    public ChucVu() {
    }

    public int getIdCV() {
        return idCV;
    }

    public void setIdCV(int idCV) {
        this.idCV = idCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }

    public double getBacLuong() {
        return bacLuong;
    }

    public void setBacLuong(double bacLuong) {
        this.bacLuong = bacLuong;
    }
    
    @Override
    public String toString(){
        return this.getTenCV();
    }
    
}
