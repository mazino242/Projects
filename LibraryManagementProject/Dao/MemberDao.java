package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Member;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IFileAccess;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IMemberDao;

import java.util.ArrayList;
import java.util.List;

public class MemberDao implements IMemberDao {
    private List<Member> members;
    private IFileAccess<Member> fileAccess;
    private String filePath;

    public MemberDao(IFileAccess<Member> fileAccess, String filePath) {
        this.fileAccess = fileAccess;
        this.filePath = filePath;
        this.members = new ArrayList<>();
        load();
    }

    @Override
    public List<Member> getAll() {
        return this.members;
    }

    @Override
    public Member getById(String id) {
        for (Member member : this.members) {
            if (member.getId().equals(id)) return member;
        }
        return null;
    }

    @Override
    public void add(Member member) {
        members.add(member);
    }

    @Override
    public void remove(String id){
        members.removeIf(member -> member.getId().equals(id));
    }

    @Override
    public void update(Member updatedMember) {
        for (int i = 0; i < this.members.size(); i++) {
            if (this.members.get(i).getId().equals(updatedMember.getId())) {
                this.members.set(i, updatedMember);
                return;
            }
        }
    }

    @Override
    public void load() {
        this.members = fileAccess.readFromFile(this.filePath);
    }

    @Override
    public void save() {
        fileAccess.writeToFile(this.members, this.filePath);
    }
}