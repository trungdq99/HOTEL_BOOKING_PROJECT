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
public class UserDTO {

    private String userID;
    private String userName;
    private String password;
    private String phone;
    private String address;
    private Timestamp createDate;
    private String role;
    private boolean status;

    public UserDTO() {
    }

    public UserDTO(String userID, String userName, String role) {
        this.userID = userID;
        this.userName = userName;
        this.role = role;
    }

    public UserDTO(String userID, String userName, String password, String phone, String address, Timestamp createDate, String role, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.createDate = createDate;
        this.role = role;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
}
