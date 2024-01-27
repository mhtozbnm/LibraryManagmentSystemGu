package org.librarysystem.model;

import org.librarysystem.helper.DBConnecter;
import org.librarysystem.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Book {
    private int id;
    private String author;
    private String title;
    private int publication_year;
    private String status;

    public Book() {

    }

    public Book(int id, String author, String title, int publication_year, String status) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.publication_year = publication_year;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ArrayList<Book> getList() {
        ArrayList<Book> bookList = new ArrayList<>();
        Book obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `book`");
            while (rs.next()) {
                obj = new Book();
                obj.setId(rs.getInt("id"));
                obj.setAuthor(rs.getString("author"));
                obj.setTitle(rs.getString("title"));
                obj.setPublication_year(rs.getInt("publication_year"));
                obj.setStatus(rs.getString("status"));
                bookList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public static boolean add(String author, String title,int publication_year, String status) {
        String query = "INSERT INTO `book`(`author`, `title`, `publication_year`, `status`) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1,author);
            pr.setString(2,title);
            pr.setInt(3,publication_year);
            pr.setString(4,status);
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

    public static boolean delete(int id) {
        String query = "DELETE FROM `book` WHERE id = ?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<Book> searchBookList(String query) {
        ArrayList<Book> bookList = new ArrayList<>();
        Book obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new Book();
                obj.setId(rs.getInt("id"));
                obj.setAuthor(rs.getString("author"));
                obj.setTitle(rs.getString("title"));
                obj.setPublication_year(rs.getInt("publication_year"));
                obj.setStatus(rs.getString("status"));
                bookList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public static String searchQuery(String title, String author, String status) {
        String sqlQuery = "SELECT * FROM `book` WHERE title LIKE '%{{title}}%' AND author LIKE '%{{author}}%'";
        sqlQuery = sqlQuery.replace("{{title}}", title);
        sqlQuery = sqlQuery.replace("{{author}}", author);
        if (!status.isEmpty()) {
            sqlQuery += "AND status = '{{status}}'";
            sqlQuery = sqlQuery.replace("{{status}}", status);
        }
        System.out.println(sqlQuery);
        return sqlQuery;
    }
}
