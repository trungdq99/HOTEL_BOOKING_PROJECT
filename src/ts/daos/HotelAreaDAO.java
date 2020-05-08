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
import ts.dtos.HotelAreaDTO;
import ts.utils.DBUtils;

/**
 *
 * @author SE130447
 */
public class HotelAreaDAO {

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

    public int getIDByName(String name) throws SQLException {
        int num = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelAreaID "
                        + "FROM tblHotelArea "
                        + "WHERE hotelAreaName = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                rs = ps.executeQuery();
                if (rs.next()) {
                    num = rs.getInt("hotelAreaID");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelAreaDAO - getIDByName: " + e.getMessage());
        } finally {
            closeConnection();
            return num;
        }
    }
    
    public Vector<HotelAreaDTO> getAllHotelArea() throws SQLException{
        Vector<HotelAreaDTO> list = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelAreaID, hotelAreaName "
                        + "FROM tblHotelArea";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                HotelAreaDTO dto;
                while (rs.next()) {
                    int hotelAreaID = rs.getInt("hotelAreaID");
                    String hotelAreaName = rs.getString("hotelAreaName");
                    dto = new HotelAreaDTO(hotelAreaID, hotelAreaName);
                    if (list == null) {
                        list = new Vector<>();
                    }
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelAreaDAO - getAllHotelArea: " + e.getMessage());
        } finally {
            closeConnection();
            return list;
        }
    }
    
    public String getNameByID(int id) throws SQLException{
        String name="";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT hotelAreaName "
                        + "FROM tblHotelArea "
                        + "WHERE hotelAreaID = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("hotelAreaName");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at HotelAreaDAO - getNameByID: " + e.getMessage());
        }finally {
            closeConnection();
            return name;
        }
    }
}
