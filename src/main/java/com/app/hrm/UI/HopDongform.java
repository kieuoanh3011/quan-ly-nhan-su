/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.ChucVuDao;
import com.app.hrm.DAO.HopDongDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.DAO.PhongBanDao;
import com.app.hrm.DAO.TrinhDoDao;
import com.app.hrm.Entity.ChucVu;
import com.app.hrm.Entity.HopDong;
import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Entity.PhongBan;
import com.app.hrm.Entity.TrinhDo;
import com.app.hrm.Utils.Auth;
import com.app.hrm.Utils.MsgBox;
import com.app.hrm.Utils.Vaidation;
import com.app.hrm.Utils.XDate;
import com.app.hrm.Utils.XImage;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class HopDongform extends javax.swing.JPanel {

    HopDongDao dao = new HopDongDao();
    NhanVienDao nvdao = new NhanVienDao();
    TrinhDoDao tddao = new TrinhDoDao();
    ChucVuDao cvdao = new ChucVuDao();
    PhongBanDao pbdao = new PhongBanDao();
    boolean check = true;

    public HopDongform() {
        initComponents();
        CheckNut(true);
        filltable();
        btnXoa.setEnabled(Auth.isBoss());
        txtCCCD.setEnabled(false);

        txtHoTen.setEnabled(false);

        txtCCCD.setEnabled(false);
        rdbNam.setEnabled(false);
        rdbNu.setEnabled(false);
        txtMaNV.setEditable(false);
    }

    void CheckNut(boolean check) {
        btnThem.setEnabled(check);
        btnSua.setEnabled(check);
        btnXoa.setEnabled(check);
        btnLuu.setEnabled(!check);
        btnHuy.setEnabled(!check);
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblHopDong.getModel();
        model.setRowCount(0);
        try {
            List<HopDong> list = dao.selectAll();
            for (HopDong tk : list) {
                Object[] row = {
                    tk.getSoHD(),
                    tk.getMaNV(),
                    XDate.toString(tk.getNgayBD()),
                    XDate.toString(tk.getNgayKT()),
                    XDate.toString(tk.getNgayKy()),
                    tk.getLanKy(),
                    tk.getLuongCB()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void fillNV() {
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = nvdao.selectHD();
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNV(), nv.getHoTen()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setform(HopDong form) {
        NhanVien nv = nvdao.selectById(form.getMaNV());
        PhongBan pb = pbdao.selectById(nv.getIDPB());
        TrinhDo td = tddao.selectById(nv.getIDTD());
        ChucVu cv = cvdao.selectById(nv.getIDCV());

        txtSoHD.setText(String.valueOf(form.getSoHD()));
        txtNgayDen.setDate(form.getNgayBD());
        txtNgayDi.setDate(form.getNgayKT());
        txtNgayKy.setDate(form.getNgayKy());
        txtLanKy.setText(String.valueOf(form.getLanKy()));
        txtMaNV.setText(form.getMaNV());
        txtLuongCB.setText(String.valueOf(form.getLuongCB()));
        txtHoTen.setText(nv.getHoTen());
        txtCCCD.setText(nv.getCCCD());
        txtNoidung.setText(form.getNoidung());
        rdbNam.setSelected(nv.isGioiTinh());
        rdbNu.setSelected(!nv.isGioiTinh());
        
        if (nv.getHinh() != null) {
                try {
                    String photo = nv.getHinh();
                    File path = new File("\\logo", photo);
                    BufferedImage image = ImageIO.read(path);
                    int x = lblHinh.getSize().width;
                    int y = lblHinh.getSize().height;
                    int ix = image.getWidth();
                    int iy = image.getHeight();
                    int dx = 0;
                    int dy = 0;
                    if (x / y > ix / iy) {
                        dy = y;
                        dx = dy * ix / iy;
                    } else {
                        dx = x;
                        dy = dx * iy / ix;
                    }

                    ImageIcon icon = new ImageIcon(image.getScaledInstance(dx, dy, image.SCALE_SMOOTH));
                    lblHinh.setText("");
                    lblHinh.setIcon(icon);
                    lblHinh.setToolTipText(nv.getHinh());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                lblHinh.setToolTipText("");
                lblHinh.setIcon(null);
            }
    }

    HopDong getform() {

        HopDong form = new HopDong();
        if (!check) {
            form.setSoHD(Integer.parseInt(txtSoHD.getText()));
        }
        form.setNoidung(txtNoidung.getText());
        form.setMaNV(txtMaNV.getText());
        form.setNgayBD(txtNgayDen.getDate());
        form.setNgayKT(txtNgayDi.getDate());
        form.setNgayKy(txtNgayKy.getDate());
        form.setLanKy(Integer.parseInt(txtLanKy.getText()));
        form.setLuongCB(Float.parseFloat(txtLuongCB.getText()));

        return form;
    }

    void Them() {
        int kt = 0;
        HopDong ul = getform();
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
        HopDong ul = getform();
    
        try {
            dao.update(ul);
            filltable();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void Delete() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa")) {
            try {
                for (int row : tblHopDong.getSelectedRows()) {
                    int id = (int) tblHopDong.getValueAt(row, 0);
                    dao.delete(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            filltable();
        }
    }

    void clear() {
        txtSoHD.setText("");
        txtNgayDen.setDate(null);
         txtNgayDi.setDate(null);
          txtNgayKy.setDate(null);
        txtLanKy.setText("");
        txtMaNV.setText("");
        txtLuongCB.setText("");
        txtHoTen.setText("");
        txtCCCD.setText("");

        rdbNam.setSelected(true);

        lblHinh.setIcon(null);

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
        pnlHopDong = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHopDong = new javax.swing.JTable();
        pnlThemSua = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSoHD = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtLanKy = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNgayDen = new com.toedter.calendar.JDateChooser();
        txtNgayDi = new com.toedter.calendar.JDateChooser();
        txtNgayKy = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtLuongCB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNoidung = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 204));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hợp đồng chi tiết");

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

        tblHopDong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Số hợp đồng", "Mã nhân viên", "Ngày bắt đầu", "Ngày kết thúc", "Ngày ký hợp đồng", "Lần ký", "Lương cơ bản"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHopDong);

        javax.swing.GroupLayout pnlHopDongLayout = new javax.swing.GroupLayout(pnlHopDong);
        pnlHopDong.setLayout(pnlHopDongLayout);
        pnlHopDongLayout.setHorizontalGroup(
            pnlHopDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
        );
        pnlHopDongLayout.setVerticalGroup(
            pnlHopDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHopDongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlHopDong, "card2");

        pnlThemSua.setEnabled(false);

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Họ tên");

        jLabel5.setText("Giới tính");

        buttonGroup1.add(rdbNam);
        rdbNam.setText("Nam");

        buttonGroup1.add(rdbNu);
        rdbNu.setText("Nữ");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Số hợp đồng");

        jLabel8.setText("Lương cơ bản");

        jLabel11.setText("Từ ngày");

        jLabel12.setText("Đến ngày");

        jLabel13.setText("Lần ký");

        jLabel14.setText("Ngày ký");

        jLabel16.setText("Mã nhân viên");

        jLabel6.setText("Nội dung");

        jScrollPane3.setViewportView(txtNoidung);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(txtSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(txtLanKy, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLuongCB, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayDen, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(txtNgayKy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtSoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(txtNgayDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel16)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNgayDi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtLanKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addComponent(txtNgayKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtLuongCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("CCCD");

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ tên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNVMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNV);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Danh Sách nhân viên chưa có hợp động");

        javax.swing.GroupLayout pnlThemSuaLayout = new javax.swing.GroupLayout(pnlThemSua);
        pnlThemSua.setLayout(pnlThemSuaLayout);
        pnlThemSuaLayout.setHorizontalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addComponent(rdbNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdbNu))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlThemSuaLayout.setVerticalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdbNam)
                                    .addComponent(rdbNu)))
                            .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane1.add(pnlThemSua, "card3");

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
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        CheckNut(false);
        pnlThemSua.setVisible(true);
        pnlHopDong.setVisible(false);
        check = true;
        txtSoHD.setEnabled(true);
        fillNV();
        tblNV.setEnabled(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int indexRow = tblHopDong.getSelectedRow();
        if (indexRow < 0) {
            MsgBox.alert(this, "Mời chọn nhân viên cần cập nhật");
        } else {
            fillNV();
            int maID = (int) tblHopDong.getValueAt(indexRow, 0);
            try {
                HopDong ul = dao.selectById(maID);
                setform(ul);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlHopDong.setVisible(false);
            check = false;
            txtSoHD.setEnabled(false);
            tblNV.setEnabled(false);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        
        StringBuilder sb = new StringBuilder();
        Vaidation.checkRong(txtSoHD, sb, "Không để trống số hợp đồng\n");
        Vaidation.checkRong(txtMaNV, sb, "không để trống mã nhân viên\n");
        Vaidation.checkRong(txtLanKy, sb, "không để trống lần ký\n");
        Vaidation.checkRong(txtLuongCB, sb, "không để trống lương cơ bản\n");
        Vaidation.checkSo(txtLanKy, sb);
        Vaidation.checkSo(txtLuongCB, sb);
        if(txtNoidung.getText().trim().equals("")){
            sb.append("Không để trống nội dung\n");
        }
        
        if(txtNgayDen.getDate() == null){
            sb.append("Vui lòng chọn ngày bắt đầu\n");
        }
         if(txtNgayDi.getDate() == null){
            sb.append("Vui lòng chọn ngày kết thúc\n");
        }
          if(txtNgayKy.getDate() == null){
            sb.append("Vui lòng chọn ngày ký\n");
        }
          
          if(sb.length()>0){
              MsgBox.alert(this, sb.toString());
              return;
          }
        if (check) {
            Them();

        } else {
            Sua();
        }
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlHopDong.setVisible(true);
        filltable();
        fillNV();
        clear();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlHopDong.setVisible(true);
        clear();
        filltable();
        fillNV();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked
       
    }//GEN-LAST:event_tblNVMouseClicked

    private void tblNVMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMousePressed
        if (evt.getClickCount() == 2) {
            int index = tblNV.getSelectedRow();
            String ma = (String) tblNV.getValueAt(index, 0);
            try {
                NhanVien nv = nvdao.selectById(ma);
                 txtMaNV.setText(nv.getMaNV());
                txtHoTen.setText(nv.getHoTen());
                txtCCCD.setText(nv.getCCCD());

                rdbNam.setSelected(nv.isGioiTinh());
                rdbNu.setSelected(!nv.isGioiTinh());
                if (!nv.getHinh().equals("")) {
                    lblHinh.setIcon(XImage.read(nv.getHinh()));
                }
                txtSoHD.setText("");

                txtLanKy.setText("");
               
                txtLuongCB.setText("");
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tblNVMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel pnlHopDong;
    private javax.swing.JPanel pnlThemSua;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTable tblHopDong;
    private javax.swing.JTable tblNV;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLanKy;
    private javax.swing.JTextField txtLuongCB;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgayDen;
    private com.toedter.calendar.JDateChooser txtNgayDi;
    private com.toedter.calendar.JDateChooser txtNgayKy;
    private javax.swing.JTextPane txtNoidung;
    private javax.swing.JTextField txtSoHD;
    // End of variables declaration//GEN-END:variables
}
