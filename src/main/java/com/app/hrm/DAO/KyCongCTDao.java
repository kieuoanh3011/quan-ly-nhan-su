/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.KyCongCT;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class KyCongCTDao extends HRMDAO<KyCongCT, Integer>{
    String INSERT_SQL = "INSERT INTO kycongchitiet(makycong,manv) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE kycongchitiet SET   d1 = ?, d2=?, d3=?, d4 =?, d5=?, d6=?, d7=?, d8=?, d9=?, d10=?,"
            + "d11=?, d12=?, d13=?, d14=?, d15=?, d16=?, d17 = ?, d18 = ?, d19=?, d20 = ?, d21=?, d22=?, d23=?, d24=?,"
            + "d25=?, d26=?, d27=?, d28=?, d29=?, d30=?, d31=?, TONGGIOLAM = ? WHERE Makycong = ? and manv = ?";
    String DELETE_SQL = "DELETE FROM kycongchitiet WHERE Makycong = ?";
    String SELECT_ALL_SQL = "SELECT * FROM kycongchitiet";
    String SELECT_BY_ID_SQL = "SELECT * FROM kycongchitiet WHERE Makycong = ?";
    @Override
    public void insert(KyCongCT e) {
         JdbcHelp.update(INSERT_SQL,e.getId(),e.getMaNV());
    }

    @Override
    public void update(KyCongCT e) {
        JdbcHelp.update(UPDATE_SQL,e.getD1(),e.getD2(),e.getD3(),e.getD4(),e.getD5(),e.getD6(),e.getD7(),e.getD8(),e.getD9(),e.getD10(),
                e.getD11(),e.getD12(),e.getD13(),e.getD14(),e.getD15(),e.getD16(),e.getD17(),e.getD18(),e.getD19(),e.getD20(),
                e.getD21(),e.getD22(),e.getD23(),e.getD24(),e.getD25(),e.getD26(),e.getD27(),e.getD28(),e.getD29(),e.getD30(),e.getD31(),e.getTG(), e.getId(),e.getMaNV());
    }

    @Override
    public void delete(Integer id) {
       JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<KyCongCT> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KyCongCT selectById(Integer id) {
        
        List<KyCongCT> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<KyCongCT> selectBySql(String sql, Object... args) {
       List<KyCongCT> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql, args);
            while(rs.next()){
                KyCongCT e  = new KyCongCT();
                e.setId(rs.getInt("makycong"));
                e.setMaNV(rs.getString("manv"));
                e.setD1(rs.getInt("d1"));
                e.setD2(rs.getInt("d2"));
                e.setD3(rs.getInt("d3"));
                e.setD4(rs.getInt("d4"));
                e.setD5(rs.getInt("d5"));
                e.setD6(rs.getInt("d6"));
                e.setD7(rs.getInt("d7"));
                e.setD8(rs.getInt("d8"));
                e.setD9(rs.getInt("d9"));
                e.setD10(rs.getInt("d10"));
                e.setD11(rs.getInt("d11"));
                e.setD12(rs.getInt("d12"));
                e.setD13(rs.getInt("d13"));
                e.setD14(rs.getInt("d14"));
                e.setD15(rs.getInt("d15"));
                e.setD16(rs.getInt("d16"));
                e.setD17(rs.getInt("d17"));
                e.setD18(rs.getInt("d18"));
                e.setD19(rs.getInt("d19"));
                e.setD20(rs.getInt("d20"));
                e.setD21(rs.getInt("d21"));
                e.setD22(rs.getInt("d22"));
                e.setD23(rs.getInt("d23"));
                e.setD24(rs.getInt("d24"));
                e.setD25(rs.getInt("d25"));
                e.setD26(rs.getInt("d26"));
                e.setD27(rs.getInt("d27"));
                e.setD28(rs.getInt("d28"));
                e.setD29(rs.getInt("d29"));
                e.setD30(rs.getInt("d30"));
                e.setD31(rs.getInt("d31"));
                e.setTG(rs.getInt("tonggiolam"));
                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public KyCongCT selectByIDMANV(Integer id, String nv){
        String sql = "SELECT * FROM kycongchitiet WHERE Makycong = ? and manv=?";
        List<KyCongCT> list = selectBySql(sql, id,nv);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }
    
    public List<KyCongCT> selectByIDAll(Integer id) {
        
        return selectBySql(SELECT_BY_ID_SQL, id);
       
      
    }
}
