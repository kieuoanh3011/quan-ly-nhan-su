/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.PhuCap;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class PhuCapDao extends HRMDAO<PhuCap, Integer>{
    String INSERT_SQL = "INSERT INTO PhuCap(MaNV,tenpc,sotien) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE phucap SET  manv=?, tenpc=?,sotien = ? WHERE idpc = ?";
    String DELETE_SQL = "DELETE FROM phucap WHERE idpc = ?";
    String SELECT_ALL_SQL = "SELECT * FROM phucap";
    String SELECT_BY_ID_SQL = "SELECT * FROM phucap WHERE idpc = ?";
    
    @Override
    public void insert(PhuCap e) {
       JdbcHelp.update(INSERT_SQL, e.getMaNV(), e.getLoaiPC(),e.getTien());
    }

    @Override
    public void update(PhuCap e) {
        JdbcHelp.update(UPDATE_SQL, e.getMaNV(),e.getLoaiPC(),e.getTien(),e.getId());
    }

    @Override
    public void delete(Integer id) {
         JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<PhuCap> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhuCap selectById(Integer id) {
      List<PhuCap> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<PhuCap> selectBySql(String sql, Object... args) {
        List<PhuCap> list = new ArrayList<>();
        try {
            ResultSet rs =  JdbcHelp.query(sql, args);
            while(rs.next()){
                PhuCap cv = new PhuCap();
                cv.setId(rs.getInt("idpc"));
                cv.setMaNV(rs.getString("manv"));
                cv.setLoaiPC(rs.getString("tenpc"));
                cv.setTien(rs.getFloat("sotien"));
                list.add(cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public double sumByMaNV(String manv) {
        String sql = "select SUM(SoTien) from PhuCap where MaNV = ? ";
        try {
              if( JdbcHelp.value(sql, manv) == null){
                  return 0;
              }else{
                  return (double) JdbcHelp.value(sql, manv);
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
