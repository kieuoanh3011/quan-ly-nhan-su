/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.DAO.UngLuongDao;
import com.app.hrm.Entity.NhanVien;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.app.hrm.Entity.UngLuong;
import com.app.hrm.Utils.Auth;
import com.app.hrm.Utils.JdbcHelp;
import com.app.hrm.Utils.MsgBox;
import com.app.hrm.Utils.Vaidation;
import com.app.hrm.Utils.XDate;
import com.app.hrm.Utils.XImage;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author ADMIN
 */
public class UngLuongfrom extends javax.swing.JPanel {

    UngLuongDao dao = new UngLuongDao();
    NhanVienDao nvdao = new NhanVienDao();
    boolean check = true;

    /**
     * Creates new form UngLuong
     */
    public UngLuongfrom() {
        initComponents();
        CheckNut(true);
        fillTable();
        fillNV();

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

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblBangLuong.getModel();
        model.setRowCount(0);// xóa tất cả các hàng trên bản

        try {
            List<UngLuong> list = dao.selectAll();
            for (UngLuong cd : list) {
                String ten = nvdao.selectById(cd.getMaNV()).getHoTen();
                Object[] row = {
                    cd.getID(),
                    cd.getMaNV(),
                    ten,
                    XDate.toString(cd.getNgayUng()),
                    cd.getSoTien(),
                    cd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillNV() {
        String key = txtTim.getText();
        List<NhanVien> list = nvdao.selectByKey(key);
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);
        for (NhanVien nv : list) {
            Object[] row = {nv.getMaNV(), nv.getHoTen()};
            model.addRow(row);
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

    void setform(UngLuong form) {
        txtManhanvien.setText(form.getMaNV());
        txtNgay.setDate(form.getNgayUng());
        cbDathanhtoan.setSelected(form.isTrangThai());
        String tien = String.valueOf(form.getSoTien());
        txtSotien.setText(tien);
        txtID.setText(String.valueOf(form.getID()));
    }

    UngLuong getform() {
        UngLuong form = new UngLuong();
        if (check == false) {
            form.setID(Integer.parseInt(txtID.getText()));
        }
        form.setMaNV(txtManhanvien.getText());
        form.setNgayUng(txtNgay.getDate());
        form.setTrangThai(cbDathanhtoan.isSelected());
        form.setSoTien(Float.valueOf(txtSotien.getText()));
        return form;
    }

    void Them() {
        int kt = 0;
        UngLuong ul = getform();
        String confirm = new String(txtTim.getText());
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
                this.fillTable();
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
        UngLuong ul = getform();

        try {
            dao.update(ul);
            fillTable();
            this.clear();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Xoa() {
        UngLuong ul = getform();
        String confirm = new String(txtTim.getText());
        try {
            dao.delete(ul.getID());
            fillTable();
            this.clear();
            MsgBox.alert(this, "Xóa thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clear() {
        txtManhanvien.setText("");
        txtSotien.setText("");
        cbDathanhtoan.setSelected(false);
        txtID.setText("");
        txtNgay.setDate(null);
    }

    void filltableTim() {
        DefaultTableModel tblMode = (DefaultTableModel) tblBangLuong.getModel();
        tblMode.setRowCount(0);
        try {
            Object n = cboNam.getSelectedItem();
            Object t = cboThang.getSelectedItem();
            if (n != null && t != null) {
                int nam = (int) n;
                int thang = (int) t;
                List<UngLuong> list = (List<UngLuong>) dao.selectByThangNam(nam, thang);
                for (UngLuong cd : list) {
                    String ten = nvdao.selectById(cd.getMaNV()).getHoTen();
                    Object[] row = {
                        cd.getID(),
                        cd.getMaNV(),
                        ten,
                        cd.getNgayUng(),
                        cd.getSoTien(),
                        cd.isTrangThai() ? "Đã thanh toán" : "Chưa thanh toán"

                    };
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        pnlBangLuong = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangLuong = new javax.swing.JTable();
        pnlThemSua = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtManhanvien = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSotien = new javax.swing.JTextField();
        cbDathanhtoan = new javax.swing.JCheckBox();
        txtNgay = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnTim = new javax.swing.JButton();
        txtTim = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
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
        jLabel1.setText("Bảng Ứng lương");

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

        tblBangLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã nhân viên", "Tên nhân viên", "Ngày ứng lương", "Số tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBangLuong);

        javax.swing.GroupLayout pnlBangLuongLayout = new javax.swing.GroupLayout(pnlBangLuong);
        pnlBangLuong.setLayout(pnlBangLuongLayout);
        pnlBangLuongLayout.setHorizontalGroup(
            pnlBangLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBangLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBangLuongLayout.setVerticalGroup(
            pnlBangLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBangLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlBangLuong, "card2");

        jLabel3.setText("Mã nhân viên");

        jLabel4.setText("Ngày ứng lương");

        jLabel5.setText("Số tiền");

        jLabel6.setText("Trạng thái");

        cbDathanhtoan.setText("Đã thanh toán");

        jLabel2.setText("ID Ứng lương");

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Ngày sinh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNVMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNV);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTim)
                .addGap(18, 18, 18)
                .addComponent(btnTim)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTim)
                        .addGap(1, 1, 1))
                    .addComponent(btnTim, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlThemSuaLayout = new javax.swing.GroupLayout(pnlThemSua);
        pnlThemSua.setLayout(pnlThemSuaLayout);
        pnlThemSuaLayout.setHorizontalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThemSuaLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(txtID))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThemSuaLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(txtManhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbDathanhtoan)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtSotien, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(18, 18, 18)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlThemSuaLayout.setVerticalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtManhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSotien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbDathanhtoan)
                        .addGap(0, 63, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLayeredPane1.add(pnlThemSua, "card3");

        jLabel7.setText("Năm");

        jLabel8.setText("Tháng");

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
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
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        CheckNut(false);
        pnlThemSua.setVisible(true);
        pnlBangLuong.setVisible(false);
        check = true;
        tblNV.setEnabled(true);
        btnTim.setEnabled(true);
        txtManhanvien.setEnabled(true);
        btnLoc.setEnabled(false);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int indexRow = tblBangLuong.getSelectedRow();
        if (indexRow < 0) {
            MsgBox.alert(this, "Mời chọn nhân viên cần cập nhật");
        } else {
            int maID = (int) tblBangLuong.getValueAt(indexRow, 0);
            try {
                UngLuong ul = dao.selectById(maID);
                setform(ul);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlBangLuong.setVisible(false);
            check = false;
            txtID.setEnabled(false);
        }
        btnLoc.setEnabled(false);
        tblNV.setEnabled(false);
        btnTim.setEnabled(false);
        txtManhanvien.setEnabled(false);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        
        StringBuilder sb = new StringBuilder();
        Vaidation.checkRong(txtManhanvien, sb, "không để trống mã nhân viên\n");
        Vaidation.checkRong(txtSotien, sb, "không để trống số tiền\n");
        Vaidation.checkSo(txtSotien, sb);
        if (txtNgay.getDate() == null) {
            sb.append("vui lòng chọn ngày ứng\n");
        }
        if (sb.length() > 0) {
            MsgBox.alert(this, sb.toString());
            return;
        }
        if (check) {
            Them();

        } else {
            Sua();
        }
        clear();
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlBangLuong.setVisible(true);
        fillTable();
        btnLoc.setEnabled(true);

    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlBangLuong.setVisible(true);
        clear();
        btnLoc.setEnabled(true);
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int indexRow = tblBangLuong.getSelectedRow();
        if (indexRow < 0) {
            MsgBox.alert(this, "Mời chọn nhân viên cần xóa");
        } else {
            int maID = (int) tblBangLuong.getValueAt(indexRow, 0);
            boolean ck = MsgBox.confirm(this, "Bạn thực sự muốn xóa");
            if (ck) {
                try {
                    dao.delete(maID);
                    fillTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        fillNV();
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        filltableTim();
    }//GEN-LAST:event_btnLocActionPerformed

    private void tblNVMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMousePressed
        if (evt.getClickCount() == 2) {
            try {
                int index = tblNV.getSelectedRow();
                String ma = (String) tblNV.getValueAt(index, 0);
                txtManhanvien.setText(ma);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tblNVMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JCheckBox cbDathanhtoan;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlBangLuong;
    private javax.swing.JPanel pnlThemSua;
    private javax.swing.JTable tblBangLuong;
    private javax.swing.JTable tblNV;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtManhanvien;
    private com.toedter.calendar.JDateChooser txtNgay;
    private javax.swing.JTextField txtSotien;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
