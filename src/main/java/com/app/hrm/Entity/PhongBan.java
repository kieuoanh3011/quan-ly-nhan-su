/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Entity;

/**
 *
 * @author ADMIN
 */
public class PhongBan {
    private String TenPhongban;
    private int idPB;

    public PhongBan() {
    }

    public String getTenPhongban() {
        return TenPhongban;
    }

    public void setTenPhongban(String TenPhongban) {
        this.TenPhongban = TenPhongban;
    }

    public int getIdPB() {
        return idPB;
    }

    public void setIdPB(int idPB) {
        this.idPB = idPB;
    }
    
    @Override
    public String toString(){
        return getTenPhongban();
    }
    
    
}
