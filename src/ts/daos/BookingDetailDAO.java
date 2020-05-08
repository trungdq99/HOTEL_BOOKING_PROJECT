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
import ts.dtos.BookingDetailDTO;
import ts.utils.DBUtils;

/**
 *
 * @author SE130447
 *
 */
public class BookingDetailDAO {

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

    public int getNumOfOccupiedOfRoom(Timestamp dateIn, Timestamp dateOut, int roomID) throws SQLException {
        int num = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT SUM(Quantity) AS 'NumOfOccupied' "
                        + "FROM tblBookingDetail "
                        + "WHERE DateOut > ? AND DateIn < ? AND RoomID = ? AND status = ? "
                        + "GROUP BY RoomID";
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, dateIn);
                ps.setTimestamp(2, dateOut);
                ps.setInt(3, roomID);
                ps.setBoolean(4, true);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num = rs.getInt("NumOfOccupied");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDetailDAO - getNumOfOccupiedRoom: " + e.getMessage());
        } finally {
            closeConnection();
            return num;
        }
    }

    public boolean reloadBookingCart(Vector<BookingDetailDTO> bookingCart) {
        boolean check = false;
        try {
            for (BookingDetailDTO dto : bookingCart) {
                if (new RoomDAO().isAvailableRoom(dto.getDateIn(), dto.getDateOut(), dto.getRoomID(), dto.getQuantity()) == false) {
                    bookingCart.remove(dto);
                    check = true;
                }
            }
        } catch (Exception e) {
        } finally {
            return check;
        }
    }

    public boolean insertBookingDetail(BookingDetailDTO dto) throws SQLException {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO tblBookingDetail(roomID, bookingID, quantity, dateIn, dateOut, status) VALUES(?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, dto.getRoomID());
                ps.setInt(2, dto.getBookingID());
                ps.setInt(3, dto.getQuantity());
                ps.setTimestamp(4, dto.getDateIn());
                ps.setTimestamp(5, dto.getDateOut());
                ps.setBoolean(6, true);
                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDetailDAO - insertBookingDetail: " + e.getMessage());
        } finally {
            closeConnection();
            return check;
        }
    }
    
    public boolean removeBookingDetail(int bookingID) throws SQLException{
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblBookingDetail SET status = ? WHERE bookingID = ?";
                ps = conn.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setInt(2, bookingID);
                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDetailDAO - removeBookingDetail: " + e.getMessage());
        } finally {
            closeConnection();
            return check;
        }
    }
}
