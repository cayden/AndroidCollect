package com.cayden.collect.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cuiran
 * Time  17/3/7 11:21
 * Email cuiran2001@163.com
 * Description
 */

public class Md5Encoder {

    public static String encode(String password){
        try{
            MessageDigest digest=MessageDigest.getInstance("MD5");
            byte[] result=digest.digest(password.getBytes());
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<result.length;i++){
                int number=result[i]&0xff;
                String str=Integer.toHexString(number);
                if(str.length()==1){
                    sb.append("0");
                    sb.append(str);
                }else {
                    sb.append(str);
                }
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return "";
        }
    }
}
