/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.ChamCong;
import com.app.hrm.Entity.KyCong;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class KyCongDao extends HRMDAO<KyCong, Integer>{
    String INSERT_SQL = "INSERT INTO kycong VALUES(?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE kycong SET   thang= ?,nam = ?,khoa=?,ngaytinhcong=?,mapb=?, trangthai=? WHERE MaKyCong = ?";
    String DELETE_SQL = "DELETE FROM kycong WHERE Makycong = ?";
    String SELECT_ALL_SQL = "SELECT * FROM kycong";
    String SELECT_BY_ID_SQL = "SELECT * FROM kycong WHERE Makycong = ?";

    @Override
    public void insert(KyCong e) {
        JdbcHelp.update(INSERT_SQL, e.getThang(), e.getNam(), e.isKhoa(),e.getNgayTinh(),e.getMaPB(),e.isTrangThai());
    }

    @Override
    public void update(KyCong e) {
        JdbcHelp.update(UPDATE_SQL, e.getThang(), e.getNam(), e.isKhoa(),e.getNgayTinh(),e.getMaPB(),e.isTrangThai() ,e.getMaKC());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<KyCong> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KyCong selectById(Integer id) {
       List<KyCong> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<KyCong> selectBySql(String sql, Object... args) {
        List<KyCong> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql, args);
            while(rs.next()){
                KyCong e = new KyCong();
                e.setMaKC(rs.getInt("Makycong"));
                e.setThang(rs.getInt("thang"));
                e.setNam(rs.getInt("nam"));
                e.setKhoa(rs.getBoolean("khoa"));
                e.setNgayTinh(rs.getDate("ngaytinhcong"));
                e.setMaPB(rs.getInt("mapb"));
                e.setTrangThai(rs.getBoolean("trangthai"));
                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    
    public List<KyCong> selectByThangNam(int nam, int thang){
        String sql = "select * from kycong where nam = ? and thang = ?";
        return this.selectBySql(sql, nam,thang);
    }
    
    public List<Object> selectNam(){
        String sql = "select distinct nam from KYCONG order by nam desc";
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
        String sql = "select distinct thang from KYCONG where nam = ? order by thang asc";
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
