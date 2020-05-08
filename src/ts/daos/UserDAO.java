/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import ts.dtos.UserDTO;
import ts.utils.DBUtils;

/**
 *
 * @author SE130447
 */
public class UserDAO {

    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public UserDTO checkLogin(String userID, String password) throws SQLException {
        UserDTO user = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT userName, role FROM tblUser WHERE userID = ? AND password = ? AND status = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setString(2, password);
                ps.setBoolean(3, true);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String userName = rs.getString("userName");
                    String role = rs.getString("role");
                    user = new UserDTO(userID, userName, role);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at UserDAO - checkLogin: " + e.getMessage());
        } finally {
            closeConnection();
            return user;
        }
    }

    public boolean createUser(UserDTO dto) throws SQLException {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblUser(userID, username, password, phone, address, createDate, status, role) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, dto.getUserID());
                ps.setString(2, dto.getUserName());
                ps.setString(3, dto.getPassword());
                ps.setString(4, dto.getPhone());
                ps.setString(5, dto.getAddress());
                ps.setTimestamp(6, dto.getCreateDate());
                ps.setBoolean(7, dto.isStatus());
                ps.setString(8, dto.getRole());

                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate")) {
                JOptionPane.showMessageDialog(null, dto.getUserID() + " is existed!");
            } else {
                JOptionPane.showMessageDialog(null, "Error at UserDAO - createUser: " + e.getMessage());
            }
        } finally {
            closeConnection();
            return check;
        }
    }
    
    public Vector<String> getUserIDByName(String name) throws SQLException{
        Vector<String> list = null;
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                String sql = "SELECT userID FROM tblUser WHERE userName LIKE ?";
                ps=conn.prepareStatement(sql);
                ps.setString(1, "%"+name+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    if(list == null) list = new Vector();
                    list.add(rs.getString("userID"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at UserDAO - getUserIDByName: " + e.getMessage());
        }finally{
            closeConnection();
            return list;
        }
    }
}
