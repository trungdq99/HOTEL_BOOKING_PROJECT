/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.dtos;

import java.sql.Timestamp;
import java.util.Comparator;

/**
 *
 * @author SE130447
 */
public class BookingDTO implements Comparator<BookingDTO> {

    private int bookingID;
    private String userID;
    private Timestamp bookingDate;
    private float subTotal;
    private String discountCode;
    private float total;

    public BookingDTO() {
    }

    public BookingDTO(int bookingID, String userID, Timestamp bookingDate, float subTotal, String discountCode, float total) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.bookingDate = bookingDate;
        this.subTotal = subTotal;
        this.discountCode = discountCode;
        this.total = total;
    }

    public BookingDTO(Timestamp bookingDate, float subTotal, String discountCode, float total) {
        this.bookingDate = bookingDate;
        this.subTotal = subTotal;
        this.discountCode = discountCode;
        this.total = total;
    }

    public BookingDTO(String userID, float subTotal, String discountCode, float total) {
        this.userID = userID;
        this.subTotal = subTotal;
        this.discountCode = discountCode;
        this.total = total;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public int compare(BookingDTO o1, BookingDTO o2) {
        return o1.getBookingDate().compareTo(o2.getBookingDate());
    }

    @Override
    public String toString() {
        return "BookingDTO{" + "bookingID=" + bookingID + ", userID=" + userID + ", bookingDate=" + bookingDate + ", subTotal=" + subTotal + ", discountCode=" + discountCode + ", total=" + total + '}';
    }
    
    

}
