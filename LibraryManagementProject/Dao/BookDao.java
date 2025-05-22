package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Book;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IBookDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IFileAccess;

import java.util.ArrayList;
import java.util.List;

public class BookDao implements IBookDao {
    private List<Book> books;
    private IFileAccess<Book> fileAccess;
    private String filePath;

    public BookDao(IFileAccess<Book> fileAccess, String filePath) {
        this.fileAccess = fileAccess;
        this.filePath = filePath;
        this.books = new ArrayList<>();
        load();
    }

    @Override
    public List<Book> getAll() {
        return this.books;
    }

    @Override
    public Book getById(String id) {
        for (Book book : this.books) {
            if (book.getId().equals(id)) return book;
        }
        return null;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public void add(Book book) {
        this.books.add(book);
    }

    @Override
    public void remove(String bookId) {
        this.books.removeIf(book -> book.getId().equals(bookId));
    }

    @Override
    public void update(Book updatedBook) {
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).getId().equals(updatedBook.getId())) {
                this.books.set(i, updatedBook);
                return;
            }
        }
    }

    @Override
    public void load() {
        this.books = fileAccess.readFromFile(this.filePath);
    }

    @Override
    public void save() {
        fileAccess.writeToFile(this.books, this.filePath);
    }
}