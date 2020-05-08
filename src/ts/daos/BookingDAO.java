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
import ts.dtos.BookingDTO;
import ts.utils.DBUtils;

/**
 *
 * @author SE130447
 */
public class BookingDAO {

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

    public Vector<BookingDTO> getBookingHistoryByUser(String userID) throws SQLException {
        Vector<BookingDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT bookingID, bookingDate, subTotal, discountCode, total "
                        + "FROM tblBooking "
                        + "WHERE userID = ? AND status = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setBoolean(2, true);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int bookingID = rs.getInt("bookingID");
                    Timestamp bookingDate = rs.getTimestamp("bookingDate");
                    float subTotal = rs.getFloat("subTotal");
                    String discountCode = rs.getString("discountCode");
                    float total = rs.getFloat("total");
                    BookingDTO dto = new BookingDTO(bookingID, userID, bookingDate, subTotal, discountCode, total);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDAO - getBookingHistoryByUser: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }

    public int getNextBookingID() throws SQLException {
        int num = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT MAX(bookingID) AS 'max' "
                        + "FROM tblBooking";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    num = rs.getInt("max");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDAO - getNextBookingID: " + e.getMessage());
        } finally {
            closeConnection();
            return ++num;
        }
    }

    public boolean insertBooking(BookingDTO dto) throws SQLException {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String discountCode = dto.getDiscountCode();
                String sql;
                if (discountCode == null || discountCode.isEmpty()) {
                    sql = "INSERT INTO tblBooking(bookingID, userID, subTotal, total) VALUES(?, ?, ?, ?)";
                    discountCode = null;
                } else {
                    sql = "INSERT INTO tblBooking(bookingID, userID, subTotal, total, discountCode) VALUES(?, ?, ?, ?, ?)";
                }
                ps = conn.prepareStatement(sql);
                ps.setInt(1, dto.getBookingID());
                ps.setString(2, dto.getUserID());
                ps.setFloat(3, dto.getSubTotal());
                ps.setFloat(4, dto.getTotal());
                if (discountCode != null) {
                    ps.setString(5, discountCode);
                }
                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDAO - insertBooking: " + e.getMessage());
        } finally {
            closeConnection();
            return check;
        }
    }

    public boolean deleteBooking(int bookingID) throws SQLException {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "DELETE FROM tblBooking WHERE bookingID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, bookingID);
                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDAO - deleteBooking: " + e.getMessage());
        } finally {
            closeConnection();
            return check;
        }
    }

    public boolean removeBooking(int bookingID) throws SQLException {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "UPDATE tblBooking SET status = ? WHERE bookingID = ?";
                ps = conn.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setInt(2, bookingID);
                check = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDAO - removeBooking: " + e.getMessage());
        } finally {
            closeConnection();
            return check;
        }
    }

    public Vector<BookingDTO> getBookingHistoryByUserAndDate(String userID, Timestamp bookingDate) throws SQLException {
        Vector<BookingDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT bookingID, bookingDate, subTotal, discountCode, total "
                        + "FROM tblBooking "
                        + "WHERE userID = ? AND bookingDate = ? AND status = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setTimestamp(2, bookingDate);
                ps.setBoolean(3, true);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int bookingID = rs.getInt("bookingID");
                    float subTotal = rs.getFloat("subTotal");
                    String discountCode = rs.getString("discountCode");
                    float total = rs.getFloat("total");
                    BookingDTO dto = new BookingDTO(bookingID, userID, bookingDate, subTotal, discountCode, total);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at BookingDAO - getBookingHistoryByUser: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }
}
