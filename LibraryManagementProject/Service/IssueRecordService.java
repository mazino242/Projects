package com.Projects.OnlyJavaUsage.LibraryManagementProject.Service;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IIssueRecordDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Book;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.IssueRecord;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IssueRecordService {
    private IIssueRecordDao issueRecordDao;
    private BookService bookService;
    private MemberService memberService;

    public IssueRecordService(IIssueRecordDao issueRecordDao, BookService bookService, MemberService memberService) {
        this.issueRecordDao = issueRecordDao;
        this.bookService = bookService;
        this.memberService = memberService;
        loadData();
    }

    public void issueBook(String bookId, String memberId) {
        Book book = bookService.getByBookId(bookId);
        Member member = memberService.getByMemberId(memberId);
        if (book == null || member == null) {
            throw new RuntimeException("Book or Member is invalid");
        }
        if (!bookService.isBookAvailable(bookId)) {
            throw new RuntimeException("Book is not available");
        }
        if (member.getIssuedBookIds().contains(bookId)) {
            throw new RuntimeException("Member has already issued this book");
        }
        IssueRecord newRecord = new IssueRecord(bookId, memberId, LocalDate.now());
        issueRecordDao.add(newRecord);
        book.setIssued(true);
        bookService.updateBook(book);
        memberService.issueBookToMember(memberId, bookId);
        saveData();
    }

    public void returnBook(String bookId, String memberId) {
        Book book = bookService.getByBookId(bookId);
        Member member = memberService.getByMemberId(memberId);
        if (book == null || member == null || !member.getIssuedBookIds().contains(bookId)) {
            throw new RuntimeException("Book or Member is invalid");
        }
        IssueRecord recordToUpdate = null;
        for (IssueRecord record : issueRecordDao.getAll()) {
            if (record.getBookId().equals(bookId) &&
                    record.getMemberId().equals(memberId) &&
                    record.getReturnDate() == null) {
                recordToUpdate = record;
                break;
            }
        }
        if (recordToUpdate == null) {
            throw new RuntimeException("No active issue record found for this book and member.");
        }
        recordToUpdate.setReturnDate(LocalDate.now());
        book.setIssued(false);
        bookService.updateBook(book);
        memberService.returnBookFromMember(memberId, bookId);
        issueRecordDao.update(recordToUpdate);
        saveData();
    }

    public List<IssueRecord> getAllRecordsForMember(String memberId) {
        if (memberId == null || memberId.isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        return issueRecordDao.getAll().stream()
                .filter(record -> record.getMemberId().equals(memberId))
                .toList();
    }

    public List<IssueRecord> getActiveIssuedRecordsForMember(String memberId) {
        if (memberId == null || memberId.isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        return issueRecordDao.getAll().stream()
                .filter(record -> record.getMemberId().equals(memberId) && record.getReturnDate() == null)
                .toList();
    }

    public List<IssueRecord> getAllActiveIssuedRecords() {
        return issueRecordDao.getAll().stream()
                .filter(record -> record.getReturnDate() == null)
                .toList();
    }

    public List<IssueRecord> getAllRecordsForBook(String bookId) {
        if (bookId == null || bookId.isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        return issueRecordDao.getAll().stream()
                .filter(record -> record.getBookId().equals(bookId))
                .toList();
    }

    public List<IssueRecord> getReturnedRecordsForMember(String memberId) {
        if (memberId == null || memberId.isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        return issueRecordDao.getAll().stream()
                .filter(record -> record.getMemberId().equals(memberId) && record.getReturnDate() != null)
                .toList();
    }

    public void removeMember(String memberId) {
        if (memberId == null || memberId.isEmpty()) {
            throw new IllegalArgumentException("Member Id cannot be null or empty");
        }
        List<IssueRecord> records = getActiveIssuedRecordsForMember(memberId);
        if (null!=records || !records.isEmpty()){
            List<String> bookIds= new ArrayList<>();
            records.stream().filter(record-> record.getBookId()!=null).toList()
                    .forEach(record-> bookIds.add(record.getBookId()));
            bookIds.forEach(bookId->returnBook(bookId,memberId));
            memberService.removeMember(memberId);
            saveData();
        }
    }

    public void removeBook(String bookId) {
        if (bookId == null || bookId.isEmpty()) {
            throw new IllegalArgumentException("Book Id cannot be null or empty");
        }
        List<IssueRecord> records = getAllActiveIssuedRecords();
        if (null!=records || !records.isEmpty()){
            for (IssueRecord record:records){
                if (record.getBookId().equals(bookId)){
                    returnBook(bookId,record.getMemberId());
                }
            }
            bookService.removeBook(bookId);
            saveData();
        }
    }

    public List<IssueRecord> getAll() {
        return issueRecordDao.getAll();
    }
    public void loadData() {
        issueRecordDao.load();
    }

    public void saveData() {
        issueRecordDao.save();
    }
}