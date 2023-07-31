/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Entity;

/**
 *
 * @author ADMIN
 */
public class TrinhDo {
    private int idTD;
    private String trinhDo;

    public TrinhDo() {
    }

    public int getIdTD() {
        return idTD;
    }

    public void setIdTD(int idTD) {
        this.idTD = idTD;
    }

    public String getTrinhDo() {
        return trinhDo;
    }

    public void setTrinhDo(String trinhDo) {
        this.trinhDo = trinhDo;
    }
    
    @Override
    public String toString(){
        return getTrinhDo();
    }
}
