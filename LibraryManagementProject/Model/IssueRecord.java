package com.Projects.OnlyJavaUsage.LibraryManagementProject.Model;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Util.DateUtils;

import java.time.LocalDate;

public class IssueRecord {
    private final String bookId;
    private final String memberId;
    private final LocalDate issueDate;
    private LocalDate returnDate;

    public IssueRecord(String bookId, String memberId, LocalDate issueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = null;
    }

    public String getBookId() {
        return bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String toTextLine() {
        return this.bookId + "|" + this.memberId + "|" + DateUtils.format(this.issueDate)
                + "|" + (this.returnDate != null ? DateUtils.format(this.returnDate) : "");
    }

    public static IssueRecord fromTextLine(String line) {
        String[] record = line.split("\\|");
        if (!(record.length >= 3 && record.length<=4)) throw new IllegalArgumentException("Invalid Issue Record Entry:" + line);
        LocalDate returnDate;
        returnDate = record.length==3 || record[3].isEmpty() ? null : DateUtils.parse(record[3]);
        IssueRecord issueRecord = new IssueRecord(record[0], record[1], DateUtils.parse(record[2]));
        issueRecord.setReturnDate(returnDate);
        return issueRecord;
    }
}