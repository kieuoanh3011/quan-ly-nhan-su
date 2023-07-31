/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.BaoHiem;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class BaoHiemDao extends HRMDAO<BaoHiem, String>{
     String INSERT_SQL = "INSERT INTO baohiem VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE baohiem SET  sobh = ?,ngaycap = ?,noicap=?,noikham=?, tien = ? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM baohiem WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM baohiem";
    String SELECT_BY_ID_SQL = "SELECT * FROM baohiem WHERE MaNV = ?";

    @Override
    public void insert(BaoHiem e) {
        JdbcHelp.update(INSERT_SQL, e.getSoBH(),e.getNgayCap(),e.getNoiCap(),e.getNoiKham(),e.getMaNV(),e.getTien());
    }

    @Override
    public void update(BaoHiem e) {
        JdbcHelp.update(UPDATE_SQL, e.getSoBH(),e.getNgayCap(),e.getNoiCap(),e.getNoiKham(),e.getTien(),e.getMaNV());
            }

    @Override
    public void delete(String id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<BaoHiem> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public BaoHiem selectById(String id) {
        List<BaoHiem> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<BaoHiem> selectBySql(String sql, Object... args) {
       List<BaoHiem> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql, args);
            while(rs.next()){
                BaoHiem e  = new BaoHiem();
                e.setSoBH(rs.getString("sobh"));
                e.setNgayCap(rs.getDate("ngaycap"));
                e.setNoiCap(rs.getString("noicap"));
                e.setNoiKham(rs.getString("noikham"));
                e.setMaNV(rs.getString("manv"));
                e.setTien(rs.getFloat("tien"));
                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
