/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.TangCa;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class TangCaDao extends HRMDAO<TangCa, Integer>{
      final String INSERT_SQL = "INSERT INTO TangCa(IDLC,manv,sogio,ngay,tien) values(?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE TangCa SET IDLC=?, MaNV=?,SoGio=?,ngay=?,tien=? WHERE IDTC = ?";
    final String DELETE_SQL = "DELETE FROM TangCa WHERE IDTC = ?" ;
    final String SELECT_ALL_SQL = "SELECT * FROM TangCa";
    final String SELECT_BY_ID_SQL = "SELECT * FROM TangCa where IDTC = ?";

    @Override
    public void insert(TangCa entity) {
        JdbcHelp.update(INSERT_SQL,entity.getIDloaiCa(),entity.getMaNV(),entity.getSoGio(),entity.getNgay(),entity.getTien());
    }

    @Override
    public void update(TangCa entity) {
        JdbcHelp.update(UPDATE_SQL, entity.getIDloaiCa(),entity.getMaNV(),entity.getSoGio(),entity.getNgay(),entity.getTien(),entity.getID());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<TangCa> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public TangCa selectById(Integer id) {
        List<TangCa> list = selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    public List<TangCa> selectBySql(String sql, Object... args) {
           List<TangCa> list = new ArrayList<>();
        try {
           ResultSet rs = JdbcHelp.query(sql, args);
           while(rs.next()){
               TangCa entity = new TangCa();
               entity.setID(rs.getInt("IDTC"));
               entity.setNgay(rs.getDate("Ngay"));
               entity.setSoGio(rs.getInt("SoGio"));
               entity.setMaNV(rs.getString("MaNV"));
               entity.setIDloaiCa(rs.getInt("IDLC"));
               entity.setTien(rs.getFloat("tien"));
               

               list.add(entity);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;    
    }
    public double sumByMaNV(String manv,int nam, int thang){
        String sql = "select SUM(Tien) from tangca where MaNV = ? and YEAR(Ngay) = ? and MONTH(Ngay) = ?";
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
        String sql = "select distinct YEAR(Ngay) from TangCa order by YEAR(Ngay) desc";
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
        String sql = "select distinct MONTH(ngay) from TangCa where YEAR(Ngay) = ? order by MONTH(ngay) asc";
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
    
      public List<TangCa> selectByThangNam(int nam, int thang){
        String sql = "select * from TangCa where YEAR(Ngay) = ? and MONTH(ngay) = ?";
        return this.selectBySql(sql, nam,thang);
    }

}
