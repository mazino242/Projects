package com.Projects.OnlyJavaUsage.LibraryManagementProject.Model;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String category;
    private boolean isIssued;

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        this.isIssued = issued;
    }

    public String toTextLine() {
        return this.id + "|" + this.title + "|" + this.author + "|" + this.category + "|" + this.isIssued;
    }

    public static Book fromTextLine(String line) {
        String[] book = line.split("\\|");
        if (5 != book.length) throw new IllegalArgumentException("Invalid Book Entry:" + line);
        Book bookToAddOrUpdate = new Book(book[0], book[1], book[2], book[3]);
        bookToAddOrUpdate.setIssued(Boolean.parseBoolean(book[4]));
        return bookToAddOrUpdate;
    }
}