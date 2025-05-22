package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Book;

import java.util.List;

public interface IBookDao extends IDataAccess<Book> {
    Book getById(String bookId);

    List<Book> searchByTitle(String title);

    void remove(String bookId);
}