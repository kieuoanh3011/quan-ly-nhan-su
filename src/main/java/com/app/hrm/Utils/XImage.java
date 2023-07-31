/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author oanh
 */
public class XImage {//tạo 1 biến url có kiểu là url
    public static Image getAppIcon(){
        URL url = XImage.class.getResource("/com/app/hrm/icon/logo.png");//lấy đường dẫn
        return new ImageIcon(url).getImage();//trả về kiểu imageicon có đường dẫn,lấy ra đường ảnh
    }
     public static void save(File src){//save tạo 1 biến file dst
        File dst = new File("\\logo",src.getName());//lấy đường dẫn vào file logo lấy tên file
        if(!dst.getParentFile().exists()){//nếu file đấy k tồn tại thì sẽ tạo 1 file mới có tên là logo
            dst.getParentFile().mkdirs();
    }
        try{
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to,StandardCopyOption.REPLACE_EXISTING);//lấy dường dẫn tuyệt đối của file src
            
            //copy từ chỗ from sang chỗ to có kiểu là copy chèn
        }catch (Exception e){
            
    }
    }
     public static ImageIcon read(String fileName){//đọc file icon
         File path = new File("\\logo",fileName);// biến path kiểu dữ liệu là file có đường dãn là logo
         return new ImageIcon(path.getAbsolutePath());//lấy đường dẫn quqa file  ảnh trả
    }
     
      
}
