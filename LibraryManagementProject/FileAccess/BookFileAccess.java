package com.Projects.OnlyJavaUsage.LibraryManagementProject.FileAccess;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Book;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IFileAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookFileAccess implements IFileAccess<Book> {
    private static final Logger logger = Logger.getLogger(BookFileAccess.class.getName());

    @Override
    public List<Book> readFromFile(String filePath) {
        List<Book> books = new ArrayList<>();
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String bookLine;
                while (null != (bookLine = bufferedReader.readLine())) {
                    Book book = Book.fromTextLine(bookLine);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file:" + filePath, e);
        }
        return books;
    }

    @Override
    public void writeToFile(List<Book> books, String filePath) {
        if (null == books || books.isEmpty()) {
            logger.log(Level.WARNING, "Book list is empty Skipping writing.");
            return;
        }
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                logger.info("Writing to: " + file.getAbsolutePath());
                books.forEach(book -> {
                    try {
                        bufferedWriter.write(book.toTextLine());
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Error writing bookLine:" + book.toTextLine(), e);
                    }
                });
                logger.info("Finished writing");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing in file:" + filePath, e);
        }
    }
}