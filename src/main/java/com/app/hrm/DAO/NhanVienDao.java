/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.DAO;

import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Utils.JdbcHelp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class NhanVienDao extends HRMDAO<NhanVien, String>{
    String INSERT_SQL = "INSERT INTO NhanVien(manv,hoten,gioitinh,ngaysinh,dienthoai,cccd,diachi,hinh,idpb,idcv,idtd,matkhautk) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE Nhanvien SET  HoTen = ?,GIoiTinh = ?,ngaysinh=?,dienthoai=?,CCCD=?,diachi=?,hinh=?,idpb=?,idcv=?,idtd=?,matkhautk=? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";
    String SELECT_BY_KEY = "select * from nhanvien where hoten like ?";


    @Override
    public void insert(NhanVien e) {
        JdbcHelp.update(INSERT_SQL, e.getMaNV(),e.getHoTen(),e.isGioiTinh(),e.getNgaySinh(),e.getDienThoai(),e.getCCCD(),
                e.getDiaChi(),e.getHinh(),e.getIDPB(),e.getIDCV(),e.getIDTD(),e.getMKTK());
    }

    @Override
    public void update(NhanVien e) {
        JdbcHelp.update(UPDATE_SQL,e.getHoTen(),e.isGioiTinh(),e.getNgaySinh(),e.getDienThoai(),e.getCCCD(),
                e.getDiaChi(),e.getHinh(),e.getIDPB(),e.getIDCV(),e.getIDTD(),e.getMKTK(),e.getMaNV());
    }

    @Override
    public void delete(String id) {
       JdbcHelp.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
      List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
       if(list.isEmpty()){
           return null;
       }
       return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
       List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs =JdbcHelp.query(sql, args);
            while(rs.next()){
                NhanVien e = new NhanVien();
                e.setMaNV(rs.getString("manv"));
                e.setHoTen(rs.getString("hoten"));
                e.setGioiTinh(rs.getBoolean("gioitinh"));
                e.setNgaySinh(rs.getDate("ngaysinh"));
                e.setDienThoai(rs.getString("dienthoai"));
                e.setCCCD(rs.getString("cccd"));
                e.setDiaChi(rs.getString("diachi"));
                e.setHinh(rs.getString("hinh"));
                e.setIDPB(rs.getInt("idpb"));
                e.setIDCV(rs.getInt("idcv"));
                e.setIDTD(rs.getInt("idtd"));
                e.setMKTK(rs.getString("matkhautk"));
                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<NhanVien> selectByKey(String key){
        
        return this.selectBySql(SELECT_BY_KEY, "%"+key+"%");
    }
    
    public List<NhanVien> selectHD(){
        String sql = "select * from nhanvien where manv not in (select manv from hopdong)";
        return this.selectBySql(sql);
    }
    
    public List<NhanVien> selectBH(){
        String sql = "select * from nhanvien where manv not in (select manv from baohiem)";
        return this.selectBySql(sql);
    }
    }
    
