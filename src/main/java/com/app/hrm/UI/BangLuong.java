/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.BaoHiemDao;
import com.app.hrm.DAO.ChucVuDao;
import com.app.hrm.DAO.HopDongDao;
import com.app.hrm.DAO.KTKLDao;
import com.app.hrm.DAO.KyCongCTDao;
import com.app.hrm.DAO.KyCongDao;
import com.app.hrm.DAO.LuongDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.DAO.PhuCapDao;
import com.app.hrm.DAO.TangCaDao;
import com.app.hrm.DAO.UngLuongDao;
import com.app.hrm.Entity.ChamCong;
import com.app.hrm.Entity.KyCong;
import com.app.hrm.Entity.KyCongCT;
import com.app.hrm.Entity.Luong;
import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Entity.PhuCap;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class BangLuong extends javax.swing.JPanel {

    NhanVienDao nvdao = new NhanVienDao();
    KyCongCTDao ctdao = new KyCongCTDao();
    UngLuongDao uldao = new UngLuongDao();
    TangCaDao tcdao = new TangCaDao();
    KTKLDao ktkldao = new KTKLDao();
    PhuCapDao pcdao = new PhuCapDao();
    ChucVuDao cvdao = new ChucVuDao();
    HopDongDao hddao = new HopDongDao();
    KyCongDao kcdao = new KyCongDao();
    LuongDao dao = new LuongDao();
    BaoHiemDao bhdao = new BaoHiemDao();
    /**
     * Creates new form BangLuong
     */
    public BangLuong() {
        initComponents();
        fillKyCong();
    }

    void fillKyCong() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKC.getModel();
        model.removeAllElements();
        try {
            List<KyCong> list = kcdao.selectAll();
            for (KyCong kc : list) {
                model.addElement(kc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cboKC.setSelectedIndex(0);
    }

    void filltable() {
        KyCong kc = (KyCong) cboKC.getSelectedItem();
        if(kc != null){
        int makc = kc.getMaKC();
        DefaultTableModel model = (DefaultTableModel) tblBangLuong.getModel();
        model.setRowCount(0);
        try {
            List<Luong> list = dao.selectBYKC(makc);
            if(list.size()>0){
            btnTinh.setEnabled(false);
            }else{
             btnTinh.setEnabled(true);
            }
            for (Luong l : list) {
                String ten = nvdao.selectById(l.getManv()).getHoTen();
                Object[] row = {l.getId(), l.getManv(), ten, l.getGio(),l.getBacLuong(), l.getLuongCB(), l.getPhuCap(), l.getUngLuong(), l.getKTKL(), l.getTangCa(),l.getBaohiem(),l.getThue(), l.getLuong()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

    void sinhTD() {
        KyCong kc = (KyCong) cboKC.getSelectedItem();
        int makc = kc.getMaKC();
        DefaultTableModel model = (DefaultTableModel) tblBangLuong.getModel();
        model.setRowCount(0);
        
        try{
        List<KyCongCT> listkyct = ctdao.selectByIDAll(makc);
        for (KyCongCT kcct : listkyct) {
            
            Luong l = new Luong();
            NhanVien nv = nvdao.selectById(kcct.getMaNV());
            
            
            l.setMaKC(makc);
            l.setManv(kcct.getMaNV());
            l.setGio(kcct.getTG());
            
            float lcb = 0;
            if(hddao.selectByMaNV(kcct.getMaNV()) != null){
                lcb = hddao.selectByMaNV(kcct.getMaNV()).getLuongCB();
            }else{
                lcb = 0;
            }
            
            
            l.setLuongCB(lcb);
            
            float cv = (float) cvdao.selectById(nv.getIDCV()).getBacLuong();
            l.setBacLuong(cv);
            
            double pc = pcdao.sumByMaNV(nv.getMaNV());
            l.setPhuCap(pc);
            
            int nam = kcdao.selectById(makc).getNam();
            int thang = kcdao.selectById(makc).getThang();
            double ung =  uldao.sumByMaNV(nv.getMaNV(), nam, thang);
            l.setUngLuong(ung);
            
            l.setNgayCham(kcdao.selectById(makc).getNgayTinh());
            
            double ktkl = ktkldao.sumByMaNV(nv.getMaNV(), nam, thang);
            l.setKTKL(ktkl);
            
            double tc = tcdao.sumByMaNV(nv.getMaNV(), nam, thang);
            l.setTangCa(tc);
            
            double bh = 0;
            if(bhdao.selectById(nv.getMaNV()) != null){
                 bh = bhdao.selectById(nv.getMaNV()).getTien();
            }else{
                bh = 0;
            }
            
            l.setBaohiem(bh);
            
            double luong = ((lcb*cv)*kcct.getTG())+pc+tc-bh-ung;
            
            
            double thue = tinhThue(luong);
            
            l.setThue(thue);
            
            double luongNhan = luong - thue;
            l.setLuong(luongNhan);
            dao.insert(l);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        filltable();
        
        
    }
    
    double tinhThue(double luong){
        if(luong>=80000){
                return  luong * 0.35;
            }else if(luong>=50000 && luong<80000 ){
                return  luong * 0.3;
            }else if(luong>=30000 && luong<50000 ){
                return luong * 0.25;
            }else if(luong>=20000 && luong<30000 ){
                return luong * 0.2;
            }else if(luong>=10000 && luong<20000 ){
                return luong * 0.15;
            }else if(luong>=5000 && luong<10000 ){
                return luong * 0.1;
            }else{
                return 0;
            }
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pnlBangLuong = new javax.swing.JPanel();
        btnTinh = new javax.swing.JButton();
        cboKC = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangLuong = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 204));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bảng lương");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnTinh.setText("Tính công");
        btnTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhActionPerformed(evt);
            }
        });

        cboKC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKCActionPerformed(evt);
            }
        });

        jLabel5.setText("Ky công ");

        javax.swing.GroupLayout pnlBangLuongLayout = new javax.swing.GroupLayout(pnlBangLuong);
        pnlBangLuong.setLayout(pnlBangLuongLayout);
        pnlBangLuongLayout.setHorizontalGroup(
            pnlBangLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(cboKC, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTinh)
                .addContainerGap(525, Short.MAX_VALUE))
        );
        pnlBangLuongLayout.setVerticalGroup(
            pnlBangLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangLuongLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlBangLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboKC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTinh)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pnlBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblBangLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã nhân viên", "Họ tên", "Tổng giờ làm", "Hệ số lương", "Lương cơ bản", "Phụ cấp", "Ứng lương", "Khen thưởng - Kỹ luật", "Tăng ca", "Bảo hiểm", "Thuế", "Lương nhận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBangLuong);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboKCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKCActionPerformed
       
        filltable();
       
    }//GEN-LAST:event_cboKCActionPerformed

    private void btnTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhActionPerformed
        sinhTD();
    }//GEN-LAST:event_btnTinhActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTinh;
    private javax.swing.JComboBox<String> cboKC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlBangLuong;
    private javax.swing.JTable tblBangLuong;
    // End of variables declaration//GEN-END:variables
}
