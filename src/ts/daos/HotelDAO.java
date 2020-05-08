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
import ts.dtos.HotelDTO;
import ts.dtos.RoomDTO;
import ts.utils.DBUtils;

/**
 *
 * @author SE130447
 */
public class HotelDAO {

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

    public Vector<HotelDTO> getHotelByName(String name) throws SQLException {
        Vector<HotelDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelID, hotelName, hotelAreaID, address, description, phone "
                        + "FROM tblHotel "
                        + "WHERE hotelName LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                rs = ps.executeQuery();
                HotelDTO dto;
                while (rs.next()) {
                    int hotelID = rs.getInt("hotelID");
                    String hotelName = rs.getString("hotelName");
                    int hotelAreaID = rs.getInt("hotelAreaID");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String phone = rs.getString("phone");
                    dto = new HotelDTO(hotelID, hotelName, hotelAreaID, address, description, phone);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelDAO - getHotelByName: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }

    public Vector<HotelDTO> getHotelByArea(int areaID) throws SQLException {
        Vector<HotelDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelID, hotelName, hotelAreaID, address, description, phone "
                        + "FROM tblHotel "
                        + "WHERE hotelAreaID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, areaID);
                rs = ps.executeQuery();
                HotelDTO dto;
                while (rs.next()) {
                    int hotelID = rs.getInt("hotelID");
                    String hotelName = rs.getString("hotelName");
                    int hotelAreaID = rs.getInt("hotelAreaID");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String phone = rs.getString("phone");
                    dto = new HotelDTO(hotelID, hotelName, hotelAreaID, address, description, phone);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelDAO - getHotelByArea: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }

    public Vector<HotelDTO> getHotelByNameAndArea(String name, int areaID) throws SQLException {
        Vector<HotelDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelID, hotelName, hotelAreaID, address, description, phone "
                        + "FROM tblHotel "
                        + "WHERE hotelName LIKE ? AND hotelAreaID = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                ps.setInt(2, areaID);
                rs = ps.executeQuery();
                HotelDTO dto;
                while (rs.next()) {
                    int hotelID = rs.getInt("hotelID");
                    String hotelName = rs.getString("hotelName");
                    int hotelAreaID = rs.getInt("hotelAreaID");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String phone = rs.getString("phone");
                    dto = new HotelDTO(hotelID, hotelName, hotelAreaID, address, description, phone);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelDAO - getHotelByNameAndArea: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }

    public int getNumOfAvailableRommOfHotel(int hotelID, Timestamp dateIn, Timestamp dateOut, int quantity) throws SQLException {
        int num = 0;
        try {
            RoomDAO rd = new RoomDAO();
            Vector<RoomDTO> list = rd.getRoomByHotelID(hotelID);
            if (list != null) {
                for (RoomDTO dto : list) {
                    if (rd.isAvailableRoom(dateIn, dateOut, dto.getRoomID(), quantity)) {
                        num += rd.getNumOfAvailableOfRoom(dateIn, dateOut, dto.getRoomID());
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelDAO - getNumOfAvailableRommOfHotel: " + e.getMessage());
        } finally {
            return num;
        }
    }

    public int getIDByName(String name) throws SQLException {
        int num = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelID "
                        + "FROM tblHotel "
                        + "WHERE hotelName = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num = rs.getInt("hotelID");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelDAO - getIDByName: " + e.getMessage());
        } finally {
            closeConnection();
            return num;
        }
    }

    public String getNameByID(int id) throws SQLException {
        String name = "";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelName "
                        + "FROM tblHotel "
                        + "WHERE hotelID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("hotelName");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelDAO - getNameByID: " + e.getMessage());
        } finally {
            closeConnection();
            return name;
        }
    }
}
