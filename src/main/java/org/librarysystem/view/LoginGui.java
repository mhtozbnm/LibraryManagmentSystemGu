package org.librarysystem.view;

import org.librarysystem.helper.Config;
import org.librarysystem.helper.Helper;
import org.librarysystem.model.Employee;
import org.librarysystem.model.User;

import javax.swing.*;

public class LoginGui extends JFrame{
    private JPanel wrpTop;
    private JPanel wrpBottom;
    private JLabel mainTxt;
    private JTextField txtFldUserName;
    private JButton btnLogin;
    private JPanel wrapper;
    private JLabel lnlUName;
    private JLabel lblPassword;
    private JPasswordField passFldLgnPass;

    public LoginGui() {

        add(wrapper);
        setSize(500,300);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        int x = Helper.screenCenterLocation("x",getSize());
        int y = Helper.screenCenterLocation("y",getSize());
        setLocation(x,y);
        setVisible(true);

        btnLogin.addActionListener(e -> {
            if (Helper.isFieldEmpty(txtFldUserName) || Helper.isFieldEmpty(passFldLgnPass)) {
                Helper.showMessage("fill");
            } else {
                User u = User.getFetch(txtFldUserName.getText(),passFldLgnPass.getText());
                if (u == null) {
                    Helper.showMessage("userNotFound");
                } else {
                    switch (u.getType()) {
                        case "employee":
                            EmployeeGui epGui = new EmployeeGui((Employee) u);
                            break;
                        case "member":
                            Helper.showMessage("noSucces");
                            break;
                        default:
                            Helper.showMessage("error");
                            break;
                    }
                    dispose();
                }
            }
        });
    }
}
