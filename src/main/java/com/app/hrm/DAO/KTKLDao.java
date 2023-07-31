/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.KTKL;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class KTKLDao extends HRMDAO<KTKL,Integer>{
    final String INSERT_SQL = "INSERT INTO Khen_KyLuat(MaKTKL,NoiDung,Ngay,MaNV,Loai,tien) values(?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE Khen_KyLuat SET NoiDung = ?,Ngay= ?,Loai=?,tien=?,manv=?,maKTKL = ?  WHERE id = ?";
    final String DELETE_SQL = "DELETE FROM Khen_KyLuat WHERE id = ?" ;
    final String SELECT_ALL_SQL = "SELECT * FROM Khen_KyLuat";
    final String SELECT_BY_ID_SQL = "SELECT * FROM Khen_KyLuat where id = ?";
    @Override
    public void insert(KTKL e) {
         JdbcHelp.update(INSERT_SQL,e.getSoKTKL(),e.getNoidung(),e.getNgay(),e.getMaNV(),e.isLoai(),e.getTien());
    }

    @Override
    public void update(KTKL e) {
        JdbcHelp.update(UPDATE_SQL, e.getNoidung(),e.getNgay(),e.isLoai(),e.getTien(),e.getMaNV(),e.getSoKTKL(),e.getId());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<KTKL> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KTKL selectById(Integer id) {
        List<KTKL> list = selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    protected List<KTKL> selectBySql(String sql, Object... args) {
        List<KTKL> list = new ArrayList<>();
        try {
           ResultSet rs = JdbcHelp.query(sql, args);
           while(rs.next()){
               KTKL entity = new KTKL();
               entity.setId(rs.getInt("ID"));
               entity.setSoKTKL(rs.getString("maKTKL"));
                entity.setNoidung(rs.getString("NoiDung"));
               entity.setNgay(rs.getDate("Ngay"));
               entity.setMaNV(rs.getString("MaNV"));
               entity.setLoai(rs.getBoolean("Loai"));
               entity.setTien(rs.getFloat("tien"));
               list.add(entity);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         return list;
    }
    public double sumByMaNV(String manv,int nam, int thang){
        String sql = "select SUM(Tien) from khen_kyluat where MaNV = ? and YEAR(Ngay) = ? and MONTH(Ngay) = ?";
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
        String sql = "select distinct YEAR(Ngay) from Khen_KyLuat order by YEAR(Ngay) desc";
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
        String sql = "select distinct MONTH(ngay) from Khen_KyLuat where YEAR(Ngay) = ? order by MONTH(ngay) asc";
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
    
    public List<KTKL> selectByThangNam(int nam, int thang){
        String sql = "select * from Khen_KyLuat where YEAR(Ngay) = ? and MONTH(ngay) = ?";
        return this.selectBySql(sql, nam,thang);
    }
}
