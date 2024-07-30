package main.java.com.example.library;

public class LibraryApplication {
    public static void main(String[] args) {
        try {
            LibraryService service = new LibraryService();
            service.createTable(); // Cria a tabela se não existir

            // Adiciona um livro de exemplo
            service.addBook("1984", "George Orwell", 1949);

            // Lista todos os livros
            service.listBooks().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}