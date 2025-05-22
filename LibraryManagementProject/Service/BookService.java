package com.Projects.OnlyJavaUsage.LibraryManagementProject.Service;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IBookDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Book;

import java.util.List;

public class BookService {
    private IBookDao bookDao;

    public BookService(IBookDao bookDao) {
        this.bookDao = bookDao;
        loadData();
    }

    public List<Book> getAllBook() {
        return this.bookDao.getAll();
    }

    public Book getByBookId(String id) {
        if (null != id && !id.isEmpty()) return bookDao.getById(id);
        return null;
    }

    public List<Book> searchByTitle(String title) {
        if (null != title && !title.isEmpty()) return bookDao.searchByTitle(title);
        return null;
    }

    public void addBook(Book book) {
        if (null != this.bookDao.getById(book.getId())) {
            throw new RuntimeException("Book is already present try adding another");
        }
        if (null == book.getTitle() || null == book.getAuthor() || null == book.getId() || book.getTitle().isEmpty()
                || book.getAuthor().isEmpty() || book.getId().isEmpty()) {
            throw new RuntimeException("Check the Book Fields properly:" + book.toTextLine());
        }
        bookDao.add(book);
        saveData();
    }

    public void removeBook(String bookId) {
        if (null == this.bookDao.getById(bookId)) {
            throw new RuntimeException("Book is not present try removing another");
        }
        if (null == bookId || bookId.isEmpty()) {
            throw new RuntimeException("Check the Book Fields properly:" + bookId);
        }
        bookDao.remove(bookId);
        saveData();
    }

    public void updateBook(Book updatedBook) {
        if (null == updatedBook.getTitle() || null == updatedBook.getAuthor() || null == updatedBook.getId()) {
            throw new RuntimeException("Check the Book Fields properly:" + updatedBook.toTextLine());
        }
        bookDao.update(updatedBook);
        saveData();
    }

    public void loadData() {
        bookDao.load();
    }

    public void saveData() {
        bookDao.save();
    }

    public boolean isBookAvailable(String bookId) {
        Book book = bookDao.getById(bookId);
        return book != null && !book.isIssued();
    }
}