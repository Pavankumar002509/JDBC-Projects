package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IssueDAO {
    public void issueBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        String checkBook = "SELECT available_copies FROM books WHERE book_id = ?";
        String checkMember = "SELECT member_id FROM members WHERE member_id = ?";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {                // Check whether the book exists and is available.
                try (PreparedStatement ps = con.prepareStatement(checkBook)) {
                    ps.setInt(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Book ID not found!");
                            con.rollback();
                            return;
                        }

                        if (rs.getInt("available_copies") <= 0) {
                            System.out.println("No copies available!");
                            con.rollback();
                            return;
                        }
                    }
                }                
                try (PreparedStatement ps = con.prepareStatement(checkMember)) {
                    ps.setInt(1, memberId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Member ID not found!");
                            con.rollback();
                            return;
                        }
                    }
                }
                String insertIssue =
                        "INSERT INTO book_issues "
                        + "(book_id, member_id, issue_date, due_date, status) "
                        + "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'ISSUED')";
                try (PreparedStatement ps = con.prepareStatement(insertIssue)) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, memberId);
                    ps.executeUpdate();
                }
                String updateBook =
                        "UPDATE books SET available_copies = available_copies - 1 "
                        + "WHERE book_id = ? AND available_copies > 0";
                try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                    ps.setInt(1, bookId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Could not update available copies.");
                    }
                }
                con.commit();
                System.out.println("Book issued successfully!");
                System.out.println("Due date: 14 days from today.");
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error issuing book.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IssueDAO dao = new IssueDAO();
        dao.issueBook();
    }
}
