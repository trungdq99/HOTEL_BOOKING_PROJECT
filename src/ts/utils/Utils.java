/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author SE130447
 */
public class Utils {

    public static String encryptPassword(String password) {
        StringBuilder hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        } finally {
            return hexString.toString();
        }
    }

    public static boolean isIntegerNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPositiveIntegerNumber(String number) {
        if (isIntegerNumber(number)) {
            if (Integer.parseInt(number) > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[^!#$@]+@[^!#$@]+$");
    }
    
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^0[0-9]{9}$");
    }
    
    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        df.setLenient(false);
        try {
            df.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^[0-9A-Za-z]{6,20}$");
    }
    
    public static void main(String[] args) {
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date date = sdf.parse(sdf.format(new Date()));
            System.out.println(date.toString());
            Date dateIn = sdf.parse("03-17-2020");
            System.out.println(dateIn.toString());
            System.out.println(date.compareTo(dateIn));
            System.out.println(TimeUnit.MILLISECONDS.toDays(date.getTime() - dateIn.getTime()));
        } catch (ParseException ex) {
        }
    }
}
