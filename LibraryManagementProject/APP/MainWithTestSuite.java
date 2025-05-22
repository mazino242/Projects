
package com.Projects.OnlyJavaUsage.LibraryManagementProject.APP;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.BookDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.IssueRecordDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.MemberDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.FileAccess.BookFileAccess;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.FileAccess.IssueRecordFileAccess;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.FileAccess.MemberFileAccess;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Service.BookService;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Service.IssueRecordService;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Service.MemberService;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Util.Constants;

public class MainWithTestSuite {
    public static void main(String[] args) {
        BookDao bookDao = new BookDao(new BookFileAccess(), Constants.BOOK_FILE_PATH);
        MemberDao memberDao = new MemberDao(new MemberFileAccess(), Constants.MEMBER_FILE_PATH);
        IssueRecordDao issueRecordDao = new IssueRecordDao(new IssueRecordFileAccess(), Constants.ISSUE_RECORD_FILE_PATH);

        BookService bookService = new BookService(bookDao);
        MemberService memberService = new MemberService(memberDao);
        IssueRecordService issueRecordService = new IssueRecordService(issueRecordDao, bookService, memberService);

        // Run test suites
        TestSuite.runAllTests(bookService, memberService, issueRecordService);
    }
}
