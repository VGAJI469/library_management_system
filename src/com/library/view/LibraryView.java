package com.library.view;

import com.library.model.Book;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Console view for Library Management System.
 */
public class LibraryView {
    private final Scanner scanner;

    public LibraryView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println();
        System.out.println("====== Library Management System ======");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Book by ID");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public void displayBooks(List<Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("\nID  | Title                          | Author                 | Status");
        System.out.println("----+--------------------------------+-----------------------+--------");
        for (Book b : books) {
            String status = b.isIssued() ? "Issued" : "Available";
            System.out.printf("%-3d | %-30s | %-21s | %-8s%n",
                    b.getId(), truncate(b.getTitle(), 30), truncate(b.getAuthor(), 21), status);
        }
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max - 3) + "...";
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}
