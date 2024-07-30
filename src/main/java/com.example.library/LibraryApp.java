package main.java.com.example.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class LibraryApp {
    private JFrame frame;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTable booksTable;
    private LibraryService libraryService;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new LibraryApp().createAndShowGUI();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void createAndShowGUI() throws SQLException {
        libraryService = new LibraryService();

        frame = new JFrame("Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        JButton addButton = new JButton("Add Book");
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Create table panel
        booksTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(booksTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh List");
        frame.add(refreshButton, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBooks();
            }
        });

        loadBooks();
        frame.setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String yearStr = yearField.getText();

        try {
            int year = Integer.parseInt(yearStr);
            libraryService.addBook(title, author, year);
            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
            loadBooks();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid year format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        try {
            List<Book> books = libraryService.listBooks();
            String[] columns = {"ID", "Title", "Author", "Year"};
            Object[][] data = new Object[books.size()][4];

            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                data[i][0] = book.getId();
                data[i][1] = book.getTitle();
                data[i][2] = book.getAuthor();
                data[i][3] = book.getYear();
            }

            booksTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
