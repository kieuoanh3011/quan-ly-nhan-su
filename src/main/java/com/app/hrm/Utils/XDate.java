/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.hrm.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author oanh
 */
public class XDate {//dùng để chuyển đổi kiệu dữ liệu ngày và kiểu dữ liệu string
    static SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");

    public static Date toDate(String date) {//định dạng kiểu dữ liệu
        try {
            
            System.out.println(date);
            return formater.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String toString(Date date) {//kiểm tra dữ kiểu , kiểu dữ liwwuj ngày
        
       
        return formater.format(date);
    }

    public static Date addDays(Date date, long days) {// nhập vào số ngày cần xuất
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }

    public static Date add(int days) {
        Date now = XDate.now();
        now.setTime(now.getTime() + days * 24 * 60 * 60 * 1000);
        return now;
    }

    public static Date now() {
        return new Date();
    }
}
