
package dao;

import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {

        Scanner sc = InputUtil.sc;

        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        IssueDAO issueDAO = new IssueDAO();
        ReturnDAO returnDAO = new ReturnDAO();
        ReportDAO reportDAO = new ReportDAO();

        int choice;

        do {
            System.out.println("\n==================================");
            System.out.println("   LIBRARY MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by ID");
            System.out.println("4. Delete Book");
            System.out.println("5. Add Member");
            System.out.println("6. View All Members");
            System.out.println("7. Search Member by ID");
            System.out.println("8. Delete Member");
            System.out.println("9. Issue Book");
            System.out.println("10. Return Book");
            System.out.println("11. View Issue Report");
            System.out.println("0. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a number.");
                sc.nextLine();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    bookDAO.addBook();
                    break;

                case 2:
                    bookDAO.viewBooks();
                    break;

                case 3:
                    bookDAO.searchBookById();
                    break;

                case 4:
                    bookDAO.deleteBook();
                    break;

                case 5:
                    memberDAO.addMember();
                    break;

                case 6:
                    memberDAO.viewMembers();
                    break;

                case 7:
                    memberDAO.searchMemberById();
                    break;

                case 8:
                    memberDAO.deleteMember();
                    break;

                case 9:
                    issueDAO.issueBook();
                    break;

                case 10:
                    returnDAO.returnBook();
                    break;

                case 11:
                    reportDAO.viewIssueReport();
                    break;

                case 0:
                    System.out.println("Thank you for using Library Management System!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

        } while (choice != 0);
    }
}
