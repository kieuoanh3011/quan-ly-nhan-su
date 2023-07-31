/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.ChamCong;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChamCongDao extends HRMDAO<ChamCong, Integer>{
    String INSERT_SQL = "INSERT INTO chamcong VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE chamcong SET giovao = ?,phutvao = ?,giove=?,phutve=?,ngay=?,sogiolam=? WHERE id = ?";
    String DELETE_SQL = "DELETE FROM chamcong WHERE id = ?";
    String SELECT_ALL_SQL = "SELECT * FROM chamcong";
    String SELECT_BY_ID_SQL = "SELECT * FROM chamcong WHERE id= ?";


    @Override
    public void insert(ChamCong e) {
        JdbcHelp.update(INSERT_SQL, e.getMaNV(),e.getNgay(),e.getGioiVao(),e.getPhutVao(),e.getGioVe(),e.getPhutVe(),e.getSoGioLam());
    }

    @Override
    public void update(ChamCong e) {
        JdbcHelp.update(UPDATE_SQL,e.getGioiVao(),e.getPhutVao(),e.getGioVe(),e.getPhutVe(),e.getNgay(),e.getSoGioLam(),e.getId());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<ChamCong> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChamCong selectById(Integer id) {
        List<ChamCong> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<ChamCong> selectBySql(String sql, Object... args) {
       List<ChamCong> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql, args);
            while(rs.next()){
                ChamCong e = new ChamCong();
              
                e.setId(rs.getInt("id"));
                e.setMaNV(rs.getString("MaNV"));
       
                e.setGioiVao(rs.getInt("giovao"));
                e.setPhutVao(rs.getInt("phutvao"));
                e.setGioVe(rs.getInt("giove"));
                e.setPhutVe(rs.getInt("phutve"));
                e.setNgay(rs.getDate("ngay"));
                e.setSoGioLam(rs.getInt("sogiolam"));
                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<ChamCong> selectAllByThangNam(int nam,int thang, String manv){
        String sql = "select * from ChamCong where YEAR(Ngay) = ? and MONTH(Ngay) = ? and MaNV = ?";
        return selectBySql(sql, nam,thang,manv);
    }
    
    public void updateByDay(ChamCong e) {
        String sql = "UPDATE chamcong giovao = ?,phutvao = ?,giove=?,phutve=?,ngay=?,sogiolam= WHERE ngay = ? and manv = ?";
        JdbcHelp.update(sql,e);
    }
    
     public ChamCong getID( String manv, int nam, int thang) {
         String sql = "Select * from chamcong where manv = ? and YEAR(Ngay) = ? and MONTH(Ngay) = ? order by Ngay desc";
        List<ChamCong> list = selectBySql(sql,manv,nam,thang);
        if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }
     
     public List<Object> selectNam(){
        String sql = "select distinct YEAR(ngay) from ChamCong order by YEAR(ngay) desc";
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
        String sql = "select distinct MONTH(Ngay) from ChamCong where YEAR(Ngay) = ? order by MONTH(Ngay) asc";
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
    }
    

