package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.IssueRecord;

import java.util.List;

public interface IIssueRecordDao extends IDataAccess<IssueRecord> {
    List<IssueRecord> getByMemberId(String memberId);

    List<IssueRecord> getByBookId(String bookId);
}