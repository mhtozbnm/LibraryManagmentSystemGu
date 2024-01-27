package org.librarysystem.model;

import org.librarysystem.helper.DBConnecter;
import org.librarysystem.helper.Helper;

import java.security.PrivilegedAction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String password;
    private String type;

    public User() {

    }

    public User(int id, String name, String uname, String password, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `user`");
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("user_name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static boolean add(String name, String uname, String password, String type) {
        String query = "INSERT INTO `user`(`user_name`, `uname`, `password`, `type`) VALUES (?,?,?,?)";
        User findUser = User.getFetch(uname);
        if (findUser != null) {
            Helper.showMessage("findSameUser");
            return false;
        }
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, uname);
            pr.setString(3, password);
            pr.setString(4, type);
            int r = pr.executeUpdate();
            if (r == -1) {
                Helper.showMessage("error");
            }
            return r != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static User getFetch(String uname) {
        User obj = null;
        String query = "SELECT * FROM `user` where uname = ?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1, uname);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("user_name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static User getFetch(String uname, String password) {
        User obj = null;
        String query = "SELECT * FROM `user` where uname = ? and password = ?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1, uname);
            pr.setString(2,password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                switch (rs.getString("type")) {
                    case "employee":
                        obj = new Employee();
                        break;
                    case "member":
                        obj = new Member();
                    default:
                        obj = new User();
                        break;
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("user_name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM `user` WHERE id = ?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(int id, String name, String uname, String password, String type) {
        String query = "UPDATE `user` SET `user_name`=?,`uname`=?,`password`=?,`type`=? where id = ?";
        User findUser = User.getFetch(uname);
        if (findUser != null && findUser.getId() != id) {
            Helper.showMessage("findSameUser");
            return false;
        }
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setString(2, uname);
            pr.setString(3, password);
            pr.setString(4, type);
            pr.setInt(5, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("user_name"));
                obj.setUname(rs.getString("uname"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static String searchQuery(String name, String uname, String type) {
        String sqlQuery = "SELECT * FROM `user` WHERE uname LIKE '%{{uname}}%' AND user_name LIKE '%{{user_name}}%'";
        sqlQuery = sqlQuery.replace("{{uname}}", uname);
        sqlQuery = sqlQuery.replace("{{user_name}}", name);
        if (!type.isEmpty()) {
            sqlQuery += "AND TYPE = '{{type}}'";
            sqlQuery = sqlQuery.replace("{{type}}", type);
        }
        System.out.println(sqlQuery);
        return sqlQuery;
    }



}
