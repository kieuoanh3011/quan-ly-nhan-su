/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.ChamCongDao;
import com.app.hrm.DAO.KyCongCTDao;
import com.app.hrm.DAO.KyCongDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.Entity.ChamCong;
import com.app.hrm.Entity.KyCong;
import com.app.hrm.Entity.KyCongCT;
import com.app.hrm.Entity.NhanVien;
import static com.app.hrm.UI.BangCong.nam;
import static com.app.hrm.UI.BangCong.thang;
import static com.app.hrm.UI.BangCong.idkc;
import static com.app.hrm.UI.BangCongNgay.giatri;
import com.app.hrm.Utils.MsgBox;
import java.awt.Window;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class BangChamCongJDialog extends javax.swing.JDialog {

    KyCongCTDao dao = new KyCongCTDao();
    NhanVienDao nvdao = new NhanVienDao();
    ChamCongDao ccdao = new ChamCongDao();
    int Mont = thang;
    int Yeas = nam;
    int ma = idkc;
    public static int row;
    public static int col;
    public static String manv;
    Mainform main;

    /**
     * Creates new form BangChamCongForm
     */
    public BangChamCongJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initTable();
        filltable();
        setLocationRelativeTo(null);
        setSize(1500, 1000);

    }

    void initTable() {
        ArrayList<String> listday = new ArrayList<String>();
        YearMonth yearMonthObject = YearMonth.of(Yeas, Mont);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        for (int j = 1; j <= daysInMonth; j++) {
            LocalDate newDate = LocalDate.of(Yeas, Mont, j);

            switch (newDate.getDayOfWeek().toString()) {
                case "SUNDAY":
                    listday.add("CN");
                    break;
                default:
                    listday.add("" + j);
                    break;
            }

        }
        DefaultTableModel model = (DefaultTableModel) tblNgay.getModel();
        Vector vctCol = new Vector();

        vctCol.add("Mã Chấm công");
        vctCol.add("Mã nhân viên");
        vctCol.add("Họ tên");
        for (String st : listday) {
            vctCol.add(st);
        }
        vctCol.add("Tổng giờ làm");
        model.setColumnIdentifiers(vctCol);
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblNgay.getModel();
        model.setRowCount(0);
        List<KyCongCT> list = dao.selectByIDAll(ma);
        YearMonth yearMonthObject = YearMonth.of(Yeas, Mont);
        int day = yearMonthObject.lengthOfMonth();
        switch (day) {
            case 31:
                for (KyCongCT kc : list) {
                    String ten = nvdao.selectById(kc.getMaNV()).getHoTen();
                    Object[] row = {kc.getId(), kc.getMaNV(), ten, kc.getD1(), kc.getD2(), kc.getD3(), kc.getD4(), kc.getD5(), kc.getD6(), kc.getD7(), kc.getD8(), kc.getD9(), kc.getD10(),
                        kc.getD11(), kc.getD12(), kc.getD13(), kc.getD14(), kc.getD15(), kc.getD16(), kc.getD17(), kc.getD18(), kc.getD19(), kc.getD20(),
                        kc.getD21(), kc.getD22(), kc.getD23(), kc.getD24(), kc.getD25(), kc.getD26(), kc.getD27(), kc.getD28(), kc.getD29(), kc.getD30(), kc.getD31(), kc.getTG()};
                    model.addRow(row);
                }
                break;
            case 30:
                for (KyCongCT kc : list) {
                    String ten = nvdao.selectById(kc.getMaNV()).getHoTen();
                    Object[] row = {kc.getId(), kc.getMaNV(), ten, kc.getD1(), kc.getD2(), kc.getD3(), kc.getD4(), kc.getD5(), kc.getD6(), kc.getD7(), kc.getD8(), kc.getD9(), kc.getD10(),
                        kc.getD11(), kc.getD12(), kc.getD13(), kc.getD14(), kc.getD15(), kc.getD16(), kc.getD17(), kc.getD18(), kc.getD19(), kc.getD20(),
                        kc.getD21(), kc.getD22(), kc.getD23(), kc.getD24(), kc.getD25(), kc.getD26(), kc.getD27(), kc.getD28(), kc.getD29(), kc.getD30(), kc.getTG()};
                    model.addRow(row);
                }
                break;
            case 29:
                for (KyCongCT kc : list) {
                    String ten = nvdao.selectById(kc.getMaNV()).getHoTen();
                    Object[] row = {kc.getId(), kc.getMaNV(), ten, kc.getD1(), kc.getD2(), kc.getD3(), kc.getD4(), kc.getD5(), kc.getD6(), kc.getD7(), kc.getD8(), kc.getD9(), kc.getD10(),
                        kc.getD11(), kc.getD12(), kc.getD13(), kc.getD14(), kc.getD15(), kc.getD16(), kc.getD17(), kc.getD18(), kc.getD19(), kc.getD20(),
                        kc.getD21(), kc.getD22(), kc.getD23(), kc.getD24(), kc.getD25(), kc.getD26(), kc.getD27(), kc.getD28(), kc.getD29(), kc.getTG()};
                    model.addRow(row);
                }
                break;
            case 28:
                for (KyCongCT kc : list) {
                    String ten = nvdao.selectById(kc.getMaNV()).getHoTen();
                    Object[] row = {kc.getId(), kc.getMaNV(), ten, kc.getD1(), kc.getD2(), kc.getD3(), kc.getD4(), kc.getD5(), kc.getD6(), kc.getD7(), kc.getD8(), kc.getD9(), kc.getD10(),
                        kc.getD11(), kc.getD12(), kc.getD13(), kc.getD14(), kc.getD15(), kc.getD16(), kc.getD17(), kc.getD18(), kc.getD19(), kc.getD20(),
                        kc.getD21(), kc.getD22(), kc.getD23(), kc.getD24(), kc.getD25(), kc.getD26(), kc.getD27(), kc.getD28(), kc.getTG()};
                    model.addRow(row);
                }
                break;

        }

    }

    void them() {
        DefaultTableModel model = (DefaultTableModel) tblNgay.getModel();
        List<NhanVien> list = nvdao.selectAll();

        for (NhanVien nv : list) {
            KyCongCT kc = new KyCongCT();
            kc.setId(ma);
            kc.setMaNV(nv.getMaNV());
            try {
                dao.insert(kc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filltable();
    }

    void sua() {

        try {
            for (int i = 0; i < tblNgay.getRowCount(); i++) {
                int tong = 0;
                int id = (int) tblNgay.getValueAt(i, 0);
                String manv = (String) tblNgay.getValueAt(i, 1);
                KyCongCT kc = dao.selectByIDMANV(id, manv);
                List<ChamCong> list = ccdao.selectAllByThangNam(nam, thang, manv);
                for (ChamCong cc : list) {

                    Timestamp ts = new Timestamp(cc.getNgay().getTime());
                    int a = ts.getDate();

                    switch (a) {
                        case 1 -> {
                            kc.setD1(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 2 -> {
                            kc.setD2(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 3 -> {
                            kc.setD3(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 4 -> {
                            kc.setD4(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 5 -> {
                            kc.setD5(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 6 -> {
                            kc.setD6(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 7 -> {
                            kc.setD7(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 8 -> {
                            kc.setD8(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 9 -> {
                            kc.setD9(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 10 -> {
                            kc.setD10(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 11 -> {
                            kc.setD11(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 12 -> {
                            kc.setD12(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 13 -> {
                            kc.setD13(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 14 -> {
                            kc.setD14(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 15 -> {
                            kc.setD15(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 16 -> {
                            kc.setD16(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 17 -> {
                            kc.setD17(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 18 -> {
                            kc.setD18(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 19 -> {
                            kc.setD19(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 20 -> {
                            kc.setD20(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 21 -> {
                            kc.setD21(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 22 -> {
                            kc.setD22(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 23 -> {
                            kc.setD23(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 24 -> {
                            kc.setD24(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 25 -> {
                            kc.setD25(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 26 -> {
                            kc.setD26(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 28 -> {
                            kc.setD28(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 29 -> {
                            kc.setD29(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 30 -> {
                            kc.setD30(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }
                        case 31 -> {
                            kc.setD31(cc.getSoGioLam());
                            tong += cc.getSoGioLam();
                        }

                    }
                    kc.setTG(tong);
                }

                try {
                    dao.update(kc);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MsgBox.alert(this, "Lưu thành công");
        filltable();

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
        btnTinh = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNgay = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setAutoscrolls(true);
        jPanel1.setMaximumSize(new java.awt.Dimension(3700, 3700));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        btnTinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Add.png"))); // NOI18N
        btnTinh.setText("Tính công");
        btnTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhActionPerformed(evt);
            }
        });
        jPanel1.add(btnTinh);

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Text.png"))); // NOI18N
        btnThem.setText("Mới");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel1.add(btnThem);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Left.png"))); // NOI18N
        jButton2.setText("Quay Lại");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        tblNgay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tblNgay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNgayMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNgayMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNgay);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhActionPerformed
        sua();
    }//GEN-LAST:event_btnTinhActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblNgayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNgayMouseClicked


    }//GEN-LAST:event_tblNgayMouseClicked

    private void tblNgayMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNgayMousePressed
        if (evt.getClickCount() == 2) {
            row = tblNgay.getSelectedRow();
            col = tblNgay.getSelectedColumn();
            manv = (String) tblNgay.getValueAt(row, 1);
            new BangCongNgay(main, true).setVisible(true);

        }
    }//GEN-LAST:event_tblNgayMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BangChamCongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BangChamCongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BangChamCongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BangChamCongJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BangChamCongJDialog dialog = new BangChamCongJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTinh;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNgay;
    // End of variables declaration//GEN-END:variables
}
