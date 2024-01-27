package org.librarysystem.helper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Helper {

    // gui ekranımızı her seferinde ortada açmamızı sağlar
    public static int screenCenterLocation(String matrix, Dimension size) {
        int point = 0;
        switch (matrix){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default:
                point = 0;
                break;
        }
        return point;
    }


    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().length() == 0;
    }

    public static boolean isFieldEmpty(JPasswordField field){
        return field.getPassword().length == 0;
    }

    public static void showMessage(String text){
        String msg;
        String title;
        switch (text){
            case "fill":
                msg = "Lütfen tüm alanları eksiksiz doldurun!";
                title = "Hata!";
                break;
            case "done":
                msg = "İşlem başarılı!";
                title = "Başarılı!";
                break;
            case "error":
                msg = "Kullanıcı eklenemedi!";
                title = "Başarısız";
                break;
            case "findSameUser":
                msg = "Lütfen farklı bir kullancı adı giriniz!";
                title = "Tekrar deneyin!";
                break;
            case "userNotFound":
                msg = "Kullanıcı kayıtlı değil lütfen yetkili ile iletişime geçiniz";
                title = "Uyarı!";
                break;
            case "noSucces":
                msg = "Yetkiziniz kısıtlıdır";
                title = "Uyar!";
            default:
                msg = text;
                title = "Uyarı!!!";
                break;
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

}
