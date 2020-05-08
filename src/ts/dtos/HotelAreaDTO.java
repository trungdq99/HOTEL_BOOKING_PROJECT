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
public class HotelAreaDTO {
    private int hotelAreaID;
    private String hotelAreaName;

    public HotelAreaDTO() {
    }

    public HotelAreaDTO(int hotelAreaID, String hotelAreaName) {
        this.hotelAreaID = hotelAreaID;
        this.hotelAreaName = hotelAreaName;
    }

    public int getHotelAreaID() {
        return hotelAreaID;
    }

    public void setHotelAreaID(int hotelAreaID) {
        this.hotelAreaID = hotelAreaID;
    }

    public String getHotelAreaName() {
        return hotelAreaName;
    }

    public void setHotelAreaName(String hotelAreaName) {
        this.hotelAreaName = hotelAreaName;
    }
    
}
