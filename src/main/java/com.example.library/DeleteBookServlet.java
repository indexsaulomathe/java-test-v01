package main.java.com.example.library;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete")
public class DeleteBookServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        try {
            libraryService.deleteBook(id);
            response.sendRedirect("books");
        } catch (SQLException e) {
            throw new ServletException("Failed to delete book", e);
        }
    }
}
