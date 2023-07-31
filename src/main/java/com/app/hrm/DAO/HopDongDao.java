/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.HopDong;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class HopDongDao extends HRMDAO<HopDong, Integer>{
     final String INSERT_SQL = "INSERT INTO HopDong(MaNV,NgayBatDau,NgayKetThuc,NgayKy,NoiDung,LanKy,luongcb) values(?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HopDong SET MaNV = ?,NgayBatDau = ?,NgayKetThuc= ?,ngayKy=?,NoiDung=?,LanKy=?,luongcb=? WHERE SoHD = ?";
    final String DELETE_SQL = "DELETE FROM HopDong WHERE soHD = ?" ;
    final String SELECT_ALL_SQL = "SELECT * FROM HopDong";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HopDong where SoHD = ?";
    
    @Override
    public void insert(HopDong e) {
        JdbcHelp.update(INSERT_SQL,e.getMaNV(),e.getNgayBD(),e.getNgayKT(),e.getNgayKy(),e.getNoidung(),e.getLanKy(),e.getLuongCB());
    }

    @Override
    public void update(HopDong e) {
        JdbcHelp.update(UPDATE_SQL, e.getMaNV(),e.getNgayBD(),e.getNgayKT(),e.getNgayKy(),e.getNoidung(),e.getLanKy(),e.getLuongCB(),e.getSoHD());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<HopDong> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HopDong selectById(Integer id) {
          List<HopDong> list = selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
            
        }
        return list.get(0);  
    
    }

    @Override
    protected List<HopDong> selectBySql(String sql, Object... args) {
       List<HopDong> list = new ArrayList<>();
        try {
           ResultSet rs = JdbcHelp.query(sql, args);
           while(rs.next()){
               HopDong entity = new HopDong();
               entity.setSoHD(rs.getInt("SoHD"));
               entity.setMaNV(rs.getString("manv"));
               entity.setNgayBD(rs.getDate("NgayBatDau"));
               entity.setNgayKT(rs.getDate("NgayKetThuc"));
               entity.setNgayKy(rs.getDate("NgayKy"));
               entity.setNoidung(rs.getString("NoiDung"));
               entity.setLanKy(rs.getInt("LanKy"));
                
               entity.setLuongCB(rs.getFloat("luongcb"));
               list.add(entity);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list; 
    }
    
    public HopDong selectByMaNV(String id) {
       String sql = "SELECT * FROM HopDong where MaNV = ?";
          List<HopDong> list = selectBySql(sql, id);
        if(list.isEmpty()){
            return null;
            
        }
        return list.get(0);  
    
    }
}
