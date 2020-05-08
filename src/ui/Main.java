/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ts.daos.BookingDetailDAO;
import ts.daos.HotelAreaDAO;
import ts.daos.HotelDAO;
import ts.daos.RoomDAO;
import ts.dtos.BookingDetailDTO;
import ts.dtos.HotelAreaDTO;
import ts.dtos.HotelDTO;
import ts.dtos.RoomDTO;
import ts.dtos.UserDTO;
import ts.utils.Utils;

/**
 *
 * @author nhocc
 */
public class Main extends javax.swing.JFrame {

    UserDTO user;
    Vector<String> searchDetail;
    Vector<BookingDetailDTO> bookingCart;

    /**
     * Creates new form Main
     */
    //Constructor at first time using
    public Main() {
        initComponents();
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);

        //Set default date in is present and date out is 1 day after date in
        Calendar cal = Calendar.getInstance();
        txtDateInSearch.setText(new SimpleDateFormat("MM-dd-yyyy").format(cal.getTime()));
        cal.add(Calendar.DAY_OF_MONTH, 1);
        txtDateOutSearch.setText(new SimpleDateFormat("MM-dd-yyyy").format(cal.getTime()));

        loadCbHotelAreaSearch();
        this.searchDetail = getSearchDetail();
        loadTable(getColumnDataOfHotelSearchResult(), getColumnHeaderOfHotelSearchResult());

        this.user = null;
        loadHeader();

        btnSelectHotel.setVisible(true);
        btnBack.setVisible(false);
        btnAddToCart.setVisible(false);
    }

    public Main(UserDTO user, Vector<String> searchDetail) {
        initComponents();
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);

        loadCbHotelAreaSearch();
        setSearchDetail(searchDetail);
        loadTable(getColumnDataOfHotelSearchResult(), getColumnHeaderOfHotelSearchResult());

        this.user = user;
        loadHeader();

        btnSelectHotel.setVisible(true);
        btnBack.setVisible(false);
        btnAddToCart.setVisible(false);
    }

    public Main(UserDTO user, Vector<String> searchDetail, Vector<BookingDetailDTO> bookingCart) {
        initComponents();
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);

        loadCbHotelAreaSearch();
        setSearchDetail(searchDetail);
        loadTable(getColumnDataOfHotelSearchResult(), getColumnHeaderOfHotelSearchResult());

        this.user = user;
        if (new BookingDetailDAO().reloadBookingCart(this.bookingCart)) {
            JOptionPane.showMessageDialog(this, "Some rooms have been remove because they are not available now for your selection!\n"
                    + "Please check your cart and make other bookings!");
        }
        this.bookingCart = bookingCart;

        loadHeader();

        btnSelectHotel.setVisible(true);
        btnBack.setVisible(false);
        btnAddToCart.setVisible(false);
    }

    //Check input for date in and date out
    public boolean isValidInput() {
        boolean check = true;
        String msg = "";
        String dateIn = txtDateInSearch.getText().trim();
        String dateOut = txtDateOutSearch.getText().trim();
        if (!Utils.isValidDate(dateIn) || !Utils.isValidDate(dateOut)) {
            msg += "Wrong format or invalid date!\n";
            check = false;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date checkIn = sdf.parse(dateIn);
            if (checkIn.compareTo(sdf.parse(sdf.format(new Date()))) < 0) {
                msg += "Date in must be from now\n";
                check = false;
            }
            Date checkOut = sdf.parse(dateOut);
            if (checkIn.compareTo(checkOut) >= 0) {
                msg += "Date out must be later than date in\n";
                check = false;
            }
        } catch (Exception ex) {
        }
        if (!msg.equals("")) {
            JOptionPane.showMessageDialog(this, msg);
        }
        return check;
    }

    //Get search detail
    public Vector<String> getSearchDetail() {
        if (!isValidInput()) {
            return null;
        }
        Vector<String> list = new Vector<>();

        //List[0]: Hotel name
        list.add(txtHotelNameSearch.getText().trim());

        String area = (String) cbHotelAreaSearch.getSelectedItem();
        //List[1]: Hotel area
        list.add(area);

        //List[2]: date in
        list.add(txtDateInSearch.getText().trim());

        //List[3]: date out
        list.add(txtDateOutSearch.getText().trim());

        //List[4]: quantity
        list.add(spQuantitySearch.getValue() + "");
        return list;
    }

    //Set search detail
    public void setSearchDetail(Vector<String> searchDetail) {
        this.searchDetail = searchDetail;
        txtHotelNameSearch.setText(searchDetail.get(0));
        cbHotelAreaSearch.setSelectedItem(searchDetail.get(1));
        txtDateInSearch.setText(searchDetail.get(2));
        txtDateOutSearch.setText(searchDetail.get(3));
        spQuantitySearch.setValue(Integer.parseInt(searchDetail.get(4)));
    }

    //Get data search result for table tblList
    public Vector<Vector> getColumnDataOfHotelSearchResult() {
        if (searchDetail == null) {
            return null;
        }
        String hotelName = searchDetail.get(0);
        String hotelArea = searchDetail.get(1);
        Vector<HotelDTO> list = null;
        HotelDAO hd = new HotelDAO();
        try {
            if (hotelArea.equals("")) {
                list = hd.getHotelByName(hotelName);
            } else {
                int areaID = new HotelAreaDAO().getIDByName(hotelArea);
                list = hd.getHotelByNameAndArea(hotelName, areaID);
            }
            if (list == null) {
                return null;
            } else {
                Vector<Vector> columnData = new Vector<>();
                for (HotelDTO dto : list) {
                    Timestamp dateIn = new Timestamp(new SimpleDateFormat("MM-dd-yyyy").parse(searchDetail.get(2)).getTime());
                    Timestamp dateOut = new Timestamp(new SimpleDateFormat("MM-dd-yyyy").parse(searchDetail.get(3)).getTime());
                    int numOfAvailable = hd.getNumOfAvailableRommOfHotel(dto.getHotelID(), dateIn, dateOut, Integer.parseInt(searchDetail.get(4)));
                    if (numOfAvailable >= Integer.parseInt(searchDetail.get(4))) {
                        Vector v = new Vector();
                        v.add(dto.getHotelName());
                        v.add(new HotelAreaDAO().getNameByID(dto.getHotelAreaID()));
                        v.add(dto.getAddress());
                        v.add(dto.getDescription());
                        v.add(dto.getPhone());
                        v.add(numOfAvailable);
                        columnData.add(v);
                    }
                }
                return columnData;
            }
        } catch (Exception e) {
        }
        return null;
    }

    //Get data for tblList that show rooms of hotel is selected
    public Vector<Vector> getColumnDataOfHotelSelected() {
        Vector<Vector> columnData = null;
        try {
            int hotelID = new HotelDAO().getIDByName((String) tblList.getValueAt(tblList.getSelectedRow(), 0));
            RoomDAO rd = new RoomDAO();
            Vector<RoomDTO> listRoom = rd.getRoomByHotelID(hotelID);

            for (RoomDTO roomDTO : listRoom) {
                Vector v = new Vector();
                v.add(roomDTO.getRoomName());
                v.add(roomDTO.getType());
                Timestamp dateIn = new Timestamp(new SimpleDateFormat("MM-dd-yyyy").parse(searchDetail.get(2)).getTime());
                Timestamp dateOut = new Timestamp(new SimpleDateFormat("MM-dd-yyyy").parse(searchDetail.get(3)).getTime());
                if (rd.isAvailableRoom(dateIn, dateOut, roomDTO.getRoomID(), Integer.parseInt(searchDetail.get(4)))) {
                    v.add(rd.getNumOfAvailableOfRoom(dateIn, dateOut, roomDTO.getRoomID()));
                    v.add(roomDTO.getPrice());
                    v.add(roomDTO.getDescription());
                    if (columnData == null) {
                        columnData = new Vector<>();
                    }
                    columnData.add(v);
                }

            }
        } catch (Exception e) {
        }
        return columnData;
    }

    //Load table tblList
    public void loadTable(Vector<Vector> columnData, Vector columnHeader) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(columnData, columnHeader);
        tblList.setModel(dtm);
        tblList.updateUI();
    }

    //Get header for tblList that show search result
    public Vector getColumnHeaderOfHotelSearchResult() {
        Vector columnHeader = new Vector();
        columnHeader.add("Name");
        columnHeader.add("Area");
        columnHeader.add("Address");
        columnHeader.add("Description");
        columnHeader.add("Phone");
        columnHeader.add("Available Room");
        return columnHeader;
    }

    //Get header for tblList that show rooms of hotel is selected
    public Vector getColumnHeaderOfHotelSelected() {
        Vector columnHeader = new Vector();
        columnHeader.add("Room Name");
        columnHeader.add("Type");
        columnHeader.add("Available");
        columnHeader.add("Price");
        columnHeader.add("Description");
        return columnHeader;
    }

    //Load data for cbHotelAreaSearch
    public void loadCbHotelAreaSearch() {
        try {
            Vector<HotelAreaDTO> list = new HotelAreaDAO().getAllHotelArea();
            cbHotelAreaSearch.removeAllItems();
            cbHotelAreaSearch.addItem("");
            for (HotelAreaDTO hotelAreaDTO : list) {
                cbHotelAreaSearch.addItem(hotelAreaDTO.getHotelAreaName());
            }
            cbHotelAreaSearch.setSelectedIndex(0);
        } catch (Exception e) {
        }
    }

    //Load header
    public void loadHeader() {
        if (this.user == null) {
            lbWelcome.setText("Not login!");
            btnLoginLogout.setText("Login");
        } else {
            lbWelcome.setText("Welcome " + user.getUserName());
            btnLoginLogout.setText("Logout");
            if (user.getRole().equals("ad")) {
                btnAddToCart.setEnabled(false);
                lbBookingCart.setText("History");
                lbNumOfCart.setVisible(false);
            }
        }

        loadNumOfCart();
    }

    public void loadNumOfCart() {
        if (bookingCart == null) {
            lbNumOfCart.setText("0");
        } else {
            lbNumOfCart.setText(bookingCart.size() + "");
        }
    }

    public boolean checkExistedRoomInCart(BookingDetailDTO dto) {
        for (BookingDetailDTO bookingDetailDTO : bookingCart) {
            if (bookingDetailDTO.equals(dto)) {
                return true;
            }
        }
        return false;
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
        btnLoginLogout = new javax.swing.JButton();
        pnBookingCart = new javax.swing.JPanel();
        lbBookingCart = new javax.swing.JLabel();
        lbNumOfCart = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtHotelNameSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbHotelAreaSearch = new javax.swing.JComboBox<>();
        txtDateInSearch = new javax.swing.JTextField();
        txtDateOutSearch = new javax.swing.JTextField();
        spQuantitySearch = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        btnSelectHotel = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnAddToCart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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
        lbWelcome.setBounds(400, 20, 300, 25);

        btnLoginLogout.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLoginLogout.setText("Login");
        btnLoginLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLoginLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginLogoutActionPerformed(evt);
            }
        });
        jPanel1.add(btnLoginLogout);
        btnLoginLogout.setBounds(620, 50, 80, 30);

        pnBookingCart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnBookingCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnBookingCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnBookingCartMouseClicked(evt);
            }
        });
        pnBookingCart.setLayout(null);

        lbBookingCart.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbBookingCart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBookingCart.setText("Booking Cart");
        pnBookingCart.add(lbBookingCart);
        lbBookingCart.setBounds(5, 5, 150, 30);

        lbNumOfCart.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        lbNumOfCart.setForeground(new java.awt.Color(51, 153, 0));
        lbNumOfCart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNumOfCart.setText("0");
        lbNumOfCart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnBookingCart.add(lbNumOfCart);
        lbNumOfCart.setBounds(160, 0, 40, 40);

        jPanel1.add(pnBookingCart);
        pnBookingCart.setBounds(750, 20, 200, 40);

        tblList.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Area", "Address", "Description", "Phone", "Available Room"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(null);

        txtHotelNameSearch.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jPanel2.add(txtHotelNameSearch);
        txtHotelNameSearch.setBounds(20, 45, 150, 30);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Hotel Name");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(45, 15, 100, 30);

        cbHotelAreaSearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jPanel2.add(cbHotelAreaSearch);
        cbHotelAreaSearch.setBounds(185, 45, 150, 30);

        txtDateInSearch.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtDateInSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtDateInSearch);
        txtDateInSearch.setBounds(350, 45, 100, 30);

        txtDateOutSearch.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtDateOutSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtDateOutSearch);
        txtDateOutSearch.setBounds(465, 45, 100, 30);

        spQuantitySearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        spQuantitySearch.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spQuantitySearch.setEditor(new javax.swing.JSpinner.NumberEditor(spQuantitySearch, ""));
        jPanel2.add(spQuantitySearch);
        spQuantitySearch.setBounds(605, 45, 50, 30);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hotel Area");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(210, 15, 100, 30);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Date In");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(350, 15, 100, 30);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Date Out");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(465, 15, 100, 30);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Quantity");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(580, 15, 100, 30);

        btnSearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel2.add(btnSearch);
        btnSearch.setBounds(700, 40, 100, 30);

        btnSelectHotel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSelectHotel.setText("Select Hotel");
        btnSelectHotel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelectHotel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectHotelActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnAddToCart.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddToCart.setText("Add To Cart");
        btnAddToCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(425, 425, 425)
                .addComponent(btnSelectHotel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnSelectHotel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectHotelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectHotelActionPerformed
        int row = tblList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "You must select a hotel!");
            return;
        }
        btnSelectHotel.setVisible(false);
        btnBack.setVisible(true);
        btnAddToCart.setVisible(true);

        try {
            loadTable(getColumnDataOfHotelSelected(), getColumnHeaderOfHotelSelected());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnSelectHotelActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchDetail = getSearchDetail();
        loadTable(getColumnDataOfHotelSearchResult(), getColumnHeaderOfHotelSearchResult());

        btnSelectHotel.setVisible(true);
        btnBack.setVisible(false);
        btnAddToCart.setVisible(false);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        searchDetail = getSearchDetail();
        loadTable(getColumnDataOfHotelSearchResult(), getColumnHeaderOfHotelSearchResult());

        btnSelectHotel.setVisible(true);
        btnBack.setVisible(false);
        btnAddToCart.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked

    }//GEN-LAST:event_tblListMouseClicked

    private void tblListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseReleased
        int row = tblList.getSelectedRow();
        int column = tblList.getSelectedColumn();
        tblList.getCellEditor(row, column).cancelCellEditing();
    }//GEN-LAST:event_tblListMouseReleased

    private void btnLoginLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginLogoutActionPerformed
        if (btnLoginLogout.getText().equals("Login")) {
            Login login = new Login(searchDetail);
            login.setVisible(true);
            this.dispose();
        } else {
            Main main = new Main(null, searchDetail);
            main.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLoginLogoutActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
        if (user == null) {
            JOptionPane.showMessageDialog(this, "You must login first!");
            return;
        }
        int row = tblList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "You must select a room!");
            return;
        }
        RoomDAO rd = new RoomDAO();
        try {
            int roomID = rd.getIDByName((String) tblList.getValueAt(row, 0));
            Timestamp dateIn = new Timestamp(new SimpleDateFormat("MM-dd-yyyy").parse(searchDetail.get(2)).getTime());
            Timestamp dateOut = new Timestamp(new SimpleDateFormat("MM-dd-yyyy").parse(searchDetail.get(3)).getTime());
            int quantity = Integer.parseInt(searchDetail.get(4));

            if (rd.isAvailableRoom(dateIn, dateOut, roomID, quantity)) {
                BookingDetailDTO dto = new BookingDetailDTO(roomID, quantity, dateIn, dateOut);
                if (bookingCart == null) {
                    bookingCart = new Vector<>();
                }
                if (!checkExistedRoomInCart(dto)) {
                    bookingCart.add(dto);
                    JOptionPane.showMessageDialog(this, "Add successful!");
                    loadNumOfCart();
                } else {
                    JOptionPane.showMessageDialog(this, "Please do not add a room twice!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sorry! This room is not available now.");
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void pnBookingCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnBookingCartMouseClicked
        if (user == null) {
            JOptionPane.showMessageDialog(this, "You must login first!");
            return;
        }
        if (user.getRole().equals("ad")) {
            History history = new History(user);
            history.setVisible(true);
        } else {
            BookingCart bc = new BookingCart(user, searchDetail, bookingCart);
            bc.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_pnBookingCartMouseClicked

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnLoginLogout;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSelectHotel;
    private javax.swing.JComboBox<String> cbHotelAreaSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBookingCart;
    private javax.swing.JLabel lbNumOfCart;
    private javax.swing.JLabel lbWelcome;
    private javax.swing.JPanel pnBookingCart;
    private javax.swing.JSpinner spQuantitySearch;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtDateInSearch;
    private javax.swing.JTextField txtDateOutSearch;
    private javax.swing.JTextField txtHotelNameSearch;
    // End of variables declaration//GEN-END:variables
}
