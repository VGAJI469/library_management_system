package com.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Book entity. Uses PreparedStatement and try-with-resources.
 */
public class BookDAO {

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, issued) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isIssued());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("[ERROR] addBook failed: " + e.getMessage());
            return false;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT id, title, author, issued FROM books ORDER BY id";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setIssued(rs.getBoolean("issued"));
                list.add(b);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] getAllBooks failed: " + e.getMessage());
        }
        return list;
    }

    public boolean issueBook(int bookId) {
        String sql = "UPDATE books SET issued = TRUE WHERE id = ? AND issued = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("[ERROR] issueBook failed: " + e.getMessage());
            return false;
        }
    }

    public boolean returnBook(int bookId) {
        String sql = "UPDATE books SET issued = FALSE WHERE id = ? AND issued = TRUE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("[ERROR] returnBook failed: " + e.getMessage());
            return false;
        }
    }

    public Book searchBookById(int id) {
        String sql = "SELECT id, title, author, issued FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setIssued(rs.getBoolean("issued"));
                    return b;
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] searchBookById failed: " + e.getMessage());
        }
        return null;
    }
}
