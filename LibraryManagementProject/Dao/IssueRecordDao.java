package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.IssueRecord;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IFileAccess;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IIssueRecordDao;

import java.util.ArrayList;
import java.util.List;

public class IssueRecordDao implements IIssueRecordDao {
    private List<IssueRecord> issueRecords;
    private IFileAccess<IssueRecord> fileAccess;
    private String filePath;

    public IssueRecordDao(IFileAccess<IssueRecord> fileAccess, String filePath) {
        this.fileAccess = fileAccess;
        this.filePath = filePath;
        issueRecords = new ArrayList<>();
        load();
    }

    @Override
    public List<IssueRecord> getByMemberId(String memberId) {
        List<IssueRecord> issueRecordList = new ArrayList<>();
        for (IssueRecord issueRecord : this.issueRecords) {
            if (issueRecord.getMemberId().equals(memberId)) issueRecordList.add(issueRecord);
        }
        return issueRecordList;
    }

    @Override
    public List<IssueRecord> getByBookId(String bookId) {
        List<IssueRecord> issueRecordList = new ArrayList<>();
        for (IssueRecord issueRecord : this.issueRecords) {
            if (issueRecord.getBookId().equals(bookId)) issueRecordList.add(issueRecord);
        }
        return issueRecordList;
    }

    @Override
    public List<IssueRecord> getAll() {
        return this.issueRecords;
    }

    @Override
    public void add(IssueRecord issueRecord) {
        this.issueRecords.add(issueRecord);
    }

    @Override
    public void update(IssueRecord updatedIssueRecord) {
        for (int i = 0; i < this.issueRecords.size(); i++) {
            if (this.issueRecords.get(i).getBookId().equals(updatedIssueRecord.getBookId()) &&
                    this.issueRecords.get(i).getMemberId().equals(updatedIssueRecord.getMemberId()) &&
                    this.issueRecords.get(i).getIssueDate().equals(updatedIssueRecord.getIssueDate())) {
                this.issueRecords.set(i, updatedIssueRecord);
                return;
            }
        }
    }

    @Override
    public void load() {
        this.issueRecords = fileAccess.readFromFile(this.filePath);
    }

    @Override
    public void save() {
        fileAccess.writeToFile(this.issueRecords, this.filePath);
    }
}