package org.librarysystem.view;

import org.librarysystem.helper.Config;
import org.librarysystem.helper.Helper;
import org.librarysystem.model.Book;
import org.librarysystem.model.Employee;
import org.librarysystem.model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EmployeeGui extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabEmployee;
    private JLabel lblWelcome;
    private JPanel pnl_top;
    private JButton btnLogOut;
    private JPanel pnlUsers;
    private JScrollPane scrlUserList;
    private JTable tblUserList;
    private JLabel lblName;
    private JTextField txtFldName;
    private JLabel lbluName;
    private JTextField txtFldUName;
    private JLabel lblPassword;
    private JPasswordField passfldUser;
    private JLabel fldType;
    private JComboBox cmbUserType;
    private JButton btnUserAdd;
    private JPanel wrpBottom;
    private JLabel lblUserId;
    private JTextField txtFldUserId;
    private JButton btnDeleteUser;
    private JTextField txtFldSrcUser;
    private JLabel lblSearchText;
    private JLabel lblUnameText;
    private JTextField txtFldSrcUname;
    private JLabel lblTypeTxt;
    private JComboBox cmbSrcType;
    private JButton btnUserSrc;
    private JPanel pnlBooks;
    private JScrollPane scrPaneBook;
    private JTable tblBookList;
    private JPanel wrapperLeft;
    private JLabel lblAuthor;
    private JTextField txtFldAuthor;
    private JLabel lblTitle;
    private JTextField txtFldTitle;
    private JLabel lblPubYear;
    private JTextField txtFldPubYear;
    private JPanel wrapperSrcUser;
    private JButton btnAddBook;
    private JLabel lblSrcTitle;
    private JTextField txtFldSrcTitle;
    private JTextField txtFldSrcAuthor;
    private JLabel lblSrcAuthor;
    private JLabel lblSrcStatus;
    private JButton btnSrcBook;
    private JPanel wrapperSrcBook;
    private JLabel lblDeleteBook;
    private JTextField txtFldBookId;
    private JButton btnDeleteBook;
    private JComboBox cmbSrcStatus;
    private DefaultTableModel mdlUserList;
    private Object[] rowUserList;
    private DefaultTableModel mdlBookList;
    private Object[] rowBookList;

    private final Employee employee;

    // bu arayüze sadece bir çalışan erişebilir
    public EmployeeGui(Employee employee) {
        this.employee = employee;
        add(wrapper);
        setSize(1200,800);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        int x = Helper.screenCenterLocation("x",getSize());
        int y = Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setVisible(true);

        lblWelcome.setText("Welcome " + employee.getName());

        //ModelUserList
        mdlUserList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] colUserList = {"id","Name Surname","User Name","Password","Membership"};
        mdlUserList.setColumnIdentifiers(colUserList);
        rowUserList = new Object[colUserList.length];

        loadUserMode();

        tblUserList.setModel(mdlUserList);
        tblUserList.getTableHeader().setReorderingAllowed(false);
        tblUserList.getColumnModel().getColumn(0).setMaxWidth(75);

        tblUserList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selectedUserID = tblUserList.getValueAt(tblUserList.getSelectedRow(),0).toString();
                System.out.println(selectedUserID);
                txtFldUserId.setText(selectedUserID);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        tblUserList.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE) {
                int userId = Integer.parseInt(tblUserList.getValueAt(tblUserList.getSelectedRow(),0).toString());
                String userName = tblUserList.getValueAt(tblUserList.getSelectedRow(), 1).toString();
                String userUname = tblUserList.getValueAt(tblUserList.getSelectedRow(), 2).toString();
                String userPassword = tblUserList.getValueAt(tblUserList.getSelectedRow(), 3).toString();
                String userRole = tblUserList.getValueAt(tblUserList.getSelectedRow(), 4).toString();
                if (User.update(userId,userName,userUname,userPassword,userRole)) {
                    Helper.showMessage("done");
                }
                loadUserMode();
            }
        });



        mdlBookList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                if (column == 1) return false;
                if (column == 2) return false;
                if (column == 3) return false;
                if (column == 4) return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] colBookList = {"ID","Author","Title","Publication Year","Status"};
        mdlBookList.setColumnIdentifiers(colBookList);
        rowBookList = new Object[colBookList.length];
        loadBookModel();

        tblBookList.setModel(mdlBookList);
        tblBookList.getTableHeader().setReorderingAllowed(false);
        tblBookList.getColumnModel().getColumn(0).setMaxWidth(75);
        tblBookList.getColumnModel().getColumn(3).setMaxWidth(100);
        tblBookList.getColumnModel().getColumn(4).setMaxWidth(100);

        tblBookList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selectedBookID = tblBookList.getValueAt(tblBookList.getSelectedRow(),0).toString();
                System.out.println(selectedBookID);
                txtFldBookId.setText(selectedBookID);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        btnUserAdd.addActionListener(e -> {
            if (Helper.isFieldEmpty(txtFldName) || Helper.isFieldEmpty(txtFldUName)|| Helper.isFieldEmpty(passfldUser)) {
                Helper.showMessage("fill");
            } else {
                String name = txtFldName.getText();
                String uname = txtFldUName.getText();
                String password = passfldUser.getText();
                String type = cmbUserType.getSelectedItem().toString();
                if (User.add(name,uname,password,type)){
                    loadUserMode();
                    Helper.showMessage("done");
                    txtFldName.setText(null);
                    txtFldUName.setText(null);
                    passfldUser.setText(null);
                }
            }
        });

        btnDeleteUser.addActionListener(e -> {
            if (Helper.isFieldEmpty(txtFldUserId)) {
                Helper.showMessage("fill");
            } else {
                int userId= Integer.parseInt(txtFldUserId.getText());
                if(User.delete(userId)) {
                    Helper.showMessage("done");
                    loadUserMode();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        btnUserSrc.addActionListener(e -> {
            String name = txtFldSrcUser.getText();
            String uname = txtFldSrcUname.getText();
            String type = cmbSrcType.getSelectedItem().toString();
            String query = User.searchQuery(name,uname,type);
            ArrayList<User> searchingUser = User.searchUserList(query);
            loadUserMode(searchingUser);
        });

        btnLogOut.addActionListener(e -> {
            dispose();
            LoginGui login = new LoginGui();
        });

        btnAddBook.addActionListener(e -> {
            if (Helper.isFieldEmpty(txtFldAuthor) || Helper.isFieldEmpty(txtFldTitle) || Helper.isFieldEmpty(txtFldPubYear)) {
                Helper.showMessage("fill");
            } else {
                String author = txtFldAuthor.getText();
                String title = txtFldTitle.getText();
                int publication_year = Integer.parseInt(txtFldPubYear.getText());
                String status = "available";
                if (Book.add(author,title,publication_year,status)) {
                    loadBookModel();
                    Helper.showMessage("done");
                    txtFldAuthor.setText(null);
                    txtFldTitle.setText(null);
                    txtFldPubYear.setText(null);
                }
            }
        });

        btnDeleteBook.addActionListener(e -> {
            if (Helper.isFieldEmpty(txtFldBookId)) {
                Helper.showMessage("fill");
            } else {
                int bookId = Integer.parseInt(txtFldBookId.getText());
                if (Book.delete(bookId)) {
                    Helper.showMessage("done");
                    loadBookModel();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        btnSrcBook.addActionListener(e -> {
            String author = txtFldSrcAuthor.getText();
            String title = txtFldSrcTitle.getText();
            String status = cmbSrcStatus.getSelectedItem().toString();
            String query = Book.searchQuery(title,author,status);
            ArrayList<Book> searchingBook = Book.searchBookList(query);
            loadBookModel(searchingBook);
        });
    }


    //tabloda ki değerleri yeniler
    private void loadBookModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tblBookList.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Book obj: Book.getList()) {
            i = 0;
            rowBookList[i++] = obj.getId();
            rowBookList[i++] = obj.getAuthor();
            rowBookList[i++] = obj.getTitle();
            rowBookList[i++] = obj.getPublication_year();
            rowBookList[i++] = obj.getStatus();
            mdlBookList.addRow(rowBookList);
        }
    }

    private void loadBookModel(ArrayList<Book> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tblBookList.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Book obj: list) {
            i = 0;
            rowBookList[i++] = obj.getId();
            rowBookList[i++] = obj.getAuthor();
            rowBookList[i++] = obj.getTitle();
            rowBookList[i++] = obj.getPublication_year();
            rowBookList[i++] = obj.getStatus();
            mdlBookList.addRow(rowBookList);
        }
    }


    // tablomuzda ki değerleri yeniler
    public void loadUserMode(){
        DefaultTableModel clearModel = (DefaultTableModel) tblUserList.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User obj : User.getList()) {
            i = 0;
            rowUserList[i++] = obj.getId();
            rowUserList[i++] = obj.getName();
            rowUserList[i++] = obj.getUname();
            rowUserList[i++] = obj.getPassword();
            rowUserList[i++] = obj.getType();
            mdlUserList.addRow(rowUserList);
        }
    }

    public void loadUserMode(ArrayList<User> list){
        DefaultTableModel clearModel = (DefaultTableModel) tblUserList.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User obj : list) {
            i = 0;
            rowUserList[i++] = obj.getId();
            rowUserList[i++] = obj.getName();
            rowUserList[i++] = obj.getUname();
            rowUserList[i++] = obj.getPassword();
            rowUserList[i++] = obj.getType();
            mdlUserList.addRow(rowUserList);
        }
    }

}
