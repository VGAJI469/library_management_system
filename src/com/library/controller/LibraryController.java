package com.library.controller;

import com.library.model.Book;
import com.library.model.BookDAO;
import com.library.view.LibraryView;

import java.util.List;

/**
 * Controller coordinating user actions with the Model and View.
 */
public class LibraryController {
    private final LibraryView view;
    private final BookDAO bookDAO;
    private boolean running = true;

    public LibraryController(LibraryView view, BookDAO bookDAO) {
        this.view = view;
        this.bookDAO = bookDAO;
    }

    public void processUserChoice() {
        while (running) {
            view.displayMenu();
            String choiceStr = view.getInput();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr.trim());
            } catch (NumberFormatException e) {
                view.displayMessage("Invalid choice. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1 -> handleAddBook();
                case 2 -> handleViewBooks();
                case 3 -> handleIssueBook();
                case 4 -> handleReturnBook();
                case 5 -> handleSearchBook();
                case 6 -> {
                    running = false;
                    view.displayMessage("Exiting application. Goodbye!");
                }
                default -> view.displayMessage("Invalid choice. Please select 1-6.");
            }
        }
    }

    public void handleAddBook() {
        String title = view.getNonEmptyInput("Enter book title: ");
        String author = view.getNonEmptyInput("Enter book author: ");
        Book book = new Book(title, author);
        boolean ok = bookDAO.addBook(book);
        view.displayMessage(ok ? "Book added successfully." : "Failed to add book.");
    }

    public void handleIssueBook() {
        int id = view.getIntInput("Enter book ID to issue: ");
        Book existing = bookDAO.searchBookById(id);
        if (existing == null) {
            view.displayMessage("Book not found.");
            return;
        }
        if (existing.isIssued()) {
            view.displayMessage("Book is already issued.");
            return;
        }
        boolean ok = bookDAO.issueBook(id);
        view.displayMessage(ok ? "Book issued successfully." : "Failed to issue book.");
    }

    public void handleReturnBook() {
        int id = view.getIntInput("Enter book ID to return: ");
        Book existing = bookDAO.searchBookById(id);
        if (existing == null) {
            view.displayMessage("Book not found.");
            return;
        }
        if (!existing.isIssued()) {
            view.displayMessage("Book is not issued.");
            return;
        }
        boolean ok = bookDAO.returnBook(id);
        view.displayMessage(ok ? "Book returned successfully." : "Failed to return book.");
    }

    public void handleViewBooks() {
        List<Book> books = bookDAO.getAllBooks();
        view.displayBooks(books);
    }

    public void handleSearchBook() {
        int id = view.getIntInput("Enter book ID to search: ");
        Book b = bookDAO.searchBookById(id);
        if (b == null) {
            view.displayMessage("Book not found.");
        } else {
            view.displayMessage(String.format("Found -> ID: %d, Title: %s, Author: %s, Status: %s",
                    b.getId(), b.getTitle(), b.getAuthor(), b.isIssued() ? "Issued" : "Available"));
        }
    }
}
