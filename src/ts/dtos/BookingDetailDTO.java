/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.dtos;

import java.sql.Timestamp;

/**
 *
 * @author SE130447
 */
public class BookingDetailDTO {

    private int roomID;
    private int bookingID;
    private int quantity;
    private Timestamp dateIn;
    private Timestamp dateOut;
    private float total;

    public BookingDetailDTO() {
    }

    public BookingDetailDTO(int roomID, int bookingID, int quantity, Timestamp dateIn, Timestamp dateOut, float total) {
        this.roomID = roomID;
        this.bookingID = bookingID;
        this.quantity = quantity;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.total = total;
    }

    public BookingDetailDTO(int roomID, int quantity, Timestamp dateIn, Timestamp dateOut) {
        this.roomID = roomID;
        this.quantity = quantity;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    
    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getDateIn() {
        return dateIn;
    }

    public void setDateIn(Timestamp dateIn) {
        this.dateIn = dateIn;
    }

    public Timestamp getDateOut() {
        return dateOut;
    }

    public void setDateOut(Timestamp dateOut) {
        this.dateOut = dateOut;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean equals(BookingDetailDTO dto) {
        return this.roomID == dto.getRoomID();
    }

}
