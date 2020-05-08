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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import ts.utils.DBUtils;

/**
 *
 * @author nhocc
 */
public class DiscountCodeDAO {

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

    public boolean checkDiscount(String discountCode) throws SQLException {
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT expiryDate "
                        + "FROM tblDiscount "
                        + "WHERE discountCode = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, discountCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Timestamp expiryDate = rs.getTimestamp("expiryDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = sdf.parse(sdf.format(new Date()));
                    if (expiryDate.compareTo(date) >= 0) {
                        check = true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at DiscountCodeDAO - checkDiscount: " + e.getMessage());
        } finally {
            closeConnection();
            return check;
        }
    }

    public float getPercent(String discountCode) throws SQLException {
        float percent = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT PercentDiscount "
                        + "FROM tblDiscount "
                        + "WHERE discountCode = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, discountCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    percent = rs.getFloat("PercentDiscount");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error at DiscountCodeDAO - checkDiscount: " + e.getMessage());
        } finally {
            closeConnection();
            return percent;
        }
    }
}
