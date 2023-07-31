/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Utils;

import com.app.hrm.Entity.NhanVien;

/**
 *
 * @author oanh
 */
public class Auth {
    public static NhanVien user = null; //tạo 1 biến user có kiểu nhân viên -> lưu thông tin đăng nhập
    public static void clear(){//hàm clear xoá dữ liệu use
        Auth.user = null;
    }
    
    public static boolean isLogin(){//islogin kiểm tra xem user đã có dữ liệu hay chưa
        return Auth.user != null;
    }
    
    public static boolean isHR(){//is manager có thêm vai trò
        return Auth.isLogin() && user.getIDPB() == 1;
    }
    
    public static boolean isBoss(){
        return Auth.isHR() && user.getIDCV() == 3;
}
}
