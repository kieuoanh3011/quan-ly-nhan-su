/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.BaoHiemDao;
import com.app.hrm.DAO.ChucVuDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.DAO.PhongBanDao;
import com.app.hrm.Entity.BaoHiem;
import com.app.hrm.Entity.ChucVu;
import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Entity.PhongBan;
import com.app.hrm.Entity.UngLuong;
import com.app.hrm.Utils.Auth;
import com.app.hrm.Utils.MsgBox;
import com.app.hrm.Utils.Vaidation;
import com.app.hrm.Utils.XDate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class BaoHiemform extends javax.swing.JPanel {
    NhanVienDao nvdao = new NhanVienDao();
    BaoHiemDao dao = new BaoHiemDao();
    ChucVuDao cvdao = new ChucVuDao();
    PhongBanDao pbdao = new PhongBanDao();
    boolean check = true;

    /**
     * Creates new form BaoHiem
     */
    public BaoHiemform() {
        initComponents();
        CheckNut(true);
        fillTable();
        fillNV();
        txtTen.setEnabled(false);
        txtCCCD.setEnabled(false);
        txtPhongBan.setEnabled(false);
        txtChucVu.setEnabled(false);
        btnXoa.setEnabled(Auth.isBoss());
    }

    void CheckNut(boolean check) {
        btnThem.setEnabled(check);
        btnSua.setEnabled(check);
        btnXoa.setEnabled(check);
        btnLuu.setEnabled(!check);
        btnHuy.setEnabled(!check);
    }
    
    

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblBaoHiem.getModel();
        model.setRowCount(0);// xóa tất cả các hàng trên bản

        try {
            List<BaoHiem> list = dao.selectAll();
            for (BaoHiem cd : list) {

                Object[] row = {
                    cd.getMaNV(),
                    cd.getSoBH(),
                   XDate.toString( cd.getNgayCap()),
                    cd.getNoiCap(),
                    cd.getNoiKham(),
                    cd.getTien()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setform(BaoHiem form) {
        txtMaNV.setText(form.getMaNV());
        txtSoBH.setText(form.getSoBH());
        txtNoiCap.setText(form.getNoiCap());
        txtNoiKham.setText(form.getNoiKham());
        txtday.setDate(form.getNgayCap());
        setFormNV(form.getMaNV());
    }
    
     void setFormNV(String manv){
         NhanVien nv = nvdao.selectById(manv);
         ChucVu cv = cvdao.selectById(nv.getIDCV());
          PhongBan pb = pbdao.selectById(nv.getIDPB());
          
          txtTen.setText(nv.getHoTen());
       
          txtCCCD.setText(nv.getCCCD());
          txtChucVu.setText(cv.getTenCV());
          txtPhongBan.setText(pb.getTenPhongban());
         
          
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

    BaoHiem getform() {
        BaoHiem form = new BaoHiem();
        form.setMaNV(txtMaNV.getText());
        form.setNgayCap(txtday.getDate());
        form.setSoBH(txtSoBH.getText());
        form.setNoiCap(txtNoiCap.getText());
        form.setNoiKham(txtNoiKham.getText());
        form.setTien(500);
        return form;
    }

    void Them() {
        int kt =0;
        BaoHiem ul = getform();

        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nv : list) {
            if(ul.getMaNV().equals(nv.getMaNV())){
                kt++;
                break;
            }
        }
        
        if(kt>0){
        try {
            dao.insert(ul);
            this.fillTable();
            clear();
            MsgBox.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại!");
        }
        }else{
            MsgBox.alert(this, "Mã nhân viên không hợp lệ");
        }
    }

    void Sua() {
        BaoHiem ul = getform();
       
        try {
            dao.update(ul);
            fillTable();
            MsgBox.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

   public void Delete(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa")){
            try {
                for(int row : tblBaoHiem.getSelectedRows()){
                    String manv = (String) tblBaoHiem.getValueAt(row, 0);
                    dao.delete(manv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            fillTable();
        }
   }
         

    void clear() {

        txtMaNV.setText("");
        txtSoBH.setText("");
        txtday.setDate(null);
        txtNoiCap.setText("");
        txtNoiKham.setText("");

        txtTen.setText("");
          
          txtCCCD.setText("");
          txtChucVu.setText("");
          txtPhongBan.setText("");
          lblHinh.setIcon(null);
          
    }
    
    void fillNV() {
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = nvdao.selectBH();
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNV(), nv.getHoTen(), XDate.toString(nv.getNgaySinh())};
                model.addRow(row);
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
        pnlBaoHiem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBaoHiem = new javax.swing.JTable();
        pnlThemSua = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtday = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtSoBH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNoiCap = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        txtNoiKham = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtCCCD = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtChucVu = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPhongBan = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 204));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bảo hiểm");

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

        tblBaoHiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Số bảo hiểm", "Ngày cấp", "Nơi cấp", "Nơi khám", "Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblBaoHiem);

        javax.swing.GroupLayout pnlBaoHiemLayout = new javax.swing.GroupLayout(pnlBaoHiem);
        pnlBaoHiem.setLayout(pnlBaoHiemLayout);
        pnlBaoHiemLayout.setHorizontalGroup(
            pnlBaoHiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBaoHiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBaoHiemLayout.setVerticalGroup(
            pnlBaoHiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBaoHiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlBaoHiem, "card2");

        pnlThemSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlThemSuaMousePressed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Ngày cấp");

        jLabel2.setText("Số bảo hiểm");

        jLabel3.setText("Nơi cấp");

        jLabel4.setText("Mã nhân viên");

        jLabel6.setText("Nơi khám");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(txtSoBH)
                    .addComponent(jLabel5)
                    .addComponent(txtday, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(txtNoiCap))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV)
                            .addComponent(txtNoiKham)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoBH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoiCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoiKham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Họ và tên");

        jLabel9.setText("CCCD");

        jLabel10.setText("Chức vụ");

        jLabel11.setText("Phòng ban");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addComponent(txtChucVu))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhongBan, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTen)
                    .addComponent(txtCCCD))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên chưa có bảo hiểm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ và tên", "Ngày sinh"
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlThemSuaLayout = new javax.swing.GroupLayout(pnlThemSua);
        pnlThemSua.setLayout(pnlThemSuaLayout);
        pnlThemSuaLayout.setHorizontalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlThemSuaLayout.setVerticalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        pnlBaoHiem.setVisible(false);
        check = true;
        tblNV.setEnabled(true);
        txtMaNV.setEnabled(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        int indexRow = tblBaoHiem.getSelectedRow();
        if (indexRow < 0) {
            MsgBox.alert(this, "Mời chọn nhân viên cần cập nhật");
        } else {
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlBaoHiem.setVisible(false);
            String maID = (String) tblBaoHiem.getValueAt(indexRow, 0);
            try {
                BaoHiem ul = dao.selectById(maID);
                setform(ul);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlBaoHiem.setVisible(false);
            check = false;
            txtMaNV.setEditable(false);
        }
        tblNV.setEnabled(false);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        
        
        StringBuilder sb = new StringBuilder();
        Vaidation.checkRong(txtSoBH, sb, "không để trống số bảo hiểm\n");
        Vaidation.checkRong(txtNoiCap, sb, "không để trống nơi cấp\n");
        Vaidation.checkRong(txtNoiKham, sb, "không để trống nơi khám\n");
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
        fillTable();
        fillNV();
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlBaoHiem.setVisible(true);
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlBaoHiem.setVisible(true);
        clear();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Delete();
        fillNV();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblNVMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMousePressed
         if(evt.getClickCount() == 2){
            try{
            int index = tblNV.getSelectedRow();
            String ma = (String) tblNV.getValueAt(index, 0);
                setFormNV(ma);
            txtMaNV.setText(ma);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tblNVMousePressed

    private void pnlThemSuaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThemSuaMousePressed
        if(evt.getClickCount() == 2){
              try{
            int index = tblNV.getSelectedRow();
            String ma = (String) tblNV.getValueAt(index, 0);
            setFormNV(ma);
            txtMaNV.setText(ma);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_pnlThemSuaMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel pnlBaoHiem;
    private javax.swing.JPanel pnlThemSua;
    private javax.swing.JTable tblBaoHiem;
    private javax.swing.JTable tblNV;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtChucVu;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNoiCap;
    private javax.swing.JTextField txtNoiKham;
    private javax.swing.JTextField txtPhongBan;
    private javax.swing.JTextField txtSoBH;
    private javax.swing.JTextField txtTen;
    private com.toedter.calendar.JDateChooser txtday;
    // End of variables declaration//GEN-END:variables
}
