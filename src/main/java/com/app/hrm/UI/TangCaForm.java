/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.DAO.ChucVuDao;
import com.app.hrm.DAO.HopDongDao;
import com.app.hrm.DAO.LoaiCaDao;
import com.app.hrm.DAO.NhanVienDao;
import com.app.hrm.DAO.PhongBanDao;
import com.app.hrm.DAO.TangCaDao;
import com.app.hrm.Entity.ChucVu;
import com.app.hrm.Entity.HopDong;
import com.app.hrm.Entity.LoaiCa;
import com.app.hrm.Entity.NhanVien;
import com.app.hrm.Entity.PhongBan;
import com.app.hrm.Entity.TangCa;
import com.app.hrm.Utils.Auth;
import com.app.hrm.Utils.MsgBox;
import com.app.hrm.Utils.Vaidation;
import com.app.hrm.Utils.XDate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class TangCaForm extends javax.swing.JPanel {
    TangCaDao dao = new TangCaDao();
    LoaiCaDao lcdao = new LoaiCaDao();
    boolean check = true;
    HopDongDao hddao = new HopDongDao();
    NhanVienDao nvdao = new NhanVienDao();
    ChucVuDao cvdao = new ChucVuDao();
    PhongBanDao pbdao = new PhongBanDao();
    /**
     * Creates new form TangCa
     */
    public TangCaForm() {
        initComponents();
        try{
        CheckNut(true);
        filltable();
        fillComboBox();
        btnXoa.setEnabled(Auth.isBoss());
        txtID.setEnabled(false);
        fillNam();
        
        fillNV();
        }catch(Exception e ){
            e.printStackTrace();
        }
        
        txtTen.setEnabled(false);
        txtCCCD.setEnabled(false);
        txtNgaySinh.setEnabled(false);
        txtGT.setEnabled(false);
        txtPhongBan.setEnabled(false);
        txtChucVu.setEnabled(false);
        
    }
    
     void CheckNut(boolean check){
        btnThem.setEnabled(check);
        btnSua.setEnabled(check);
        btnXoa.setEnabled(check);
        btnLuu.setEnabled(!check);
        btnHuy.setEnabled(!check);
    }
     
     void fillNam(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        List<Object> list = dao.selectNam();
        for (Object ob : list) {
            model.addElement(ob);
        }
        

        fillThang();
    }
    
      void fillThang(){
          DefaultComboBoxModel model = (DefaultComboBoxModel) cboThang.getModel();
          model.removeAllElements();
          try{
          Object nam =  cboNam.getSelectedItem();
          
           if(nam != null){
               List<Object> list = dao.selectThang(nam);
          for (Object i : list) {
              
              model.addElement(i);
          }
           }
          
           
          }catch(Exception e){
              e.printStackTrace();
          }
         
    }
      
      void fillNV(){
          String key = txtTim.getText();
          List<NhanVien> list = nvdao.selectByKey(key);
          DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
          model.setRowCount(0);
          for (NhanVien nv : list) {
              Object[] row = {nv.getMaNV(),nv.getHoTen()};
              model.addRow(row);
          }
      }
      
      void filltableTim(){
        DefaultTableModel tblMode = (DefaultTableModel) tbltangca.getModel();
        tblMode.setRowCount(0);
        try {
            Object n =  cboNam.getSelectedItem();
            Object t =  cboThang.getSelectedItem();
            if(n != null && t != null){
                int nam = (int) n;
                int thang = (int) t;
                List<TangCa> list = (List<TangCa>) dao.selectByThangNam(nam, thang);
             for (TangCa tk : list) {
                   String ca = lcdao.selectById(tk.getIDloaiCa()).getTenCa();
                 Object[] row = { 
                     tk.getID(),
                     tk.getMaNV(), 
                     ca,
                     tk.getSoGio(),
                     tk.getNgay(),
                     tk.getTien()
                 };
                 tblMode.addRow(row);
               }
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     void filltable(){
           DefaultTableModel model = (DefaultTableModel) tbltangca.getModel();
           model.setRowCount(0);
           try {
               List<TangCa> list = dao.selectAll();
               for (TangCa tk : list) {
                   String ca = lcdao.selectById(tk.getIDloaiCa()).getTenCa();
                 Object[] row = { 
                     tk.getID(),
                     tk.getMaNV(), 
                     ca,
                     tk.getSoGio(),
                     XDate.toString(tk.getNgay()),
                     tk.getTien()
                 };
                 model.addRow(row);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
           
       }
     
    void fillComboBox(){
         DefaultComboBoxModel model = (DefaultComboBoxModel) cbbLoai.getModel();
         model.removeAllElements();
         try{
         List<LoaiCa> list = lcdao.selectAll();
             for (LoaiCa loaiCa : list) {
                 model.addElement(loaiCa);
             }
         }catch(Exception e){
             e.printStackTrace();
         }
    }
     
     void setform(TangCa form){ 
         LoaiCa lc = lcdao.selectById(form.getIDloaiCa());
        txtMaNV.setText(form.getMaNV()); 
         setFormNV(form.getMaNV());
        txtNgay.setDate(form.getNgay()); 
        txtSoGio.setText(String.valueOf(form.getSoGio())); 
       cbbLoai.getModel().setSelectedItem(lc);
       txtID.setText(String.valueOf(form.getID()));
    } 
     
     void setFormNV(String manv){
         NhanVien nv = nvdao.selectById(manv);
         ChucVu cv = cvdao.selectById(nv.getIDCV());
          PhongBan pb = pbdao.selectById(nv.getIDPB());
          
          txtTen.setText(nv.getHoTen());
          txtNgaySinh.setText(XDate.toString(nv.getNgaySinh()));
          txtCCCD.setText(nv.getCCCD());
          txtChucVu.setText(cv.getTenCV());
          txtPhongBan.setText(pb.getTenPhongban());
          if(nv.isGioiTinh()){
              txtGT.setText("Nam");
          }else{
              txtGT.setText("Nữ");
          }
          
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
    
    TangCa getform() { 
        LoaiCa lc = (LoaiCa) cbbLoai.getSelectedItem();
        HopDong hd = hddao.selectByMaNV(txtMaNV.getText());
        int gio =Integer.parseInt(txtSoGio.getText());
        float tien = ((hd.getLuongCB()*lc.getHeSo()))*gio;
        TangCa form = new TangCa(); 
        form.setMaNV(txtMaNV.getText()); 
        form.setNgay(txtNgay.getDate());
        form.setIDloaiCa(lc.getId()); 
        form.setTien(tien); 
        form.setSoGio(gio);
        if(!check){
        form.setID(Integer.parseInt(txtID.getText()));
        }
        return form; 
    }
    
    void Them() {
        int kt =0;
        TangCa ul = getform();
        List<NhanVien> list = nvdao.selectAll();
        for (NhanVien nv : list) {
            if(ul.getMaNV().equals(nv.getMaNV())){
                kt++;
                break;
            }
        }
        if(kt>0){
        try{
                    dao.insert(ul);
                    this.filltable();
                    this.clear();
                    MsgBox.alert(this, "Thêm mới thành công!");
                }catch (Exception e) {
                    e.printStackTrace();
}
        }else{
               MsgBox.alert(this, "Mã nhân viên không hợp lệ");
        }
    }

    void Sua() {
        TangCa ul = getform();
        
        try{
                dao.update(ul);
                filltable();
                MsgBox.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public void Delete(){
        if(MsgBox.confirm(this, "Bạn thực sự muốn xóa")){
            try {
                for(int row : tbltangca.getSelectedRows()){
                    int id = (int) tbltangca.getValueAt(row, 0);
                    dao.delete(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            filltable();
        }
    }
    void clear(){
        txtTim.setText("");
        txtMaNV.setText("");
        txtNgay.setDate(null);
        txtSoGio.setText("");
        txtTen.setText("");
          txtNgaySinh.setText("");
          txtCCCD.setText("");
          txtChucVu.setText("");
          txtPhongBan.setText("");
          lblHinh.setIcon(null);
          txtGT.setText("");
          txtID.setText("");
          txtNgay.setDate(null);
        
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
        pnlTangCa = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltangca = new javax.swing.JTable();
        pnlThemSua = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTim = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtSoGio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbLoai = new javax.swing.JComboBox<>();
        txtNgay = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtChucVu = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPhongBan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtGT = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboThang = new javax.swing.JComboBox<>();
        btnLoc = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 204));

        jLabel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bảng Chấm công tăng ca");

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
            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        tbltangca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã nhân viên", "Loại ca", "Số giờ", "Ngày", "Lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbltangca);

        javax.swing.GroupLayout pnlTangCaLayout = new javax.swing.GroupLayout(pnlTangCa);
        pnlTangCa.setLayout(pnlTangCaLayout);
        pnlTangCaLayout.setHorizontalGroup(
            pnlTangCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
        );
        pnlTangCaLayout.setVerticalGroup(
            pnlTangCaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTangCaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlTangCa, "card2");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTim)
                .addGap(18, 18, 18)
                .addComponent(btnTim)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên"
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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNVMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNV);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("ID tăng ca");

        jLabel3.setText("Mã nhân viên");

        jLabel4.setText("Số giờ làm");

        jLabel8.setText("Loại ca");

        jLabel9.setText("Ngày");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtSoGio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setEnabled(false);

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Tên");

        jLabel11.setText("Ngày Sinh");

        jLabel12.setText("Chức vụ");

        jLabel13.setText("Phòng ban");

        jLabel14.setText("Giới tính");

        jLabel15.setText("CCCD");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPhongBan, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtChucVu, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblHinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen)
                            .addComponent(txtNgaySinh)
                            .addComponent(txtCCCD)
                            .addComponent(txtGT)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlThemSuaLayout = new javax.swing.GroupLayout(pnlThemSua);
        pnlThemSua.setLayout(pnlThemSuaLayout);
        pnlThemSuaLayout.setHorizontalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlThemSuaLayout.setVerticalGroup(
            pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThemSuaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThemSuaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThemSuaLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.add(pnlThemSua, "card3");

        jLabel5.setText("Năm");

        jLabel6.setText("Tháng");

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
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel6)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
        pnlTangCa.setVisible(false);
        check = true;
        btnTim.setEnabled(true);
        tblNV.setEnabled(true);
        btnLoc.setEnabled(false);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
         int indexRow = tbltangca.getSelectedRow();
        if(indexRow<0){
            MsgBox.alert(this, "Mời chọn nhân viên cần cập nhật");
        }else{
            int maID =  (int) tbltangca.getValueAt(indexRow, 0);
            try {
                TangCa ul = dao.selectById(maID);
                setform(ul);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CheckNut(false);
            pnlThemSua.setVisible(true);
            pnlTangCa.setVisible(false);
            check = false;
            txtMaNV.setEditable(false);
        }
        btnTim.setEnabled(false);
        tblNV.setEnabled(false);
        btnLoc.setEnabled(false);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
      
        StringBuilder sb = new StringBuilder();
        Vaidation.checkRong(txtMaNV, sb, "không để trống mã nhân viên\n");
        Vaidation.checkRong(txtSoGio, sb, "không để trống số giờ\n");
        Vaidation.checkSo(txtSoGio, sb);
        if(txtNgay.getDate() == null){
            sb.append("Vui lòng chọn ngày tăng ca\n");
        }
        
        if(sb.length()>0){
            MsgBox.alert(this, sb.toString());
            return;
        }
        
        if(check){
            Them();
            
        }else{
            Sua();
        }
        clear();
        filltable();
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlTangCa.setVisible(true);
 
        btnLoc.setEnabled(true);
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        CheckNut(true);
        pnlThemSua.setVisible(false);
        pnlTangCa.setVisible(true);
        clear();
        btnLoc.setEnabled(true);
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
       Delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        filltableTim();
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
       fillNV();
    }//GEN-LAST:event_btnTimActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbbLoai;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel pnlTangCa;
    private javax.swing.JPanel pnlThemSua;
    private javax.swing.JTable tblNV;
    private javax.swing.JTable tbltangca;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtChucVu;
    private javax.swing.JTextField txtGT;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgay;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtPhongBan;
    private javax.swing.JTextField txtSoGio;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
