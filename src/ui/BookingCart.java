/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ts.daos.BookingDAO;
import ts.daos.BookingDetailDAO;
import ts.daos.DiscountCodeDAO;
import ts.daos.HotelDAO;
import ts.daos.RoomDAO;
import ts.dtos.BookingDTO;
import ts.dtos.BookingDetailDTO;
import ts.dtos.RoomDTO;
import ts.dtos.UserDTO;
import ts.utils.Utils;

/**
 *
 * @author nhocc
 */
public class BookingCart extends javax.swing.JFrame {

    private UserDTO user;
    private Vector<String> searchDetail;
    private Vector<BookingDetailDTO> bookingCart;
    private BookingDTO booking;
    boolean isChange;

    /**
     * Creates new form BookingCart
     */
    public BookingCart(UserDTO user, Vector<String> searchDetail, Vector<BookingDetailDTO> bookingCart) {
        initComponents();
        this.setSize(1005, 800);
        this.setLocationRelativeTo(null);
        this.user = user;
        this.searchDetail = searchDetail;
        this.bookingCart = bookingCart;

        loadUser();

        this.booking = new BookingDTO();
        this.booking.setUserID(this.user.getUserID());
        loadData();
        isChange = false;
    }

    public void loadUser() {
        if (user == null) {
            lbWelcome.setText("Not login!");
        } else {
            lbWelcome.setText("Welcome " + user.getUserName());
        }
    }

    public void loadTable() {
        Vector columnHeader = new Vector();
        columnHeader.add("Hotel Name");
        columnHeader.add("Room Name");
        columnHeader.add("Room Type");
        columnHeader.add("Price");
        columnHeader.add("Amount");
        columnHeader.add("Date In");
        columnHeader.add("Date Out");
        columnHeader.add("Total");

        Vector<Vector> columnData = new Vector<>();
        try {
            for (BookingDetailDTO dto : bookingCart) {
                Vector v = new Vector();
                RoomDAO rd = new RoomDAO();
                RoomDTO roomDTO = rd.getRoomByID(dto.getRoomID());
                String hotelName = new HotelDAO().getNameByID(roomDTO.getHotelID());
                v.add(hotelName);
                v.add(roomDTO.getRoomName());
                v.add(roomDTO.getType());
                v.add(roomDTO.getPrice());
                v.add(dto.getQuantity());
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                v.add(sdf.format(dto.getDateIn()));
                v.add(sdf.format(dto.getDateOut()));
                float total = roomDTO.getPrice() * dto.getQuantity() * TimeUnit.MILLISECONDS.toDays(dto.getDateOut().getTime() - dto.getDateIn().getTime());
                v.add(total + "");
                columnData.add(v);
            }
        } catch (Exception e) {
        }

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(columnData, columnHeader);
        tblList.setModel(dtm);
        tblList.updateUI();
    }

    public void loadData() {
        if (new BookingDetailDAO().reloadBookingCart(this.bookingCart)) {
            JOptionPane.showMessageDialog(this, "Some rooms have been remove because they are not available now for your selection!\n"
                    + "Please check your cart and make other bookings!");
        }
        if (bookingCart == null || bookingCart.isEmpty()) {
            btnCheckDiscount.setEnabled(false);
        } else {
            btnCheckDiscount.setVisible(true);
        }
        loadTable();
        float subTotal = getSubTotal();
        this.booking.setSubTotal(subTotal);
        lbSubTotal.setText(subTotal + "");
        String discountCode = this.booking.getDiscountCode();
        float total = subTotal;
        try {
            DiscountCodeDAO dcd = new DiscountCodeDAO();
            if (dcd.checkDiscount(discountCode)) {
                txtDiscountCode.setText(discountCode);
                float percent = dcd.getPercent(discountCode);
                lblDiscount.setText("- " + percent + "%");
                lblDiscount.setVisible(true);
                total -= subTotal * percent / 100;
                lbTotal.setText(total + "");
            } else {
                txtDiscountCode.setText("");
                lblDiscount.setVisible(false);
                lbTotal.setText(total + "");
                if (discountCode != null && !discountCode.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Discount code is not available now!");
                }
            }
            booking.setTotal(total);
        } catch (Exception e) {
        }

    }

    public float getSubTotal() {
        float subTotal = 0;
        if (tblList.getRowCount() == 0) {
            return 0;
        }
        for (int i = 0; i < tblList.getRowCount(); i++) {
            subTotal += Float.parseFloat((String) tblList.getValueAt(i, 7));
        }
        return subTotal;
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
        jLabel6 = new javax.swing.JLabel();
        lbWelcome = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        btnHistory = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDiscountCode = new javax.swing.JTextField();
        btnBooking = new javax.swing.JButton();
        btnCheckDiscount = new javax.swing.JButton();
        lblDiscount = new javax.swing.JLabel();
        lbSubTotal = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0), 2));
        jPanel1.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("HOTEL BOOKING");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(25, 25, 300, 50);

        lbWelcome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbWelcome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbWelcome.setText("Welcome");
        jPanel1.add(lbWelcome);
        lbWelcome.setBounds(550, 10, 300, 25);

        btnLogout.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogout);
        btnLogout.setBounds(880, 10, 80, 35);

        btnBack.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack);
        btnBack.setBounds(880, 50, 80, 35);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Booking Cart");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(425, 35, 150, 30);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1000, 100);

        tblList.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Hotel Name", "Room Name", "Room Type", "Price", "Amount", "Date In", "Date Out", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblListMouseReleased(evt);
            }
        });
        tblList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblListKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(50, 170, 900, 400);

        btnHistory.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnHistory.setText("History");
        btnHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoryActionPerformed(evt);
            }
        });
        getContentPane().add(btnHistory);
        btnHistory.setBounds(850, 115, 100, 40);

        btnRemove.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        getContentPane().add(btnRemove);
        btnRemove.setBounds(850, 585, 100, 40);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Sub Total:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(0, 15, 125, 30);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Discount Code:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 45, 125, 30);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Total:");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(0, 75, 125, 30);
        jPanel2.add(txtDiscountCode);
        txtDiscountCode.setBounds(140, 45, 150, 30);

        btnBooking.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBooking.setText("Booking");
        btnBooking.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingActionPerformed(evt);
            }
        });
        jPanel2.add(btnBooking);
        btnBooking.setBounds(140, 120, 100, 40);

        btnCheckDiscount.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCheckDiscount.setText("Check Discount Code");
        btnCheckDiscount.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCheckDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckDiscountActionPerformed(evt);
            }
        });
        jPanel2.add(btnCheckDiscount);
        btnCheckDiscount.setBounds(305, 40, 200, 40);

        lblDiscount.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        lblDiscount.setForeground(new java.awt.Color(0, 153, 0));
        lblDiscount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDiscount.setText("-20%");
        lblDiscount.setToolTipText("");
        jPanel2.add(lblDiscount);
        lblDiscount.setBounds(310, 75, 50, 30);

        lbSubTotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbSubTotal.setForeground(new java.awt.Color(0, 153, 0));
        lbSubTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(lbSubTotal);
        lbSubTotal.setBounds(140, 15, 150, 30);

        lbTotal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(0, 153, 0));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTotal.setText("100");
        jPanel2.add(lbTotal);
        lbTotal.setBounds(140, 75, 150, 30);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(50, 590, 520, 175);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Main main = new Main(user, searchDetail, bookingCart);
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int row = tblList.getSelectedRow();
        if (row >= 0) {
            if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure to remove this room?")) {
                bookingCart.remove(row);
                JOptionPane.showMessageDialog(this, "Remove successful!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Remove unsuccessful!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must select a room!");
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void tblListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseReleased
        try {
            if (tblList.getSelectedColumn() != 4) {
                tblList.getCellEditor().cancelCellEditing();
            } else {
                isChange = true;
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblListMouseReleased

    private void tblListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblListKeyReleased
        if (isChange) {
            isChange = false;
            int row = tblList.getSelectedRow();
            int column = 4;
            String quantityStr = (String) tblList.getValueAt(row, column);
            if (!Utils.isPositiveIntegerNumber(quantityStr)) {
                JOptionPane.showMessageDialog(this, "You must input a positive integer number!");
                loadData();
                return;
            }
            int quantity = Integer.parseInt(quantityStr);
            BookingDetailDTO dto = bookingCart.get(row);
            try {
                if (new RoomDAO().isAvailableRoom(dto.getDateIn(), dto.getDateOut(), dto.getRoomID(), quantity)) {
                    dto.setQuantity(quantity);
                    bookingCart.set(row, dto);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Sorry! We do not have enough room for this quantity!");
                    loadData();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error at BookingCart - tblListKeyReleased: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_tblListKeyReleased

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        Main main = new Main(null, searchDetail);
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoryActionPerformed
        History history = new History(user);
        history.setVisible(true);
    }//GEN-LAST:event_btnHistoryActionPerformed

    private void btnBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingActionPerformed
        loadData();
        if (bookingCart == null || bookingCart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
        } else {
            if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure to finish booking?")) {
                try {
                    BookingDAO bd = new BookingDAO();
                    int bookingID = bd.getNextBookingID();
                    booking.setBookingID(bookingID);
                    boolean result = bd.insertBooking(booking);
                    BookingDetailDAO bdd = new BookingDetailDAO();
                    for (BookingDetailDTO bookingDetailDTO : bookingCart) {
                        bookingDetailDTO.setBookingID(bookingID);
                        result = bdd.insertBookingDetail(bookingDetailDTO);
                    }
                    if (result) {
                        JOptionPane.showMessageDialog(this, "Booking Successful!");
                        bookingCart = null;
                        booking.setDiscountCode("");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Booking fail!");
                    }
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_btnBookingActionPerformed

    private void btnCheckDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckDiscountActionPerformed
        String discountCode = txtDiscountCode.getText().trim();
        try {
            if (new DiscountCodeDAO().checkDiscount(discountCode)) {
                JOptionPane.showMessageDialog(this, "Available!");
                this.booking.setDiscountCode(discountCode);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Not available!");
                this.booking.setDiscountCode("");
                loadData();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCheckDiscountActionPerformed

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
            java.util.logging.Logger.getLogger(BookingCart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookingCart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookingCart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookingCart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new BookingCart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBooking;
    private javax.swing.JButton btnCheckDiscount;
    private javax.swing.JButton btnHistory;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbSubTotal;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbWelcome;
    private javax.swing.JLabel lblDiscount;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtDiscountCode;
    // End of variables declaration//GEN-END:variables
}
