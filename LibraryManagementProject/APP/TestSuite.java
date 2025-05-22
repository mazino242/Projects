
package com.Projects.OnlyJavaUsage.LibraryManagementProject.APP;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Service.BookService;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Service.IssueRecordService;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Service.MemberService;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Book;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Member;

public class TestSuite {

    public static void runAllTests(BookService bookService, MemberService memberService, IssueRecordService issueRecordService) {
        System.out.println("=== Running All 40 Tests ===");
        System.out.println("Test 1: Add a new book and confirm it appears in the list.");
        bookService.addBook(new Book("B003", "The Pragmatic Programmer", "Andrew Hunt", "Programming"));
        bookService.addBook(new Book("B001", "Atomic Habit", "author I don't Know", "Self help"));
        bookService.addBook(new Book("B002", "Sherlock Homes", "author I don't Know", "Mystery"));

        System.out.println("Test 2: Try to re-add the same book (should throw error).");
        try {
            bookService.addBook(new Book("B003", "The Pragmatic Programmer", "Andrew Hunt", "Programming"));
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 3: Add a new member and confirm.");
        memberService.addMember(new Member("M003", "Aman"));
        memberService.addMember(new Member("M001", "Nikhil"));

        System.out.println("Test 4: Try to re-add the same member (should fail).");
        try {
            memberService.addMember(new Member("M003", "Aman"));
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 5: Issue a new book to a new member.");
        issueRecordService.issueBook("B003", "M003");

        System.out.println("Test 6: Try to issue the same book again (should fail).");
        try {
            issueRecordService.issueBook("B003", "M001");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 7: Return the book issued in Test 5.");
        issueRecordService.returnBook("B003", "M003");

        System.out.println("Test 8: Try to return the same book again (should fail).");
        try {
            issueRecordService.returnBook("B003", "M003");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 9: Try issuing a book with an invalid book ID.");
        try {
            issueRecordService.issueBook("BXXX", "M003");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 10: Try issuing a book with an invalid member ID.");
        try {
            issueRecordService.issueBook("B001", "MXXX");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 11: Issue B001 to M003.");
        try {
            issueRecordService.issueBook("B001", "M003");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 12: Get all books issued to M003.");
        memberService.getByMemberId("M003").getIssuedBookIds().forEach(System.out::println);

        System.out.println("Test 13: Return B001 from M003.");
        issueRecordService.returnBook("B001", "M003");

        System.out.println("Test 14: Check returned records for M003.");
        issueRecordService.getReturnedRecordsForMember("M003").forEach(r -> System.out.println(r.toTextLine()));

        System.out.println("Test 15: Get all records for M003.");
        issueRecordService.getAllRecordsForMember("M003").forEach(r -> System.out.println(r.toTextLine()));

        System.out.println("Test 16: Attempt to return unissued book from M003 (should fail).");
        try {
            issueRecordService.returnBook("B002", "M003");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 17: Add member M004 and issue two books.");
        memberService.addMember(new Member("M004", "Sneha"));
        bookService.addBook(new Book("B004", "Refactoring", "Martin Fowler", "Programming"));
        bookService.addBook(new Book("B005", "Thinking Fast and Slow", "Daniel Kahneman", "Psychology"));
        issueRecordService.issueBook("B004", "M004");
        issueRecordService.issueBook("B005", "M004");

        System.out.println("Test 18: Get all issued book IDs for M004.");
        memberService.getByMemberId("M004").getIssuedBookIds().forEach(System.out::println);

        System.out.println("Test 19: Return one of the books from M004.");
        issueRecordService.returnBook("B004", "M004");

        System.out.println("Test 20: Get active issued records for M004.");
        issueRecordService.getActiveIssuedRecordsForMember("M004").forEach(r -> System.out.println(r.toTextLine()));

        System.out.println("Test 21: Add B006 and assign to M004.");
        bookService.addBook(new Book("B006", "Deep Work", "Cal Newport", "Self-help"));
        issueRecordService.issueBook("B006", "M004");

        System.out.println("Test 22: Confirm B006 status is issued.");
        System.out.println(bookService.getByBookId("B006").isIssued());

        System.out.println("Test 23: Return B006 and confirm status.");
        issueRecordService.returnBook("B006", "M004");
        System.out.println(bookService.getByBookId("B006").isIssued());

        System.out.println("Test 24: Get all records for B006.");
        issueRecordService.getAllRecordsForBook("B006").forEach(r -> System.out.println(r.toTextLine()));

        System.out.println("Test 25: Try issuing B006 again after return.");
        issueRecordService.issueBook("B006", "M001");

        System.out.println("Test 26: Try issuing a book to a member who already issued it.");
        try {
            issueRecordService.issueBook("B006", "M001");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 27: Try returning book not owned by a member.");
        try {
            issueRecordService.returnBook("B005", "M001");
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 28: Confirm book B005 is still with M004.");
        System.out.println(memberService.getByMemberId("M004").getIssuedBookIds().contains("B005"));

        System.out.println("Test 29: Remove B005 via return.");
        issueRecordService.returnBook("B005", "M004");

        System.out.println("Test 30: Confirm B005 removed from M004.");
        System.out.println(memberService.getByMemberId("M004").getIssuedBookIds().contains("B005"));

        System.out.println("Test 31: Issue and return B001 multiple times to M001.");
        for (int i = 0; i < 3; i++) {
            issueRecordService.issueBook("B001", "M001");
            issueRecordService.returnBook("B001", "M001");
        }

        System.out.println("Test 32: Print all records for B001.");
        issueRecordService.getAllRecordsForBook("B001").forEach(r -> System.out.println(r.toTextLine()));

        System.out.println("Test 33: Add invalid member with null name (should fail).");
        try {
            memberService.addMember(new Member("M999", null));
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 34: Add invalid book with empty title (should fail).");
        try {
            bookService.addBook(new Book("B999", "", "Some Author", "Genre"));
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 35: Check if book is available.");
        System.out.println(bookService.isBookAvailable("B002"));

        System.out.println("Test 36: Attempt to add a member with blank ID.");
        try {
            memberService.addMember(new Member("", "Ghost"));
        } catch (Exception e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        System.out.println("Test 37: Issue many books to one member (M001).");
        for (int i = 10; i < 15; i++) {
            String bookId = "B" + i;
            bookService.addBook(new Book(bookId, "Book " + i, "Author " + i, "Category"));
            issueRecordService.issueBook(bookId, "M001");
        }

        System.out.println("Test 38: Confirm M001 has 5 new books issued.");
        System.out.println(memberService.getByMemberId("M001").getIssuedBookIds().size());

        System.out.println("Test 39: Return all new books from M001.");
        for (int i = 10; i < 15; i++) {
            String bookId = "B" + i;
            issueRecordService.returnBook(bookId, "M001");
        }

        System.out.println("Test 40: Confirm all new books returned by M001.");
        System.out.println(memberService.getByMemberId("M001").getIssuedBookIds().size()==1);

        /*System.out.println("Test 41:remove member M001");
        try {
            issueRecordService.removeMember("M001");
        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }*/

        /*System.out.println("Test 42:remove again M001");
        try {
            issueRecordService.removeMember("M001");
        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }*/

        System.out.println("Test 42:remove book B006");
        try {
            issueRecordService.removeBook("B006");
        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
        }
    }
}
