/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.TrinhDo;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class TrinhDoDao extends HRMDAO<TrinhDo, Integer>{
    String INSERT_SQL = "INSERT INTO trinhdo(tenTD)  VALUES(?)";
    String UPDATE_SQL = "UPDATE trinhdo SET  tentd=? WHERE idtd = ?";
    String DELETE_SQL = "DELETE FROM trinhdo WHERE idtd = ?";
    String SELECT_ALL_SQL = "SELECT * FROM trinhdo";
    String SELECT_BY_ID_SQL = "SELECT * FROM trinhdo WHERE idtd = ?";
    String SELECT_BY_KEY = "select * from trinhdo where tentd like ?";

    @Override
    public void insert(TrinhDo e) {
        JdbcHelp.update(INSERT_SQL, e.getTrinhDo());
    }

    @Override
    public void update(TrinhDo e) {
        JdbcHelp.update(UPDATE_SQL, e.getTrinhDo(),e.getIdTD());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<TrinhDo> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public TrinhDo selectById(Integer id) {
        List<TrinhDo> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    
    }

    @Override
    protected List<TrinhDo> selectBySql(String sql, Object... args) {
        List<TrinhDo> list = new ArrayList<>();
        try {
            ResultSet rs =  JdbcHelp.query(sql, args);
            while(rs.next()){
                TrinhDo td = new TrinhDo();
                td.setIdTD(rs.getInt("idtd"));
                td.setTrinhDo(rs.getString("tentd"));
                list.add(td);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
      public List<TrinhDo> selectBykey(String key) {
      return this.selectBySql(SELECT_BY_KEY, key);
    }
}
