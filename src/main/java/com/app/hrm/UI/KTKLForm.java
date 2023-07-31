/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.KTKLDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.Entity.KTKL;
import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Utils.Auth;

import com.app.hrm.Utils.MsgBox;
import com.app.hrm.Utils.Vaidation;
import com.app.hrm.Utils.XDate;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class KTKLForm extends javax.swing.JPanel {

    boolean check = true;
    NhanVienDao nvdao = new NhanVienDao();
    KTKLDao dao = new KTKLDao();

    public KTKLForm() {
        initComponents();
        CheckNut(true);
        filltable();
        fillTable2();
        fillNam();
        btnXoa.setEnabled(Auth.isBoss());
        txtID.setEnabled(false);
    }

    void CheckNut(boolean check) {
        btnThem.setEnabled(check);
        btnSua.setEnabled(check);
        btnXoa.setEnabled(check);
        btnLuu.setEnabled(!check);
        btnHuy.setEnabled(!check);
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblKTKL.getModel();

        model.setRowCount(0);

        try {
            List<KTKL> list = dao.selectAll();
            for (KTKL a : list) {
                String ten = nvdao.selectById(a.getMaNV()).getHoTen();
                Object[] row = {a.getId(),a.getSoKTKL(), a.getMaNV(), ten, a.getNoidung(),XDate.toString( a.getNgay()), a.isLoai() ? "Khen thưởng" : "Kỷ luật", a.getTien()};
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void fillTable2() {
        DefaultTableModel model2 = (DefaultTableModel) tblNhanVien.getModel();
        model2.setRowCount(0);
        try {
            List<NhanVien> list2 = nvdao.selectByKey(txtTimMa.getText());
            for (NhanVien nv : list2) {

                Object[] row2 = {nv.getMaNV(), nv.getHoTen()};
                model2.addRow(row2);
            }
        } catch (Exception e) {
        }
    }

    void setform(KTKL form) {
        txtMaKTKL.setText(form.getSoKTKL());
        txtNgay.setDate(form.getNgay());
        rdoKhen.setSelected(form.isLoai());
        String tien = String.valueOf(form.getTien());
        txtTien.setText(tien);
        txtMaNV.setText(form.getMaNV());
        txtNoiDung.setText(form.getNoidung());
        txtID.setText(String.valueOf(form.getId()));
    }

    KTKL getform() {
        KTKL form = new KTKL();
        form.setSoKTKL(txtMaKTKL.getText());
        form.setMaNV(txtMaNV.getText());
        form.setNgay(txtNgay.getDate());
        form.setLoai(rdoKhen.isSelected());
        if(!txtID.getText().trim().equals("")){
            form.setId(Integer.parseInt(txtID.getText()));
        }
        
        float tien = Float.valueOf(txtTien.getText());
        if (rdoKhen.isSelected()) {
            form.setTien(tien);
        } else {
            tien = tien * (-1);
            form.setTien(tien);
        }

        form.setNoidung(txtNoiDung.getText());
        return form;
    }

    void Them() {
        int kt = 0;
        KTKL ul = getform();

        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nv : list) {
            if (ul.getMaNV().equals(nv.getMaNV())) {
                kt++;
                break;
            }
        }
        if (kt > 0) {
            try {
                dao.insert(ul);
                this.filltable();
                this.clear();
                MsgBox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MsgBox.alert(this, "Mã nhân viên không hợp lệ");
        }
    }

    void Sua() {
        KTKL ul = getform();

        try {
            dao.update(ul);
            filltable();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Xoa() {
        KTKL ul = getform();

        try {
            dao.delete(ul.getId());
            filltable();
            this.clear();
            MsgBox.alert(this, "Xóa thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Nhân viên này không thể xóa");
        }
    }

    void clear() {
        txtMaKTKL.setText("");
        txtMaNV.setText("");
        txtID.setText("");
        txtNoiDung.setText("");
        txtTien.setText("");
        txtNgay.setDate(null);

    }

    public void DeleteNhanVien() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa")) {
            try {
                for (int row : tblKTKL.getSelectedRows()) {
                    int manv = (int) tblKTKL.getValueAt(row, 0);
                    dao.delete(manv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            filltable();
        }
    }

    void fillNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        List<Object> list = dao.selectNam();
        for (Object ob : list) {
            model.addElement(ob);
        }

        cboNam.setSelectedIndex(0);
        fillThang();
    }

    void fillThang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboThang.getModel();
        model.removeAllElements();
        try {
            Object nam = cboNam.getSelectedItem();

            if (nam != null) {
                List<Object> list = dao.selectThang(nam);
                for (Object i : list) {

                    model.addElement(i);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void filltableTim() {
        DefaultTableModel tblMode = (DefaultTableModel) tblKTKL.getModel();
        tblMode.setRowCount(0);
        try {
            Object n = cboNam.getSelectedItem();
            Object t = cboThang.getSelectedItem();
            if (n != null && t != null) {
                int nam = (int) n;
                int thang = (int) t;
                List<KTKL> list = (List<KTKL>) dao.selectByThangNam(nam, thang);
                for (KTKL a : list) {
                    String ten = nvdao.selectById(a.getMaNV()).getHoTen();
                    Object[] row = {a.getId(),a.getSoKTKL(), a.getMaNV(), ten, a.getNoidung(), a.getNgay(), a.isLoai() ? "Khen thưởng" : "Kỷ luật", a.getTien()};
                    tblMode.addRow(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlKTKL = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKTKL = new javax.swing.JTable();
        pnlThemSua = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaKTKL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdoKhen = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTien = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNoiDung = new javax.swing.JTextArea();
        txtNgay = new com.toedter.calendar.JDateChooser();
        txtTimMa = new javax.swing.JTextField();
        btnTimMa = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 204));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Khen thưởng - Kỷ luật");

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

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Add-icon2.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Text-Edit-icon.png"))); // NOI18N
        btnSua.setText("Sữa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/delete-file-icon.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Actions-document-save-icon.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Stop-icon.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnThem)
                .addGap(0, 0, 0)
                .addComponent(btnSua)
                .addGap(0, 0, 0)
                .addComponent(btnXoa)
                .addGap(0, 0, 0)
                .addComponent(btnLuu)
                .addGap(0, 0, 0)
                .addComponent(btnHuy)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThem)
            .addComponent(btnSua)
            .addComponent(btnXoa)
            .addComponent(btnLuu)
            .addComponent(btnHuy)
        );

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        tblKTKL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã KTKL", "Mã nhân viên", "Tên nhân viên", "Nội dung", "Ngày", "Loại", "Tiền"
            }
        ));
        jScrollPane1.setViewportView(tblKTKL);

        javax.swing.GroupLayout pnlKTKLLayout = new javax.swing.GroupLayout(pnlKTKL);
        pnlKTKL.setLayout(pnlKTKLLayout);
        pnlKTKLLayout.setHorizontalGroup(
            pnlKTKLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKTKLLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlKTKLLayout.setVerticalGroup(
            pnlKTKLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKTKLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlKTKL, "card2");

        jLabel3.setText("Mã nhân viên");

        jLabel4.setText("Mã KTKL");

        jLabel5.setText("Loại");

        buttonGroup1.add(rdoKhen);
        rdoKhen.setText("Khen thưởng");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Kỷ luật");

        jLabel6.setText("Ngày");

        jLabel12.setText("Tiền");

        jLabel13.setText("Nội dung");

        txtNoiDung.setColumns(20);
        txtNoiDung.setRows(5);
        jScrollPane3.setViewportView(txtNoiDung);

        btnTimMa.setText("Tim");
        btnTimMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimMaActionPerformed(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ và tên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblNhanVien);

        jLabel2.setText("ID");

        javax.swing.GroupLayout pnlThemSuaLayout = new javax.swing.GroupLayout(pnlThemSua);
        pnlThemSua.setLayout(pnlThemSuaLayout);
        pnlThemSuaLayout.setHorizontalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel12))
                                .addGap(237, 237, 237))))
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtID)
                            .addComponent(txtMaKTKL, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThemSuaLayout.createSequentialGroup()
                                .addComponent(rdoKhen)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton2)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(txtTimMa, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimMa))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThemSuaLayout.setVerticalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTimMa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaKTKL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoKhen)
                                    .addComponent(jRadioButton2)))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(85, 85, 85))
        );

        jLayeredPane1.add(pnlThemSua, "card3");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Năm");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Tháng");

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel8)
                .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLoc))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        CheckNut(false);
        pnlThemSua.setVisible(true);
        pnlKTKL.setVisible(false);
        check = true;
        tblNhanVien.setEnabled(true);
        btnLoc.setEnabled(false);
        btnTimMa.setEnabled(true);
        txtMaKTKL.setEnabled(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        int indexRow = tblKTKL.getSelectedRow();
        if (indexRow < 0) {
            MsgBox.alert(this, "Mời chọn nhân viên cần cập nhật");
        } else {
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlKTKL.setVisible(false);
            int maID = (int) tblKTKL.getValueAt(indexRow, 0);
            try {
                KTKL ul = dao.selectById(maID);
                setform(ul);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlKTKL.setVisible(false);
            check = false;
            txtMaNV.setEditable(false);
            txtMaKTKL.setEnabled(false);
            tblNhanVien.setEnabled(false);
            btnLoc.setEnabled(false);
            btnTimMa.setEnabled(false);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
       
        
        StringBuilder sb = new StringBuilder();
        Vaidation.checkRong(txtMaNV, sb, "Không để trống mã nhân viên\n");
        Vaidation.checkRong(txtTien, sb, "không để trống tiền\n");
        Vaidation.checkSo(txtTien, sb);
        if(txtMaKTKL.getText().length()>5){
            sb.append("Mã KTKL không quá 5 ký tự\n");
        }
        if(txtNoiDung.getText().length()<=0){
            sb.append("không để trống nội dung\n");
        }
        
        if(txtNgay.getDate() == null){
            sb.append("Vui lòng chọn ngày\n");
        }
        
        if(sb.length()>0){
            MsgBox.alert(this, sb.toString());
            return;
        }
        if (check) {
            Them();
            clear();

        } else {
            Sua();
            clear();
        }
         CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlKTKL.setVisible(true);
        filltable();
       
        btnLoc.setEnabled(true);
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlKTKL.setVisible(true);
        clear();
        filltable();
        filltableTim();
        btnLoc.setEnabled(true);
    }//GEN-LAST:event_btnHuyActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            int index = tblNhanVien.getSelectedRow();
            String manv = (String) tblNhanVien.getValueAt(index, 0);
            txtMaNV.setText("");
            txtMaNV.setText(manv);
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnTimMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimMaActionPerformed
        fillTable2();
    }//GEN-LAST:event_btnTimMaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        DeleteNhanVien();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        filltableTim();
    }//GEN-LAST:event_btnLocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimMa;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel pnlKTKL;
    private javax.swing.JPanel pnlThemSua;
    private javax.swing.JRadioButton rdoKhen;
    private javax.swing.JTable tblKTKL;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMaKTKL;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgay;
    private javax.swing.JTextArea txtNoiDung;
    private javax.swing.JTextField txtTien;
    private javax.swing.JTextField txtTimMa;
    // End of variables declaration//GEN-END:variables
}
