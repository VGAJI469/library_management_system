package com.library;

import com.library.controller.LibraryController;
import com.library.model.BookDAO;
import com.library.view.LibraryView;

/**
 * Entry point for the Library Management System application.
 */
public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryView view = new LibraryView();
        BookDAO dao = new BookDAO();
        LibraryController controller = new LibraryController(view, dao);
        controller.processUserChoice();
    }
}
