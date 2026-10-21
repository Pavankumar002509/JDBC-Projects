package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import db.DBConnection;

public class BookDAO {
	 public void addBook() {
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter Book titile: ");
		 String title = sc.nextLine();
		 
		 System.out.println("Eneter author name: ");
		 String author = sc.nextLine();
		 
		 System.out.println("Enter ISBN: ");
		 String isbn = sc.nextLine();
		 
		 System.out.println("Enter total copies: ");
		 int totalCopies = sc.nextInt();
		 
		 String sql = "INSERT INTO books "
			        + "(title, author, isbn, total_copies, available_copies) "
			        + "VALUES (?, ?, ?, ?, ?)";
		 
		 try(Connection con = DBConnection.getConnection();
				 PreparedStatement ps = con.prepareStatement(sql)){
					 ps.setString(1,title);
					 ps.setString(2, author);
					 ps.setString(3, isbn);
					 ps.setInt(4, totalCopies);
					 ps.setInt(5,totalCopies);
					 
					 int rows = ps.executeUpdate();
					 if(rows > 0) {
						 System.out.println("Book added sucessfully!");
					 }
				 }catch (SQLException e) {
					 System.out.println("Error adding book.");
					 e.printStackTrace();
				 }
	 }
	 
	 public void viewBooks() {

	     String sql = "SELECT * FROM books";

	     try (Connection con = DBConnection.getConnection();
	          PreparedStatement ps = con.prepareStatement(sql);
	          java.sql.ResultSet rs = ps.executeQuery()) {

	         System.out.println("\n========== LIBRARY BOOKS ==========");
	         System.out.printf("%-5s %-25s %-20s %-15s %-10s %-10s%n",
	                 "ID", "TITLE", "AUTHOR", "ISBN", "TOTAL", "AVAILABLE");

	         while (rs.next()) {
	             int id = rs.getInt("book_id");
	             String title = rs.getString("title");
	             String author = rs.getString("author");
	             String isbn = rs.getString("isbn");
	             int total = rs.getInt("total_copies");
	             int available = rs.getInt("available_copies");
	             System.out.printf("%-5d %-25s %-20s %-15s %-10d %-10d%n",
	                     id, title, author, isbn, total, available);
	         }

	     } catch (SQLException e) {
	         System.out.println("Error retrieving books.");
	         e.printStackTrace();
	     }
	 }
	 
	 
	 public void searchBookById() {

	     Scanner sc = new Scanner(System.in);
	     System.out.print("Enter Book ID to search: ");
	     int id = sc.nextInt();
	     String sql = "SELECT * FROM books WHERE book_id = ?";

	     try (Connection con = DBConnection.getConnection();
	          PreparedStatement ps = con.prepareStatement(sql)) {
	         ps.setInt(1, id);
	         try (java.sql.ResultSet rs = ps.executeQuery()) {
	             if (rs.next()) {
	                 System.out.println("\nBook Found!");
	                 System.out.println("Book ID: " + rs.getInt("book_id"));
	                 System.out.println("Title: " + rs.getString("title"));
	                 System.out.println("Author: " + rs.getString("author"));
	                 System.out.println("ISBN: " + rs.getString("isbn"));
	                 System.out.println("Total Copies: " + rs.getInt("total_copies"));
	                 System.out.println("Available Copies: " + rs.getInt("available_copies"));
	             } else {
	                 System.out.println("Book not found!");
	             }
	         }

	     } catch (SQLException e) {
	         System.out.println("Error searching for book.");
	         e.printStackTrace();
	     }
	 }
	 
	 
	 public void deleteBook() {
	     Scanner sc = new Scanner(System.in);
	     System.out.print("Enter Book ID to delete: ");
	     int id = sc.nextInt();
	     String sql = "DELETE FROM books WHERE book_id = ?";
	     try (Connection con = DBConnection.getConnection();
	          PreparedStatement ps = con.prepareStatement(sql)) {
	         ps.setInt(1, id);
	         int rows = ps.executeUpdate();

	         if (rows > 0) {
	             System.out.println("Book deleted successfully!");
	         } else {
	             System.out.println("Book ID not found!");
	         }

	     } catch (SQLException e) {
	         System.out.println("Unable to delete this book.");
	         System.out.println("It may have existing issue records.");
	         e.printStackTrace();
	     }
	 }
	 
	 
	 // to add book
//	 public static void main(String[] args) {
//		 BookDAO dao = new BookDAO();
//		 dao.addBook();
//	 }
	 
	 // to view table
//	 public static void main(String[] args) {
//		    BookDAO dao = new BookDAO();
//		    dao.viewBooks();
//		}
	 
	 // to search book by ID
//	 public static void main(String[] args) {
//		    BookDAO dao = new BookDAO();
//		    dao.searchBookById();
//		}
	 
	 // Delete Book BY  ID
	 public static void main(String[] args) {
		    BookDAO dao = new BookDAO();
		    dao.deleteBook();
		}
}
