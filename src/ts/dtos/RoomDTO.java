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
public class RoomDTO {
    private int roomID;
    private String roomName;
    private int quantity;
    private String type;
    private int hotelID;
    private float price;
    private String description;

    public RoomDTO() {
    }

    public RoomDTO(int roomID, String roomName, int quantity, String type, int hotelID, float price, String description) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.quantity = quantity;
        this.type = type;
        this.hotelID = hotelID;
        this.price = price;
        this.description = description;
    }
    
    public RoomDTO(String roomName, String type, int hotelID, float price, String description) {
        this.roomName = roomName;
        this.type = type;
        this.hotelID = hotelID;
        this.price = price;
        this.description = description;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
