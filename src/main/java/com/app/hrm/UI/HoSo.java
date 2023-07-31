/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import Swing.ScrollBarCustom;
import com.app.hrm.DAO.ChucVuDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.DAO.PhongBanDao;
import com.app.hrm.DAO.TrinhDoDao;
import com.app.hrm.Entity.ChucVu;
import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Entity.PhongBan;
import com.app.hrm.Entity.TrinhDo;
import com.app.hrm.Utils.Auth;
import com.app.hrm.Utils.MsgBox;
import com.app.hrm.Utils.Vaidation;
import com.app.hrm.Utils.XDate;
import com.app.hrm.Utils.XImage;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class HoSo extends javax.swing.JPanel {

    NhanVienDao dao = new NhanVienDao();
    PhongBanDao pbdao = new PhongBanDao();
    TrinhDoDao tddao = new TrinhDoDao();
    ChucVuDao cvdao = new ChucVuDao();

    boolean check;

    /**
     * Creates new form HoSo
     */
    public HoSo() {
        initComponents();
        filltable();
        fillPhongban();
        fillTrinhDo();
        fillChucVu();
        CheckNut(true);
        btnXoa.setEnabled(Auth.isBoss());
        Scroll.setViewportBorder(null);
        Scroll.setBorder(null);
        Scroll.getViewport().setOpaque(false);
        Scroll.setVerticalScrollBar(new ScrollBarCustom());

    }

    void CheckNut(boolean check) {
        btnThem.setEnabled(check);
        btnSua.setEnabled(check);

        btnLuu.setEnabled(!check);
        btnHuy.setEnabled(!check);
    }

    void filltable() {
        DefaultTableModel tblMode = (DefaultTableModel) tblNhanVien.getModel();
        tblMode.setRowCount(0);
        try {
            String key = txtTim.getText();
            List<NhanVien> list = dao.selectByKey(key);
            for (NhanVien nv : list) {
                String trinhDo = tddao.selectById(nv.getIDTD()).getTrinhDo();
                String chucVu = cvdao.selectById(nv.getIDCV()).getTenCV();
                String phongban = pbdao.selectById(nv.getIDPB()).getTenPhongban();
                Object[] row = {nv.getMaNV(), nv.getHoTen(), nv.isGioiTinh() ? "Nam" : "Nữ",XDate.toString( nv.getNgaySinh()), nv.getDienThoai(), nv.getCCCD(), nv.getDiaChi(), trinhDo, chucVu, phongban, nv.getHinh()};
                tblMode.addRow(row);
            }
            tblNhanVien.getColumnModel().getColumn(10).setCellRenderer(new ImageRender());

            tblNhanVien.setRowHeight(80);
            tblNhanVien.getColumnModel().getColumn(10).setWidth(80);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class ImageRender extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSize(80,80);
            if (value != null) {
                try {
                    String photo = value.toString();
                    File path = new File("\\logo", photo);
                    BufferedImage image = ImageIO.read(path);
                    int x = getSize().width;
                    int y = getSize().height;
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
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(dx, dy, Image.SCALE_SMOOTH));
                    return new JLabel(icon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return new JLabel("Không có hình");

        }

    }

    public void fillTrinhDo() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTrinhDo.getModel();
        model.removeAllElements();
        List<TrinhDo> listcd = tddao.selectAll();
        for (TrinhDo td : listcd) {
            model.addElement(td);
        }
    }

    public void fillPhongban() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbPhongBan.getModel();
        model.removeAllElements();
        List<PhongBan> listcd = pbdao.selectAll();
        for (PhongBan pb : listcd) {
            model.addElement(pb);
        }
    }

    public void fillChucVu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbChucVu.getModel();
        model.removeAllElements();
        List<ChucVu> list = cvdao.selectAll();
        for (ChucVu cv : list) {
            model.addElement(cv);
        }
    }

    void clear() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtCCCD.setText("");
        txtDT.setText("");
        txtDiaChi.setText("");
        txtNgaySinh.setDate(null);

        txtMK.setText("");
        cbbChucVu.setSelectedIndex(0);
        cbbPhongBan.setSelectedIndex(0);
        cbbTrinhDo.setSelectedIndex(0);
        lblHinh.setIcon(null);
    }

    public NhanVien getForm() {
        ChucVu cv = (ChucVu) cbbChucVu.getSelectedItem();
        PhongBan pb = (PhongBan) cbbPhongBan.getSelectedItem();
        TrinhDo td = (TrinhDo) cbbTrinhDo.getSelectedItem();

        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setCCCD(txtCCCD.getText());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setDienThoai(txtDT.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setGioiTinh(rdbNam.isSelected());
        nv.setIDPB(pb.getIdPB());
        nv.setIDTD(td.getIdTD());
        nv.setIDCV(cv.getIdCV());
        nv.setMKTK(new String(txtMK.getPassword()));
        nv.setNgaySinh(txtNgaySinh.getDate());
        nv.setHinh(lblHinh.getToolTipText());

        return nv;
    }

    public void setForm(NhanVien nv) {
        try {
            ChucVu cv = cvdao.selectById(nv.getIDCV());
            TrinhDo td = tddao.selectById(nv.getIDTD());
            PhongBan pb = pbdao.selectById(nv.getIDPB());

            txtMaNV.setText(nv.getMaNV());
            txtHoTen.setText(nv.getHoTen());
            txtCCCD.setText(nv.getCCCD());
            txtDT.setText(nv.getDienThoai());
            txtDiaChi.setText(nv.getDiaChi());
            txtNgaySinh.setDate(nv.getNgaySinh());
            txtMK.setText(nv.getMKTK());
            cbbChucVu.getModel().setSelectedItem(cv);
            cbbPhongBan.getModel().setSelectedItem(pb);
            cbbTrinhDo.getModel().setSelectedItem(td);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddNhanVien() {
        NhanVien nv = getForm();
        int kt = 0;
        try {

            List<NhanVien> list = dao.selectAll();
            for (NhanVien check : list) {
                if (nv.getMaNV().equals(check.getMaNV())) {
                    kt++;
                    break;
                }
            }
            if (kt > 0) {
                MsgBox.alert(this, "Nhân viên đã tồn tại");
            } else {
                try {
                    dao.insert(nv);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateNhanVien() {
        NhanVien nv = getForm();
        try {

            dao.update(nv);
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void DeleteNhanVien() {
        if (MsgBox.confirm(this, "Bạn thực sự muốn xóa")) {
            try {
                for (int row : tblNhanVien.getSelectedRows()) {
                    String manv = (String) tblNhanVien.getValueAt(row, 0);
                    dao.delete(manv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            filltable();
        }

    }

    void chonAnh() {

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            try {
                File path = new File("\\logo", file.getName());
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
                lblHinh.setToolTipText(file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // giữ tên hình trong tooltip
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
        txtTim = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlNhanVien = new javax.swing.JPanel();
        Scroll = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        pnlThemSua = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnHinh = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtMK = new javax.swing.JPasswordField();
        cbbTrinhDo = new javax.swing.JComboBox<>();
        cbbPhongBan = new javax.swing.JComboBox<>();
        cbbChucVu = new javax.swing.JComboBox<>();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 204));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hồ sơ chi tiết");

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

        txtTim.setAutoscrolls(false);
        txtTim.setMaximumSize(new java.awt.Dimension(300, 2147483647));

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Search-icon.png"))); // NOI18N
        btnTim.setMaximumSize(new java.awt.Dimension(30, 23));
        btnTim.setPreferredSize(new java.awt.Dimension(30, 23));
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThem)
            .addComponent(btnSua)
            .addComponent(btnXoa)
            .addComponent(btnLuu)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnHuy)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Giới tính", "Ngày sinh", "Điện thoại", "CCCD", "Địa chỉ", "Trình độ", "Chức vụ", "Phòng ban", "Hình"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Byte.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tblNhanVien);

        javax.swing.GroupLayout pnlNhanVienLayout = new javax.swing.GroupLayout(pnlNhanVien);
        pnlNhanVien.setLayout(pnlNhanVienLayout);
        pnlNhanVienLayout.setHorizontalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhanVienLayout.createSequentialGroup()
                .addComponent(Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlNhanVienLayout.setVerticalGroup(
            pnlNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlNhanVien, "card2");

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.setMaximumSize(new java.awt.Dimension(135, 20));

        jLabel3.setText("Mã nhân viên");

        jLabel4.setText("Họ tên");

        jLabel5.setText("Giới tính");

        buttonGroup1.add(rdbNam);
        rdbNam.setText("Nam");

        buttonGroup1.add(rdbNu);
        rdbNu.setText("Nữ");

        jLabel6.setText("Ngày sinh");

        jLabel7.setText("Điện thoại");

        jLabel8.setText("Trình độ");

        jLabel9.setText("Phòng ban");

        jLabel11.setText("Địa chỉ");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane2.setViewportView(txtDiaChi);

        btnHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Documents.png"))); // NOI18N
        btnHinh.setText("Chọn hình");
        btnHinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHinhActionPerformed(evt);
            }
        });

        jLabel10.setText("Chức vụ");

        jLabel12.setText("CCCD");

        jLabel13.setText("Mật khẩu");

        cbbTrinhDo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbPhongBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlThemSuaLayout = new javax.swing.GroupLayout(pnlThemSua);
        pnlThemSua.setLayout(pnlThemSuaLayout);
        pnlThemSuaLayout.setHorizontalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbbPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(193, 193, 193))
                            .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                    .addComponent(rdbNam)
                                    .addGap(18, 18, 18)
                                    .addComponent(rdbNu))
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(cbbTrinhDo, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThemSuaLayout.createSequentialGroup()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDT, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThemSuaLayout.setVerticalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTrinhDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdbNam)
                            .addComponent(rdbNu)
                            .addComponent(cbbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)))
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHinh)))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlThemSua, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        CheckNut(false);
        pnlThemSua.setVisible(true);
        pnlNhanVien.setVisible(false);
        check = true;
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int indexRow = tblNhanVien.getSelectedRow();
        if (indexRow < 0) {
            MsgBox.alert(this, "Mời chọn nhân viên cần cập nhật");
        } else {
            String manv = (String) tblNhanVien.getValueAt(indexRow, 0);
            try {
                NhanVien nv = dao.selectById(manv);
                setForm(nv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlNhanVien.setVisible(false);
            check = false;
            txtMaNV.setEditable(false);
        }


    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        
        
        StringBuilder sb = new StringBuilder();
        Vaidation.checkRong(txtDT, sb, "Không để trống điện thoại\n");
        Vaidation.checkRong(txtCCCD, sb, "Không để trống CCCD\n");
        Vaidation.checkRong(txtHoTen, sb, "Không để trống họ tên\n");
        Vaidation.checkRong(txtMaNV, sb, "Không để trống mã Nhân viên\n");
        Vaidation.checkPass(txtMK, sb, "Không để trống mật khẩu\n");
        if(txtNgaySinh.getDate() == null ){
            sb.append("Vui lòng chọn ngày sinh\n");
        }
        
        if(sb.length()>0){
            MsgBox.alert(this, sb.toString());
            return;
        }
        
        if (check) {
            AddNhanVien();

        } else {
            UpdateNhanVien();
        }
        filltable();
        clear();
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlNhanVien.setVisible(true);
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlNhanVien.setVisible(true);
        clear();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        filltable();
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        DeleteNhanVien();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnHinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHinhActionPerformed
        chonAnh();
    }//GEN-LAST:event_btnHinhActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            int indexRow = tblNhanVien.getSelectedRow();
           
           
                String manv = (String) tblNhanVien.getValueAt(indexRow, 0);
                try {
                    NhanVien nv = dao.selectById(manv);
                    setForm(nv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CheckNut(false);
                pnlThemSua.setVisible(true);
                pnlNhanVien.setVisible(false);
                check = false;
                txtMaNV.setEditable(false);
            }

        

    }//GEN-LAST:event_tblNhanVienMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Scroll;
    private javax.swing.JButton btnHinh;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbChucVu;
    private javax.swing.JComboBox<String> cbbPhongBan;
    private javax.swing.JComboBox<String> cbbTrinhDo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel pnlNhanVien;
    private javax.swing.JPanel pnlThemSua;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDT;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
