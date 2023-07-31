/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.ChucVu;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChucVuDao extends HRMDAO<ChucVu, Integer>{
    String INSERT_SQL = "INSERT INTO chucvu(tencv,bacluong) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE chucvu SET  tencv=?, bacluong=? WHERE idcv = ?";
    String DELETE_SQL = "DELETE FROM chucvu WHERE idcv = ?";
    String SELECT_ALL_SQL = "SELECT * FROM chucvu";
    String SELECT_BY_ID_SQL = "SELECT * FROM chucvu WHERE idcv = ?";
    String SELECT_BY_KEY = "select * from chucvu where tencv like ?";

    @Override
    public void insert(ChucVu e) {
        JdbcHelp.update(INSERT_SQL, e.getTenCV(), e.getBacLuong());
    }

    @Override
    public void update(ChucVu e) {
        JdbcHelp.update(UPDATE_SQL, e.getTenCV(),e.getBacLuong(),e.getIdCV());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<ChucVu> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChucVu selectById(Integer id) {
         List<ChucVu> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<ChucVu> selectBySql(String sql, Object... args) {
        List<ChucVu> list = new ArrayList<>();
        try {
            ResultSet rs =  JdbcHelp.query(sql, args);
            while(rs.next()){
                ChucVu cv = new ChucVu();
                cv.setIdCV(rs.getInt("idcv"));
                cv.setTenCV(rs.getString("tencv"));
                cv.setBacLuong(rs.getFloat("bacluong"));
                list.add(cv);
            }
        } catch (Exception e) {
        }
        return list;
    }
     public List<ChucVu> selectBykey(String key) {
      return this.selectBySql(SELECT_BY_KEY, key);
    }
}
