/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.Luong;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class LuongDao extends HRMDAO<Luong, Integer>{
    String INSERT_SQL = "INSERT INTO bangluong VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Nhanvien SET  tonggiolam = ?,bacLuong = ?,luongcb = ?, phucap = ?,ungluong = ?, KTKL = ?, tangca = ?, luongnhan = ?, baohiem = ?, thue = ? WHERE idbl = ?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";

    @Override
    public void insert(Luong e) {
      JdbcHelp.update(INSERT_SQL, e.getManv(),e.getGio(),e.getBacLuong(),e.getLuongCB(),e.getPhuCap(),e.getUngLuong(),e.getKTKL(),e.getTangCa(),e.getLuong(),e.getMaKC(),e.getBaohiem(),e.getThue(),e.getNgayCham());
    }

    @Override
    public void update(Luong e) {
        JdbcHelp.update(UPDATE_SQL, e.getGio(), e.getBacLuong(), e.getLuongCB(),e.getPhuCap(), e.getUngLuong(),e.getKTKL(),e.getTangCa(),e.getLuong(),e.getBaohiem(),e.getThue(),e.getId());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelp.update(DELETE_SQL,id);
    }

    @Override
    public List<Luong> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Luong selectById(Integer id) {
       List<Luong> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<Luong> selectBySql(String sql, Object... args) {
         List<Luong> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql, args);
            while(rs.next()){
                Luong e = new Luong();
                e.setId(rs.getInt("idbl"));
                e.setMaKC(rs.getInt("makycong"));
                e.setManv(rs.getString("manv"));
                e.setGio(rs.getInt("tonggiolam"));
                e.setBacLuong(rs.getFloat("bacluong"));
                e.setLuongCB(rs.getFloat("luongcb"));
                e.setPhuCap(rs.getFloat("phucap"));
                e.setUngLuong(rs.getFloat("ungluong"));
                e.setKTKL(rs.getFloat("ktkl"));
                e.setTangCa(rs.getFloat("tangca"));
                e.setLuong(rs.getFloat("luongnhan"));
                e.setBaohiem(rs.getFloat("baohiem"));
                e.setThue(rs.getFloat("thue"));
                e.setNgayCham(rs.getDate("NgayChamCong"));
                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<Luong> selectBYKC(int kc){
        String sql = "Select * from bangluong where makycong = ?";
        return selectBySql(sql, kc);
    }
    public List<Luong> selectBYNV(String manv){
        String sql = "Select * from bangluong where MaNV = ?";
        return selectBySql(sql, manv);
    }
}
