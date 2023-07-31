/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.ChucVu;
import com.app.hrm.Entity.LoaiCa;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class LoaiCaDao extends HRMDAO<LoaiCa, Integer>{
     String INSERT_SQL = "INSERT INTO LoaiCa(tenloaica,hso) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE chucvu SET  tenloaica=?, hso=? WHERE idlc = ?";
    String DELETE_SQL = "DELETE FROM loaica WHERE idlc = ?";
    String SELECT_ALL_SQL = "SELECT * FROM loaica";
    String SELECT_BY_ID_SQL = "SELECT * FROM loaica WHERE idlc = ?";

    @Override
    public void insert(LoaiCa e) {
        JdbcHelp.update(INSERT_SQL, e.getTenCa(), e.getHeSo());
    }

    @Override
    public void update(LoaiCa e) {
       JdbcHelp.update(UPDATE_SQL, e.getTenCa(),e.getHeSo(),e.getId());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiCa> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LoaiCa selectById(Integer id) {
         List<LoaiCa> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<LoaiCa> selectBySql(String sql, Object... args) {
        List<LoaiCa> list = new ArrayList<>();
        try {
            ResultSet rs =  JdbcHelp.query(sql, args);
            while(rs.next()){
                LoaiCa cv = new LoaiCa();
                cv.setId(rs.getInt("idlc"));
                cv.setTenCa(rs.getString("tenloaica"));
                cv.setHeSo(rs.getFloat("hso"));
                list.add(cv);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
}
