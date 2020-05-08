/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ts.daos.BookingDAO;
import ts.daos.BookingDetailDAO;
import ts.daos.UserDAO;
import ts.dtos.BookingDTO;
import ts.dtos.UserDTO;
import ts.utils.Utils;

/**
 *
 * @author nhocc
 */
public class History extends javax.swing.JFrame {

    private UserDTO user;

    /**
     * Creates new form History
     */
    public History(UserDTO user) {
        initComponents();
        this.setSize(1005, 800);
        this.setLocationRelativeTo(null);
        this.user = user;
        btnShowAll.setVisible(false);
        pnSpFunction.setVisible(false);
        loadUser();

    }

    public void loadUser() {
        if (user == null) {
            lbWelcome.setText("Not login!");
        } else {
            lbWelcome.setText("Welcome " + user.getUserName());
            String role = user.getRole();
            if (role.equals("sp")) {
                btnShowAll.setVisible(true);
            } else if (role.equals("ad")) {
                pnSpFunction.setVisible(true);
                loadTable(getColumnHeaderOfSearchResult(), getColumnDataOfSearchResult());
                return;
            }
            loadTable(getColumnHeaderOfCurUser(), getColumnDataOfCurUser());
        }
    }

    public void loadTable(Vector columnHeader, Vector<Vector<String>> columnData) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(columnData, columnHeader);
        tblList.setModel(dtm);
        tblList.updateUI();
    }

    public Vector getColumnHeaderOfCurUser() {
        Vector columnHeader = new Vector();
        columnHeader.add("Booking Date");
        columnHeader.add("Sub Total");
        columnHeader.add("Discount Code");
        columnHeader.add("Total");
        return columnHeader;
    }

    public Vector<Vector<String>> getColumnDataOfCurUser() {
        Vector<Vector<String>> columnData = new Vector<>();
        try {
            Vector<BookingDTO> list = new BookingDAO().getBookingHistoryByUser(user.getUserID());
            Collections.sort(list, new BookingDTO());
            for (BookingDTO bookingDTO : list) {
                Vector v = new Vector();
                v.add(new SimpleDateFormat("MM-dd-yyyy").format(bookingDTO.getBookingDate()));
                v.add(bookingDTO.getSubTotal() + "");
                v.add(bookingDTO.getDiscountCode());
                v.add(bookingDTO.getTotal());
                columnData.add(v);
            }
            return columnData;
        } catch (Exception e) {
        }
        return null;
    }

    public Vector getColumnHeaderOfSearchResult() {
        Vector columnHeader = new Vector();
        columnHeader.add("bookingID");
        columnHeader.add("UserID");
        columnHeader.add("Booking Date");
        columnHeader.add("Sub Total");
        columnHeader.add("Discount Code");
        columnHeader.add("Total");
        return columnHeader;
    }

    public Vector<Vector<String>> getColumnDataOfSearchResult() {
        Vector<Vector<String>> columnData = new Vector<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            BookingDAO bd = new BookingDAO();
            String bookingDate = txtBookingDate.getText().trim();
            Vector<String> user = new UserDAO().getUserIDByName(txtName.getText());
            for (String userID : user) {
                Vector<BookingDTO> list = null;
                if (bookingDate.isEmpty()) {
                    list = bd.getBookingHistoryByUser(userID);
                } else if (Utils.isValidDate(bookingDate)) {
                    Timestamp date = new Timestamp(sdf.parse(bookingDate).getTime());
                    list = bd.getBookingHistoryByUserAndDate(userID, date);
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong format or not valid date!");
                }
                if (list != null && !list.isEmpty()) {
                    for (BookingDTO bookingDTO : list) {
                        Vector<String> v = new Vector();
                        v.add(bookingDTO.getBookingID() + "");
                        v.add(bookingDTO.getUserID());
                        v.add(new SimpleDateFormat("MM-dd-yyyy").format(bookingDTO.getBookingDate()));
                        v.add(bookingDTO.getSubTotal() + "");
                        v.add(bookingDTO.getDiscountCode());
                        v.add(bookingDTO.getTotal() + "");
                        columnData.add(v);
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            Collections.sort(columnData, new Comparator<Vector<String>>() {
                @Override
                public int compare(Vector<String> o1, Vector<String> o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                    try {
                        return sdf.parse(o1.get(2)).compareTo(sdf.parse(o2.get(2)));
                    } catch (ParseException ex) {
                    }
                    return 0;
                }
            });
            return columnData;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbWelcome = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        pnSpFunction = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtBookingDate = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnShowAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 0), 2));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HOTEL BOOKING");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(25, 25, 300, 50);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("HISTORY");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(450, 35, 100, 30);

        lbWelcome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbWelcome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbWelcome.setText("Welcome");
        jPanel1.add(lbWelcome);
        lbWelcome.setBounds(580, 10, 300, 25);

        btnBack.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack);
        btnBack.setBounds(900, 10, 80, 35);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1000, 100);

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(50, 150, 900, 400);

        pnSpFunction.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnSpFunction.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Name:");
        pnSpFunction.add(jLabel3);
        jLabel3.setBounds(20, 15, 100, 30);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Booking Date:");
        pnSpFunction.add(jLabel4);
        jLabel4.setBounds(20, 45, 100, 30);

        txtName.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        pnSpFunction.add(txtName);
        txtName.setBounds(145, 15, 150, 30);

        txtBookingDate.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        pnSpFunction.add(txtBookingDate);
        txtBookingDate.setBounds(145, 45, 150, 30);

        btnSearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        pnSpFunction.add(btnSearch);
        btnSearch.setBounds(320, 25, 100, 40);

        btnDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnSpFunction.add(btnDelete);
        btnDelete.setBounds(440, 25, 100, 40);

        getContentPane().add(pnSpFunction);
        pnSpFunction.setBounds(50, 630, 560, 90);

        btnShowAll.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnShowAll.setText("Show all history");
        btnShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowAllActionPerformed(evt);
            }
        });
        getContentPane().add(btnShowAll);
        btnShowAll.setBounds(50, 570, 150, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void tblListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseReleased
        try {
            tblList.getCellEditor().cancelCellEditing();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblListMouseReleased

    private void btnShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllActionPerformed
        if (btnShowAll.getText().equals("Show all history")) {
            btnShowAll.setText("Show my history");
            pnSpFunction.setVisible(true);
            loadTable(getColumnHeaderOfSearchResult(), getColumnDataOfSearchResult());
        } else {
            pnSpFunction.setVisible(false);
            btnShowAll.setText("Show all history");
            loadTable(getColumnHeaderOfCurUser(), getColumnDataOfCurUser());
        }
    }//GEN-LAST:event_btnShowAllActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadTable(getColumnHeaderOfSearchResult(), getColumnDataOfSearchResult());
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tblList.getSelectedRow();
        if (row >= 0) {
            if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure to remove this booking?")) {
                try {
                    int bookingID = Integer.parseInt((String) tblList.getValueAt(row, 0));
                    boolean result = new BookingDAO().removeBooking(bookingID);
                    result = new BookingDetailDAO().removeBookingDetail(bookingID);
                    if (result) {
                        JOptionPane.showMessageDialog(this, "Delete successful!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Delete unsuccessful!");
                    }
                    loadTable(getColumnHeaderOfSearchResult(), getColumnDataOfSearchResult());
                } catch (Exception e) {
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must select a booking!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new History().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnShowAll;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbWelcome;
    private javax.swing.JPanel pnSpFunction;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtBookingDate;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
