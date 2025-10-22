# Library Management System (Java, Console, MVC, JDBC + MySQL)

A simple console-based Library Management System built with Java using MVC architecture and JDBC connectivity to a MySQL database.

## Features
- Add new books
- View all books
- Issue/Return books
- Search book by ID
- Exit application

## Project Structure
```
src/
  com/library/
    LibraryManagementSystem.java
    controller/
      LibraryController.java
    model/
      Book.java
      BookDAO.java
      DBConnection.java
    view/
      LibraryView.java
schema.sql
config.properties
```

## Prerequisites
- Java 17+ (works with Java 8+, adjust compilation accordingly)
- MySQL Server (8.x recommended)
- MySQL Connector/J (JDBC driver) jar on your classpath

## Database Setup
1. Create the database and table:
   - Open MySQL client and run the SQL script:
   ```sql
   -- Use the provided file
   SOURCE /absolute/path/to/schema.sql;
   ```
   Or copy the contents of `schema.sql` and run it manually.

2. Update `config.properties` with your MySQL credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/library_db
   db.user=your_user
   db.password=your_password
   ```

## Build and Run (Windows PowerShell)
Assume the MySQL Connector/J jar is located at `lib/mysql-connector-j-8.4.0.jar`.

1. Compile:
   ```powershell
   javac -cp ".;lib/mysql-connector-j-8.4.0.jar" -d out src/com/library/model/Book.java src/com/library/model/DBConnection.java src/com/library/model/BookDAO.java src/com/library/view/LibraryView.java src/com/library/controller/LibraryController.java src/com/library/LibraryManagementSystem.java
   ```

2. Run:
   ```powershell
   java -cp ".;out;lib/mysql-connector-j-8.4.0.jar" com.library.LibraryManagementSystem
   ```

> Note: On Unix-like shells, replace classpath separators `;` with `:`.

## Notes
- Ensure MySQL service is running and the user has privileges on `library_db`.
- Uses PreparedStatement and try-with-resources for safety.
- If `config.properties` is missing, defaults are used (localhost, database `library_db`, user `root`, empty password).
