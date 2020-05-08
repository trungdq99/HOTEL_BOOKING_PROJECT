/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
import ts.daos.UserDAO;
import ts.dtos.UserDTO;
import ts.utils.Utils;

/**
 *
 * @author SE130447
 */
public class SignUp extends javax.swing.JFrame {

    /**
     * Creates new form SignUp
     */
    public SignUp() {
        initComponents();
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(btnSignUp);
        resetUI();
    }

    public void resetUI() {
        txtUserID.setText("");
        txtUserID.requestFocus();
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtUserName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");

        resetErrorLabel();
    }

    public void resetErrorLabel() {
        lbUserIDError.setVisible(false);
        lbPasswordError.setVisible(false);
        lbConfirmError.setVisible(false);
        lbFullNameError.setVisible(false);
        lbPhoneError.setVisible(false);
        lbAddressError.setVisible(false);
    }

    public boolean isValidInput() {
        resetErrorLabel();
        boolean check = true;
        if (!Utils.isValidEmail(txtUserID.getText().trim())) {
            lbUserIDError.setVisible(true);
            check = false;
        }
        if (!Utils.isValidPassword(txtPassword.getText())) {
            lbPasswordError.setVisible(true);
            check = false;
        } else if (!txtPassword.getText().trim().equals(txtConfirmPassword.getText().trim())) {
            lbConfirmError.setVisible(true);
            check = false;
        }
        if (txtUserName.getText().trim().length() < 2 || txtUserName.getText().trim().length() > 50) {
            lbFullNameError.setVisible(true);
            check = false;
        }
        if (!Utils.isValidPhoneNumber(txtPhone.getText().trim())) {
            lbPhoneError.setVisible(true);
            check = false;
        }
        if (txtAddress.getText().trim().isEmpty()) {
            lbAddressError.setVisible(true);
            check = false;
        }
        return check;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtUserID = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        txtConfirmPassword = new javax.swing.JPasswordField();
        btnSignUp = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lbUserIDError = new javax.swing.JLabel();
        lbPasswordError = new javax.swing.JLabel();
        lbConfirmError = new javax.swing.JLabel();
        lbFullNameError = new javax.swing.JLabel();
        lbPhoneError = new javax.swing.JLabel();
        lbAddressError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIGN UP");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(175, 40, 150, 50);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("User ID:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(50, 120, 150, 30);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Password:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(50, 170, 150, 30);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Confirm Password:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(50, 220, 150, 30);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Full name:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(50, 270, 150, 30);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Phone:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(50, 320, 150, 30);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Address:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(50, 370, 150, 30);

        txtUserID.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtUserID);
        txtUserID.setBounds(220, 120, 200, 30);

        txtUserName.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtUserName);
        txtUserName.setBounds(220, 270, 200, 30);

        txtPhone.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtPhone);
        txtPhone.setBounds(220, 320, 200, 30);

        txtAddress.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtAddress);
        txtAddress.setBounds(220, 370, 200, 30);

        txtPassword.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtPassword);
        txtPassword.setBounds(220, 170, 200, 30);

        txtConfirmPassword.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        getContentPane().add(txtConfirmPassword);
        txtConfirmPassword.setBounds(220, 220, 200, 30);

        btnSignUp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });
        getContentPane().add(btnSignUp);
        btnSignUp.setBounds(125, 440, 100, 40);

        btnReset.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnReset.setText("Reset");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        getContentPane().add(btnReset);
        btnReset.setBounds(275, 440, 100, 40);

        jLabel8.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("(Use Email)");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(50, 150, 150, 16);

        lbUserIDError.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lbUserIDError.setForeground(java.awt.Color.red);
        lbUserIDError.setText("User ID must follow format: example@example2");
        getContentPane().add(lbUserIDError);
        lbUserIDError.setBounds(220, 150, 265, 16);

        lbPasswordError.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lbPasswordError.setForeground(java.awt.Color.red);
        lbPasswordError.setText("Password must be from 6 - 20 characters");
        getContentPane().add(lbPasswordError);
        lbPasswordError.setBounds(220, 200, 223, 16);

        lbConfirmError.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lbConfirmError.setForeground(java.awt.Color.red);
        lbConfirmError.setText("Confirm must match password");
        getContentPane().add(lbConfirmError);
        lbConfirmError.setBounds(220, 250, 166, 16);

        lbFullNameError.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lbFullNameError.setForeground(java.awt.Color.red);
        lbFullNameError.setText("Full name must be from 2 to 50 characters");
        getContentPane().add(lbFullNameError);
        lbFullNameError.setBounds(220, 300, 231, 16);

        lbPhoneError.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lbPhoneError.setForeground(java.awt.Color.red);
        lbPhoneError.setText("Phone must be 10 digits and start with 0");
        getContentPane().add(lbPhoneError);
        lbPhoneError.setBounds(220, 350, 217, 16);

        lbAddressError.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        lbAddressError.setForeground(java.awt.Color.red);
        lbAddressError.setText("Address can not be empty");
        getContentPane().add(lbAddressError);
        lbAddressError.setBounds(220, 400, 143, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetUI();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        if (!isValidInput()) {

            return;
        }
        String userID = txtUserID.getText().trim();
        String password = Utils.encryptPassword(txtPassword.getText());
        String userName = txtUserName.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        Timestamp createDate = new Timestamp(new Date().getTime());

        UserDTO dto = new UserDTO(userID, userName, password, phone, address, createDate, "cu", true);
        UserDAO dao = new UserDAO();
        try {
            boolean result = dao.createUser(dto);
            if (result) {
                JOptionPane.showMessageDialog(this, "Sign up successfully!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sign up unsuccessfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error at SignUp - btnSignUpActionPerformed: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSignUpActionPerformed

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
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lbAddressError;
    private javax.swing.JLabel lbConfirmError;
    private javax.swing.JLabel lbFullNameError;
    private javax.swing.JLabel lbPasswordError;
    private javax.swing.JLabel lbPhoneError;
    private javax.swing.JLabel lbUserIDError;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUserID;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}