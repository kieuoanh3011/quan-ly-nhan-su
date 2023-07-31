/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author oanh
 */
public class MsgBox {//form tra, hiện tin nhắn, hiện thông báo tin nhắn ở form tra có lời nhắn là
     public static void alert(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message,
                "Hệ thống quản lý nhân sự", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean confirm(Component parent, String message){
        int result = JOptionPane.showConfirmDialog(parent, message,
                "Hệ thống quản lý nhân sự", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;//hiện bảng để chọn có kiểu yes khi nó trả về nó sẽ trả về yes nếu đúng trả về true nếu sai trả về false
    }
    
    public static String prompt(Component parent, String message){//show ra bảng nhập dữ liệu vào
        return JOptionPane.showInputDialog(parent, message,
                "Hệ thống quản lý nhân sự", JOptionPane.INFORMATION_MESSAGE);
    }
}
