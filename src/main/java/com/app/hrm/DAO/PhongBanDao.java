/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.PhongBan;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class PhongBanDao extends HRMDAO<PhongBan, Integer>{
    
    String INSERT_SQL = "INSERT INTO phongban(tenPB) VALUES(?)";
    String UPDATE_SQL = "UPDATE phongban SET tenpb = ? where idpb = ? ";
    String DELETE_SQL = "DELETE FROM phongban WHERE idpb = ?";
    String SELECT_ALL_SQL = "SELECT * FROM phongban";
     String SELECT_BY_ID_SQL = "SELECT * FROM phongban WHERE idpb = ?";
    String SELECT_BY_KEY = "select * from phongban where tenpb like ?";

    @Override
    public void insert(PhongBan e) {
        JdbcHelp.update(INSERT_SQL , e.getTenPhongban());
    }

    @Override
    public void update(PhongBan e) {
        JdbcHelp.update(UPDATE_SQL, e.getTenPhongban(), e.getIdPB());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<PhongBan> selectAll() {
       return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<PhongBan> selectBykey(String key) {
      return this.selectBySql(SELECT_BY_KEY, key);
    }

    @Override
    protected List<PhongBan> selectBySql(String sql, Object... args) {
        List<PhongBan> list = new ArrayList<>();
        try {
            ResultSet rs =  JdbcHelp.query(sql, args);
            while(rs.next()){
                PhongBan pb = new PhongBan();
                pb.setIdPB(rs.getInt("idpb"));
                pb.setTenPhongban(rs.getString("tenpb"));
                list.add(pb);
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public PhongBan selectById(Integer id) {
       List<PhongBan> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }
    
}
