package main.java.com.example.library;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private LibraryService libraryService;

    @Override
    public void init() throws ServletException {
        libraryService = new LibraryService();
        try {
            libraryService.createTable(); // Cria a tabela se não existir
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize the database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> books = libraryService.listBooks();
            request.setAttribute("books", books);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Failed to list books", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int year = Integer.parseInt(request.getParameter("year"));
        try {
            libraryService.addBook(title, author, year);
            response.sendRedirect("books");
        } catch (SQLException e) {
            throw new ServletException("Failed to add book", e);
        }
    }
}