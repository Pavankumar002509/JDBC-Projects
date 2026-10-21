package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import db.DBConnection;


public class MemberDAO {
	public void addMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter member name: ");
		String name = sc.nextLine();
		System.out.println("Enter Phone Number: ");
		String phone = sc.nextLine();
		System.out.println("Enter email: ");
		String email = sc.nextLine();
		
		String sql = "Insert into members "
				+ "(member_name, phone, email, join_date) "
				+ "Values (?,?,?, CURDATE())";
		
		try(Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)){
			ps.setString(1,name);
			ps.setString(2, phone);
			ps.setString(3,email);
			int rows = ps.executeUpdate();
			if(rows > 0) {
				System.out.println("Member added Succesfully");
			}
		}catch(SQLException e) {
			System.out.println("Error adding members.");
			e.printStackTrace(); 
		}
	}
	

	public void viewMembers() {
	    String sql = "SELECT * FROM members";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         java.sql.ResultSet rs = ps.executeQuery()) {
	        System.out.println("\n======================================== LIBRARY MEMBERS ======================================");
	        System.out.printf("%-10s %-25s %-15s %-30s %-12s%n",
	                "ID", "NAME", "PHONE", "EMAIL", "JOIN DATE");
	        while (rs.next()) {
	            int id = rs.getInt("member_id");
	            String name = rs.getString("member_name");
	            String phone = rs.getString("phone");
	            String email = rs.getString("email");
	            java.sql.Date joinDate = rs.getDate("join_date");
	            System.out.printf("%-10d %-25s %-15s %-30s %-12s%n",
	                    id, name, phone, email, joinDate);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving members.");
	        e.printStackTrace();
	    }
	}
	
	public void searchMemberById() {
	    java.util.Scanner sc = InputUtil.sc;
	    System.out.print("Enter Member ID to search: ");
	    int id = sc.nextInt();
	    sc.nextLine();
	    String sql = "SELECT * FROM members WHERE member_id = ?";
	    try (java.sql.Connection con = db.DBConnection.getConnection();
	         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        try (java.sql.ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                System.out.println("Member ID: " + rs.getInt("member_id"));
	                System.out.println("Name: " + rs.getString("member_name"));
	                System.out.println("Phone: " + rs.getString("phone"));
	                System.out.println("Email: " + rs.getString("email"));
	                System.out.println("Join Date: " + rs.getDate("join_date"));
	            } else {
	                System.out.println("Member not found.");
	            }
	        }
	    } catch (java.sql.SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void deleteMember() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Memeber ID to delete: ");
		int id = sc.nextInt();
		String sql = "Delete from members where member_id = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1,id);
			int rows = ps.executeUpdate();
			if(rows > 0) {
				System.out.println("Members deleted Successfully !");
			}else {
				System.out.println("Memebr ID not found!");
			}
		} catch (SQLException e) {
			System.out.println("Unable to delete this member.");
			System.out.println("The member may have existing bookissue records.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MemberDAO dao = new MemberDAO();
//		dao.addMember();
//		dao.viewMembers();
//		dao.serachMemberById();
		dao.deleteMember();
	}
}
