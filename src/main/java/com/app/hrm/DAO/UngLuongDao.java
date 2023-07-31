/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.UngLuong;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class UngLuongDao extends HRMDAO<UngLuong, Integer>{
    final String INSERT_SQL = "INSERT INTO UngLuong(NgayUng,SoTien,TrangThai,MaNV) values(?,?,?,?)";
    final String UPDATE_SQL = "UPDATE UngLuong SET MaNV=?, Ngayung= ?,SoTien=?,TrangThai=? WHERE IDUL = ?";
    final String DELETE_SQL = "DELETE FROM UngLuong WHERE IDUL = ?" ;
    final String SELECT_ALL_SQL = "SELECT * FROM UngLuong";
    final String SELECT_BY_ID_SQL = "SELECT * FROM UngLuong where IDUL = ?";

    @Override
    public void insert(UngLuong entity) {
        JdbcHelp.update(INSERT_SQL, entity.getNgayUng(),entity.getSoTien(),entity.isTrangThai(),entity.getMaNV());
    }

    @Override
    public void update(UngLuong entity) {
        JdbcHelp.update(UPDATE_SQL ,entity.getMaNV(),entity.getNgayUng(),entity.getSoTien(),entity.isTrangThai(),entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<UngLuong> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public UngLuong selectById(Integer id) {
       List<UngLuong> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(id == null){
           return null;
       }else{
           return list.get(0);
       }
    }

    @Override
    public List<UngLuong> selectBySql(String sql, Object... args) {
        List<UngLuong> list = new ArrayList<>();
        try {
           ResultSet rs = JdbcHelp.query(sql, args);
           while(rs.next()){
               UngLuong entity = new UngLuong();
               entity.setID(rs.getInt("IDUL"));
               entity.setNgayUng(rs.getDate("Ngayung"));
               entity.setSoTien(rs.getFloat("SoTien"));
               entity.setTrangThai(rs.getBoolean("TrangThai"));
               entity.setMaNV(rs.getString("MaNV"));

               list.add(entity);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;   
    }
    
    public double sumByMaNV(String manv,int nam, int thang){
        String sql = "select SUM(SoTien) from UngLuong where MaNV = ? and YEAR(NgayUng) = ? and MONTH(NgayUng) = ?";
        try {
              if( JdbcHelp.value(sql, manv,nam,thang) == null){
                  return 0;
              }else{
                  return (double) JdbcHelp.value(sql, manv,nam,thang);
              }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<Object> selectNam(){
        String sql = "select distinct YEAR(NgayUng) from UngLuong order by YEAR(NgayUng) desc";
        List<Object> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Object> selectThang(Object nam){
        String sql = "select distinct MONTH(ngayung) from ungluong where YEAR(Ngayung) = ? order by MONTH(ngayung) asc";
        List<Object> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql,nam);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
      public List<UngLuong> selectByThangNam(int nam, int thang){
        String sql = "select * from ungluong where YEAR(Ngayung) = ? and MONTH(ngayung) = ?";
        return this.selectBySql(sql, nam,thang);
    }
}
