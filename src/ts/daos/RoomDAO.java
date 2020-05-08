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
import java.sql.Timestamp;
import java.util.Vector;
import javax.swing.JOptionPane;
import ts.dtos.RoomDTO;
import ts.utils.DBUtils;

/**
 *
 * @author SE130447
 */
public class RoomDAO {

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

    public int getQuantityOfRoom(int roomID) throws SQLException {
        int num = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT quantity "
                        + "FROM tblRoom "
                        + "WHERE roomID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, roomID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num = rs.getInt("quantity");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at RoomDAO - getQuantityOfRoom: " + e.getMessage());
        } finally {
            closeConnection();
            return num;
        }
    }

    public int getNumOfAvailableOfRoom(Timestamp dateIn, Timestamp dateOut, int roomID) throws SQLException {
        int num = 0;
        try {
            int numOfOccupiedOfRoom = new BookingDetailDAO().getNumOfOccupiedOfRoom(dateIn, dateOut, roomID);
            int quantityOfRoom = getQuantityOfRoom(roomID);
            num = quantityOfRoom - numOfOccupiedOfRoom;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at RoomDAO - getNumOfOccupiedRoom: " + e.getMessage());
        } finally {
            closeConnection();
            return num;
        }
    }

    public boolean isAvailableRoom(Timestamp dateIn, Timestamp dateOut, int roomID, int quantity) throws SQLException {
        try {
            int numOfAvailable = getNumOfAvailableOfRoom(dateIn, dateOut, roomID);
            return numOfAvailable >= quantity;
        } catch (Exception e) {
        }
        return false;
    }

    public Vector<RoomDTO> getRoomByHotelID(int hotelID) throws SQLException {
        Vector<RoomDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT roomID, roomName, quantity, type, price, description "
                        + "FROM tblRoom "
                        + "WHERE hotelID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, hotelID);
                rs = ps.executeQuery();
                RoomDTO dto;
                while (rs.next()) {
                    int roomID = rs.getInt("roomID");
                    String roomName = rs.getString("roomName");
                    int quantity = rs.getInt("quantity");
                    String type = rs.getString("type");
                    float price = rs.getFloat("price");
                    String description = rs.getString("description");
                    dto = new RoomDTO(roomID, roomName, quantity, type, hotelID, price, description);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at RoomDAO - getRoomByHotelID: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }

    public int getIDByName(String name) throws SQLException {
        int num = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT roomID "
                        + "FROM tblRoom "
                        + "WHERE roomName = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num = rs.getInt("roomID");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at RoomDAO - getIDByName: " + e.getMessage());
        } finally {
            closeConnection();
            return num;
        }
    }

    public RoomDTO getRoomByID(int id) throws SQLException {
        RoomDTO dto = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT roomName, type, hotelID, price, description "
                        + "FROM tblRoom "
                        + "WHERE roomID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String roomName = rs.getString("roomName");
                    String type = rs.getString("type");
                    int hotelID = rs.getInt("hotelID");
                    float price = rs.getFloat("price");
                    String description = rs.getString("description");
                    dto = new RoomDTO(roomName, type, hotelID, price, description);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at RoomDAO - getRoomByID: " + e.getMessage());
        } finally {
            closeConnection();
            return dto;
        }
    }
}
