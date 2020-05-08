/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.dtos;

/**
 *
 * @author SE130447
 */
public class HotelDTO {
    private int hotelID;
    private String hotelName;
    private int hotelAreaID;
    private String address;
    private String description;
    private String phone;

    public HotelDTO() {
    }

    public HotelDTO(int hotelID, String hotelName, int hotelAreaID, String address, String description, String phone) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.hotelAreaID = hotelAreaID;
        this.address = address;
        this.description = description;
        this.phone = phone;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelAreaID() {
        return hotelAreaID;
    }

    public void setHotelAreaID(int hotelAreaID) {
        this.hotelAreaID = hotelAreaID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
