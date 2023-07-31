/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.app.hrm.UI;

import com.app.hrm.Utils.Auth;
import com.app.hrm.Utils.XImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author ADMIN
 */
public class Mainform extends javax.swing.JFrame {

    private HoSo hs;
    private int checkhs;
    private BangCong bc;
    private int checkbc;
    private BangLuong bl;
    private int checkbl;
    private BaoHiemform bh;
    private int checkbh;
    private TangCaForm tc;
    private int checktc;
    private UngLuongfrom ul;
    private int checkul;
    private HopDongform hd;
    private int checkhd;
    private KTKLForm ktkl;
    private int checkktkl;
    private PhuCapPanel pc;
    private int checkpc;
    private BangChamCongNV ccnv;
    private int checkccnv;
    private BangLuongNV lnv;
    private int checklnv;
    private HoSoNV hsnv;
    private int checkhsnv;

    /**
     * Creates new form Mainform
     */
    public Mainform() {
        initComponents();
        customDesing();
        openDN();
        this.dongHo();
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setSize(1250, 750);
        setBackground(Color.white);

        hsnv = new HoSoNV();
        tabbMain.addTab("Hồ sơ ", hsnv);
        tabbMain.setSelectedComponent(hsnv);
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));

        EnButton(btnHoSoCT);
        EnButton(btnCV);
        EnButton(btnHoSoCT1);

        EnButton(btnBangCong);
        EnButton(btnDoiMatKhau);
        EnButton(btnBangChamCong);
        EnButton(btnBangLuong);

        EnButton(btnHopDong);
        EnButton(btnKTKL);

        EnButton(btnPhongBan);
        EnButton(btnTD);
        EnButton(btnTangCa);

        EnButton(btnUngLuong);
        EnButton(btnDoiMatKhau);
        EnButton(btnLuongNV);
        EnButton(btnBaoHiem);
        EnButton(btnPhuCap);
        setAvata();
    }

    public void dongHo() {
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText(format.format(new Date()));
            }
        }).start();
    }

    public void openDN() {
        Auth.clear();
        new Login.Main.LoginDialog(this, true).setVisible(true);

        btnHoSoCT.setEnabled(Auth.isHR());
        btnCV.setVisible(Auth.isHR());
        btnPhongBan.setVisible(Auth.isHR());
        btnTD.setVisible(Auth.isHR());

        btnChamCong.setVisible(Auth.isHR());
        btnLuong.setVisible(Auth.isHR());
        btnThuTuc.setVisible(Auth.isHR());

        setAvata();

        lblten.setText(Auth.user.getHoTen());
    }

    private void customDesing() {
        pnlChamCong.setVisible(false);
        pnlHoso.setVisible(false);
        pnlLuong.setVisible(false);
        pnlThuTuc.setVisible(false);
    }

    private void hideSubMenu() {
        if (pnlChamCong.isVisible() == true) {
            pnlChamCong.setVisible(false);
        }
        if (pnlHoso.isVisible() == true) {
            pnlHoso.setVisible(false);
        }
        if (pnlLuong.isVisible() == true) {
            pnlLuong.setVisible(false);
        }
        if (pnlThuTuc.isVisible() == true) {
            pnlThuTuc.setVisible(false);
        }
    }

    private void showSubMenu(JPanel panel) {
        if (panel.isVisible() == false) {
            hideSubMenu();
            panel.setVisible(true);
        } else {
            panel.setVisible(false);
        }
    }

    public class DemoCustomTab extends JPanel {

        Mainform mainform;

        /**
         * JPanel contain a JLabel and a JButton to close
         */
        public DemoCustomTab(Mainform mainform) {
            this.mainform = mainform;
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            setBorder(new EmptyBorder(0, 2, 2, 2));
            setOpaque(false);

            addLabel();
            add(new CustomButton("x"));
        }

        private void addLabel() {
            JLabel label = new JLabel() {

                public String getText() {
                    int index = mainform.tabbMain.indexOfTabComponent(DemoCustomTab.this);
                    if (index != -1) {
                        return mainform.tabbMain.getTitleAt(index);
                    }
                    return null;
                }
            };
            /**
             * tao khoang cach giu chu va nut
             */
            label.setBorder(new EmptyBorder(0, 0, 0, 10));
            label.setFont(new Font("segoe UI", Font.BOLD, 12));
            add(label);
        }

        class CustomButton extends JButton implements MouseListener {

            public CustomButton(String text) {
                int size = 15;
                setText(text);
                /**
                 * set size for button close
                 */
                setPreferredSize(new Dimension(size, size));

                setToolTipText("Đống Tab");

                /**
                 * set transparent
                 */
                setContentAreaFilled(false);

                /**
                 * set border for button
                 */
                setBorder(new EtchedBorder());
                /**
                 * don't show border
                 */
                setBorderPainted(false);

                setFocusable(false);

                setFont(new Font("segoe UI", Font.BOLD, 14));

                /**
                 * add event with mouse
                 */
                addMouseListener(this);

            }

            /**
             * when click button, tab will close
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = mainform.tabbMain.indexOfTabComponent(DemoCustomTab.this);
                if (index != -1) {
                    if (index == checkhs) {
                        hs = null;
                    }
                    if (index == checkbh) {
                        bh = null;
                    }
                    if (index == checkbl) {
                        bl = null;
                    }
                    if (index == checkhd) {
                        hd = null;
                    }
                    if (index == checkktkl) {
                        ktkl = null;
                    }
                    if (index == checktc) {
                        tc = null;
                    }
                    if (index == checkul) {
                        ul = null;
                    }
                    if (index == checkbc) {
                        bc = null;
                    }
                    if (index == checkccnv) {
                        ccnv = null;
                    }
                    if (index == checklnv) {
                        lnv = null;
                    }
                    if (index == checkhsnv) {
                        hsnv = null;
                    }
                    if (index == checkpc) {
                        pc = null;
                    }
                    mainform.tabbMain.remove(index);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            /**
             * show border button when mouse hover
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorderPainted(true);

                setForeground(Color.red);
            }

            /**
             * hide border when mouse not hover
             */
            @Override
            public void mouseExited(MouseEvent e) {
                setBorderPainted(false);
                setForeground(Color.BLACK);
            }
        }
    }

    void EnButton(JButton nut) {
        MouseListener l = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                nut.setBorderPainted(false);

                nut.setContentAreaFilled(true);
                nut.setBackground(Color.white);
                nut.setForeground(new Color(0, 102, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nut.setBorderPainted(false);
                nut.setContentAreaFilled(false);
                nut.setBackground(new Color(0, 102, 255));
                nut.setForeground(Color.white);
            }
        };
        nut.addMouseListener(l);
    }

    void setAvata() {
        String photo = Auth.user.getHinh();
        if (photo != null) {
            try {

                Icon image = XImage.read(photo);
                Avatar.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
            }
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
        btnHoSo = new javax.swing.JButton();
        pnlHoso = new javax.swing.JPanel();
        btnHoSoCT = new javax.swing.JButton();
        btnBangChamCong = new javax.swing.JButton();
        btnLuongNV = new javax.swing.JButton();
        btnDoiMatKhau = new javax.swing.JButton();
        btnPhongBan = new javax.swing.JButton();
        btnTD = new javax.swing.JButton();
        btnCV = new javax.swing.JButton();
        btnHoSoCT1 = new javax.swing.JButton();
        btnChamCong = new javax.swing.JButton();
        pnlChamCong = new javax.swing.JPanel();
        btnBangCong = new javax.swing.JButton();
        btnTangCa = new javax.swing.JButton();
        btnLuong = new javax.swing.JButton();
        pnlLuong = new javax.swing.JPanel();
        btnBangLuong = new javax.swing.JButton();
        btnUngLuong = new javax.swing.JButton();
        btnBaoHiem = new javax.swing.JButton();
        btnPhuCap = new javax.swing.JButton();
        btnThuTuc = new javax.swing.JButton();
        pnlThuTuc = new javax.swing.JPanel();
        btnHopDong = new javax.swing.JButton();
        btnKTKL = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        tabbMain = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        Avatar = new Swing.ImageAvatar();
        lblten = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HRM-Phần mềm quản lý nhân sự");
        setBackground(new java.awt.Color(51, 153, 255));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/User group.png"))); // NOI18N
        jLabel1.setText("Quản lý nhân sự");

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
                .addComponent(jLabel1))
        );

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setAutoscrolls(true);

        btnHoSo.setBackground(new java.awt.Color(51, 153, 255));
        btnHoSo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHoSo.setForeground(new java.awt.Color(255, 255, 255));
        btnHoSo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Clien list.png"))); // NOI18N
        btnHoSo.setText("Hồ sơ nhân viên");
        btnHoSo.setToolTipText("");
        btnHoSo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnHoSo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHoSo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnHoSo.setIconTextGap(10);
        btnHoSo.setMargin(new java.awt.Insets(2, 20, 3, 14));
        btnHoSo.setPreferredSize(new java.awt.Dimension(160, 35));
        btnHoSo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoSoActionPerformed(evt);
            }
        });
        jPanel2.add(btnHoSo);

        pnlHoso.setBackground(new java.awt.Color(0, 102, 255));
        pnlHoso.setPreferredSize(new java.awt.Dimension(160, 300));

        btnHoSoCT.setBackground(new java.awt.Color(0, 102, 255));
        btnHoSoCT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHoSoCT.setForeground(new java.awt.Color(255, 255, 255));
        btnHoSoCT.setText("Hồ sơ nhân viên");
        btnHoSoCT.setBorder(null);
        btnHoSoCT.setContentAreaFilled(false);
        btnHoSoCT.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnHoSoCT.setPreferredSize(new java.awt.Dimension(180, 25));
        btnHoSoCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoSoCTActionPerformed(evt);
            }
        });

        btnBangChamCong.setBackground(new java.awt.Color(0, 102, 255));
        btnBangChamCong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBangChamCong.setForeground(new java.awt.Color(255, 255, 255));
        btnBangChamCong.setText("Bảng chấm công");
        btnBangChamCong.setBorder(null);
        btnBangChamCong.setBorderPainted(false);
        btnBangChamCong.setContentAreaFilled(false);
        btnBangChamCong.setPreferredSize(new java.awt.Dimension(180, 25));
        btnBangChamCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangChamCongActionPerformed(evt);
            }
        });

        btnLuongNV.setBackground(new java.awt.Color(0, 102, 255));
        btnLuongNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuongNV.setForeground(new java.awt.Color(255, 255, 255));
        btnLuongNV.setText("Bảng lương");
        btnLuongNV.setBorder(null);
        btnLuongNV.setBorderPainted(false);
        btnLuongNV.setContentAreaFilled(false);
        btnLuongNV.setPreferredSize(new java.awt.Dimension(180, 25));
        btnLuongNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuongNVActionPerformed(evt);
            }
        });

        btnDoiMatKhau.setBackground(new java.awt.Color(0, 102, 255));
        btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.setBorder(null);
        btnDoiMatKhau.setBorderPainted(false);
        btnDoiMatKhau.setContentAreaFilled(false);
        btnDoiMatKhau.setPreferredSize(new java.awt.Dimension(180, 25));
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        btnPhongBan.setBackground(new java.awt.Color(0, 102, 255));
        btnPhongBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPhongBan.setForeground(new java.awt.Color(255, 255, 255));
        btnPhongBan.setText("Phòng ban");
        btnPhongBan.setBorder(null);
        btnPhongBan.setBorderPainted(false);
        btnPhongBan.setContentAreaFilled(false);
        btnPhongBan.setPreferredSize(new java.awt.Dimension(180, 25));
        btnPhongBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhongBanActionPerformed(evt);
            }
        });

        btnTD.setBackground(new java.awt.Color(0, 102, 255));
        btnTD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTD.setForeground(new java.awt.Color(255, 255, 255));
        btnTD.setText("Trình độ");
        btnTD.setBorder(null);
        btnTD.setBorderPainted(false);
        btnTD.setContentAreaFilled(false);
        btnTD.setPreferredSize(new java.awt.Dimension(180, 25));
        btnTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTDActionPerformed(evt);
            }
        });

        btnCV.setBackground(new java.awt.Color(0, 102, 255));
        btnCV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCV.setForeground(new java.awt.Color(255, 255, 255));
        btnCV.setText("Chức vụ");
        btnCV.setBorder(null);
        btnCV.setBorderPainted(false);
        btnCV.setContentAreaFilled(false);
        btnCV.setPreferredSize(new java.awt.Dimension(180, 25));
        btnCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCVActionPerformed(evt);
            }
        });

        btnHoSoCT1.setBackground(new java.awt.Color(0, 102, 255));
        btnHoSoCT1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHoSoCT1.setForeground(new java.awt.Color(255, 255, 255));
        btnHoSoCT1.setText("Hồ sơ");
        btnHoSoCT1.setBorder(null);
        btnHoSoCT1.setContentAreaFilled(false);
        btnHoSoCT1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnHoSoCT1.setPreferredSize(new java.awt.Dimension(180, 25));
        btnHoSoCT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoSoCT1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHosoLayout = new javax.swing.GroupLayout(pnlHoso);
        pnlHoso.setLayout(pnlHosoLayout);
        pnlHosoLayout.setHorizontalGroup(
            pnlHosoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHosoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHosoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCV, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnHoSoCT1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlHosoLayout.createSequentialGroup()
                        .addGroup(pnlHosoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHosoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnHoSoCT, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addComponent(btnBangChamCong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnTD, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addComponent(btnLuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addComponent(btnPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlHosoLayout.setVerticalGroup(
            pnlHosoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHosoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHoSoCT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHoSoCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBangChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPhongBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(pnlHoso);

        btnChamCong.setBackground(new java.awt.Color(51, 153, 255));
        btnChamCong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChamCong.setForeground(new java.awt.Color(255, 255, 255));
        btnChamCong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Clock.png"))); // NOI18N
        btnChamCong.setText("Chấm công");
        btnChamCong.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnChamCong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnChamCong.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnChamCong.setPreferredSize(new java.awt.Dimension(160, 35));
        btnChamCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChamCongActionPerformed(evt);
            }
        });
        jPanel2.add(btnChamCong);

        pnlChamCong.setBackground(new java.awt.Color(0, 102, 255));
        pnlChamCong.setPreferredSize(new java.awt.Dimension(160, 70));

        btnBangCong.setBackground(new java.awt.Color(0, 102, 255));
        btnBangCong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBangCong.setForeground(new java.awt.Color(255, 255, 255));
        btnBangCong.setText("Bảng công");
        btnBangCong.setBorder(null);
        btnBangCong.setBorderPainted(false);
        btnBangCong.setContentAreaFilled(false);
        btnBangCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangCongActionPerformed(evt);
            }
        });

        btnTangCa.setBackground(new java.awt.Color(0, 102, 255));
        btnTangCa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTangCa.setForeground(new java.awt.Color(255, 255, 255));
        btnTangCa.setText("Tăng ca");
        btnTangCa.setBorder(null);
        btnTangCa.setContentAreaFilled(false);
        btnTangCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTangCaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChamCongLayout = new javax.swing.GroupLayout(pnlChamCong);
        pnlChamCong.setLayout(pnlChamCongLayout);
        pnlChamCongLayout.setHorizontalGroup(
            pnlChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChamCongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTangCa, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(btnBangCong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlChamCongLayout.setVerticalGroup(
            pnlChamCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChamCongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBangCong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTangCa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel2.add(pnlChamCong);

        btnLuong.setBackground(new java.awt.Color(51, 153, 255));
        btnLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuong.setForeground(new java.awt.Color(255, 255, 255));
        btnLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Dollar.png"))); // NOI18N
        btnLuong.setText("Lương - Bảo hiểm");
        btnLuong.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLuong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLuong.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLuong.setPreferredSize(new java.awt.Dimension(160, 35));
        btnLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuongActionPerformed(evt);
            }
        });
        jPanel2.add(btnLuong);

        pnlLuong.setBackground(new java.awt.Color(0, 102, 255));
        pnlLuong.setPreferredSize(new java.awt.Dimension(160, 130));

        btnBangLuong.setBackground(new java.awt.Color(0, 102, 255));
        btnBangLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBangLuong.setForeground(new java.awt.Color(255, 255, 255));
        btnBangLuong.setText("Bảng Lương");
        btnBangLuong.setBorder(null);
        btnBangLuong.setContentAreaFilled(false);
        btnBangLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangLuongActionPerformed(evt);
            }
        });

        btnUngLuong.setBackground(new java.awt.Color(0, 102, 255));
        btnUngLuong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUngLuong.setForeground(new java.awt.Color(255, 255, 255));
        btnUngLuong.setText("Ứng lương");
        btnUngLuong.setBorder(null);
        btnUngLuong.setContentAreaFilled(false);
        btnUngLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUngLuongActionPerformed(evt);
            }
        });

        btnBaoHiem.setBackground(new java.awt.Color(0, 102, 255));
        btnBaoHiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBaoHiem.setForeground(new java.awt.Color(255, 255, 255));
        btnBaoHiem.setText("Bảo hiểm");
        btnBaoHiem.setBorder(null);
        btnBaoHiem.setContentAreaFilled(false);
        btnBaoHiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoHiemActionPerformed(evt);
            }
        });

        btnPhuCap.setBackground(new java.awt.Color(0, 102, 255));
        btnPhuCap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPhuCap.setForeground(new java.awt.Color(255, 255, 255));
        btnPhuCap.setText("Phụ cấp");
        btnPhuCap.setBorder(null);
        btnPhuCap.setContentAreaFilled(false);
        btnPhuCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhuCapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLuongLayout = new javax.swing.GroupLayout(pnlLuong);
        pnlLuong.setLayout(pnlLuongLayout);
        pnlLuongLayout.setHorizontalGroup(
            pnlLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPhuCap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBaoHiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUngLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBangLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlLuongLayout.setVerticalGroup(
            pnlLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLuongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUngLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBaoHiem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPhuCap, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(pnlLuong);

        btnThuTuc.setBackground(new java.awt.Color(51, 153, 255));
        btnThuTuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThuTuc.setForeground(new java.awt.Color(255, 255, 255));
        btnThuTuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Certificate.png"))); // NOI18N
        btnThuTuc.setText("Thủ tục");
        btnThuTuc.setToolTipText("");
        btnThuTuc.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThuTuc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThuTuc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThuTuc.setPreferredSize(new java.awt.Dimension(160, 35));
        btnThuTuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuTucActionPerformed(evt);
            }
        });
        jPanel2.add(btnThuTuc);

        pnlThuTuc.setBackground(new java.awt.Color(0, 102, 255));
        pnlThuTuc.setPreferredSize(new java.awt.Dimension(160, 70));

        btnHopDong.setBackground(new java.awt.Color(0, 102, 255));
        btnHopDong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHopDong.setForeground(new java.awt.Color(255, 255, 255));
        btnHopDong.setText("Hợp đồng lao động");
        btnHopDong.setBorder(null);
        btnHopDong.setBorderPainted(false);
        btnHopDong.setContentAreaFilled(false);
        btnHopDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHopDongActionPerformed(evt);
            }
        });

        btnKTKL.setBackground(new java.awt.Color(0, 102, 255));
        btnKTKL.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKTKL.setForeground(new java.awt.Color(255, 255, 255));
        btnKTKL.setText("Khen - Kỷ luật");
        btnKTKL.setBorder(null);
        btnKTKL.setContentAreaFilled(false);
        btnKTKL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKTKLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThuTucLayout = new javax.swing.GroupLayout(pnlThuTuc);
        pnlThuTuc.setLayout(pnlThuTucLayout);
        pnlThuTucLayout.setHorizontalGroup(
            pnlThuTucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThuTucLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThuTucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHopDong, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(btnKTKL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlThuTucLayout.setVerticalGroup(
            pnlThuTucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThuTucLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKTKL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel2.add(pnlThuTuc);

        btnThoat.setBackground(new java.awt.Color(51, 153, 255));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat.setForeground(new java.awt.Color(255, 255, 255));
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Log out.png"))); // NOI18N
        btnThoat.setText("Đăng xuất");
        btnThoat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThoat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThoat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThoat.setPreferredSize(new java.awt.Dimension(160, 35));
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel2.add(btnThoat);

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/User group.png"))); // NOI18N
        jLabel9.setText("Hệ thống quản trị nhân sự");

        lblDongHo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(255, 255, 255));
        lblDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/hrm/icon/Alarm.png"))); // NOI18N
        lblDongHo.setText("00:00:00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 755, Short.MAX_VALUE)
                .addComponent(lblDongHo)
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblDongHo))
                .addContainerGap())
        );

        tabbMain.setBackground(new java.awt.Color(255, 255, 255));
        tabbMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));

        Avatar.setBorderSize(3);
        Avatar.setBorderSpace(2);
        Avatar.setGradientColor1(new java.awt.Color(255, 153, 153));
        Avatar.setGradientColor2(new java.awt.Color(0, 0, 204));

        lblten.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblten.setForeground(new java.awt.Color(255, 255, 255));
        lblten.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblten.setText("Tên nhân viên");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Avatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblten, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbMain)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE))
                    .addComponent(tabbMain))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHoSoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoSoActionPerformed
        showSubMenu(pnlHoso);
    }//GEN-LAST:event_btnHoSoActionPerformed

    private void btnChamCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChamCongActionPerformed
        showSubMenu(pnlChamCong);
    }//GEN-LAST:event_btnChamCongActionPerformed

    private void btnLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuongActionPerformed
        showSubMenu(pnlLuong);
    }//GEN-LAST:event_btnLuongActionPerformed

    private void btnHoSoCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoSoCTActionPerformed
        if (hs == null) {
            try {
                hs = new HoSo();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Hồ sơ chi tiết", hs);
        }
        tabbMain.setSelectedComponent(hs);
        checkhs = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnHoSoCTActionPerformed

    private void btnThuTucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuTucActionPerformed
        showSubMenu(pnlThuTuc);
    }//GEN-LAST:event_btnThuTucActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        tabbMain.removeAll();
        hsnv = null;
        if (hsnv == null) {
            hsnv = new HoSoNV();
            tabbMain.addTab("Hồ sơ ", hsnv);
            tabbMain.setSelectedComponent(hsnv);
            checkhsnv = tabbMain.getSelectedIndex();
            int vt = tabbMain.getSelectedIndex();
            tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
        } else {
            hsnv.clear();
        }

        ktkl = null;

        ul = null;

        hd = null;

        bh = null;

        pc = null;

        lnv = null;

        ccnv = null;

        bl = null;

        bc = null;

        tc = null;

        hs = null;

        customDesing();

        openDN();
        hsnv.setForm(Auth.user);

    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnBangCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangCongActionPerformed
        if (bc == null) {
            try {
                bc = new BangCong();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Bảng chấm công", bc);
        }
        tabbMain.setSelectedComponent(bc);
        checkbc = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnBangCongActionPerformed

    private void btnTangCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangCaActionPerformed
        if (tc == null) {
            try {
                tc = new TangCaForm();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Bảng tăng ca", tc);
        }
        tabbMain.setSelectedComponent(tc);
        checktc = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnTangCaActionPerformed

    private void btnBangLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangLuongActionPerformed
        if (bl == null) {
            try {
                bl = new BangLuong();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Bảng lương", bl);
        }
        tabbMain.setSelectedComponent(bl);
        checkbl = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnBangLuongActionPerformed

    private void btnUngLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUngLuongActionPerformed
        if (ul == null) {
            try {
                ul = new UngLuongfrom();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Ứng lưng", ul);
        }
        tabbMain.setSelectedComponent(ul);
        checkul = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnUngLuongActionPerformed

    private void btnBaoHiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoHiemActionPerformed
        if (bh == null) {
            try {
                bh = new BaoHiemform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Hồ sơ bảo hiểm", bh);
        }
        tabbMain.setSelectedComponent(bh);
        checkbh = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnBaoHiemActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        new DoiMatKhau(this, true).setVisible(true);
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void btnHopDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHopDongActionPerformed
        if (hd == null) {
            try {
                hd = new HopDongform();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Hợp đồng lao động", hd);
        }
        tabbMain.setSelectedComponent(hd);
        checkhd = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnHopDongActionPerformed

    private void btnKTKLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKTKLActionPerformed
        if (ktkl == null) {
            try {
                ktkl = new KTKLForm();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Khen-Kỷ luật", ktkl);
        }
        tabbMain.setSelectedComponent(ktkl);
        checkktkl = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnKTKLActionPerformed

    private void btnPhongBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhongBanActionPerformed
        new PhongBanJDialog(this, true).setVisible(true);
    }//GEN-LAST:event_btnPhongBanActionPerformed

    private void btnTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTDActionPerformed
        new TrinhDoJDialog(this, true).setVisible(true);
    }//GEN-LAST:event_btnTDActionPerformed

    private void btnPhuCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhuCapActionPerformed
        if (pc == null) {
            try {
                pc = new PhuCapPanel();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Phụ cấp", pc);
        }
        tabbMain.setSelectedComponent(pc);
        checkpc = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnPhuCapActionPerformed

    private void btnBangChamCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangChamCongActionPerformed
        if (ccnv == null) {
            try {
                ccnv = new BangChamCongNV();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Chấm công", ccnv);
        }
        tabbMain.setSelectedComponent(ccnv);
        checkccnv = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnBangChamCongActionPerformed

    private void btnLuongNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuongNVActionPerformed
        if (lnv == null) {
            try {
                lnv = new BangLuongNV();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Lương", lnv);
        }
        tabbMain.setSelectedComponent(lnv);
        checklnv = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnLuongNVActionPerformed

    private void btnCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCVActionPerformed
        new ChucVuJDialog(this, true).setVisible(true);
    }//GEN-LAST:event_btnCVActionPerformed

    private void btnHoSoCT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoSoCT1ActionPerformed
        if (hsnv == null) {
            try {
                hsnv = new HoSoNV();
            } catch (Exception ex) {
            }
            tabbMain.addTab("Hồ sơ", hsnv);
        }
        tabbMain.setSelectedComponent(hsnv);
        checkhsnv = tabbMain.getSelectedIndex();
        int vt = tabbMain.getSelectedIndex();
        tabbMain.setTabComponentAt(vt, new DemoCustomTab(this));
    }//GEN-LAST:event_btnHoSoCT1ActionPerformed

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
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mainform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Swing.ImageAvatar Avatar;
    private javax.swing.JButton btnBangChamCong;
    private javax.swing.JButton btnBangCong;
    private javax.swing.JButton btnBangLuong;
    private javax.swing.JButton btnBaoHiem;
    private javax.swing.JButton btnCV;
    private javax.swing.JButton btnChamCong;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnHoSo;
    private javax.swing.JButton btnHoSoCT;
    private javax.swing.JButton btnHoSoCT1;
    private javax.swing.JButton btnHopDong;
    private javax.swing.JButton btnKTKL;
    private javax.swing.JButton btnLuong;
    private javax.swing.JButton btnLuongNV;
    private javax.swing.JButton btnPhongBan;
    private javax.swing.JButton btnPhuCap;
    private javax.swing.JButton btnTD;
    private javax.swing.JButton btnTangCa;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnThuTuc;
    private javax.swing.JButton btnUngLuong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblten;
    private javax.swing.JPanel pnlChamCong;
    private javax.swing.JPanel pnlHoso;
    private javax.swing.JPanel pnlLuong;
    private javax.swing.JPanel pnlThuTuc;
    private javax.swing.JTabbedPane tabbMain;
    // End of variables declaration//GEN-END:variables
}
