package com.Projects.OnlyJavaUsage.LibraryManagementProject.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Member {
    private final String id;
    private final String name;
    private List<String> issuedBookIds;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.issuedBookIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIssuedBookIds() {
        return issuedBookIds;
    }

    public void issueBook(String issueBookId) {
        this.issuedBookIds.add(issueBookId);
    }

    public void returnBook(String bookId) {
        this.issuedBookIds.remove(bookId);
    }

    public void setIssuedBookIds(List<String> issuedBookIds) {
        this.issuedBookIds = issuedBookIds;
    }

    public String toTextLine() {
        return this.id + "|" + this.name + "|" + String.join(",", this.issuedBookIds);
    }

    public static Member fromTextLine(String line) {
        String[] member = line.split("\\|");
        if (!(member.length>=2 && member.length<=3)) throw new IllegalArgumentException("Invalid Member Entry:" + line);
        Member memberToAddOrUpdate = new Member(member[0], member[1]);
        if (member.length==3) memberToAddOrUpdate.setIssuedBookIds(new ArrayList<>(Arrays.asList(member[2].split(","))));
        return memberToAddOrUpdate;
    }
}