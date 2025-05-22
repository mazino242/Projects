package com.Projects.OnlyJavaUsage.LibraryManagementProject.Service;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IMemberDao;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Member;

import java.util.List;

public class MemberService {
    private IMemberDao memberDao;

    public MemberService(IMemberDao memberDao) {
        this.memberDao = memberDao;
        loadData();
    }

    public List<Member> getAllMembers() {
        return this.memberDao.getAll();
    }

    public Member getByMemberId(String id) {
        if (null != id && !id.isEmpty()) return memberDao.getById(id);
        return null;
    }

    public void addMember(Member member) {
        if (null != this.memberDao.getById(member.getId())) {
            throw new RuntimeException("member is already present try adding another");
        }
        if (null == member.getId() || null == member.getName() ||member.getName().isEmpty() || member.getId().isEmpty()) {
            throw new RuntimeException("Check the Member Fields properly:" + member.toTextLine());
        }
        memberDao.add(member);
        saveData();
    }

    public void removeMember(String memberId) {
        if (null == this.memberDao.getById(memberId)) {
            throw new RuntimeException("member is not present try removing another");
        }
        if (null == memberId || memberId.isEmpty()) {
            throw new RuntimeException("Check the Member id properly:" + memberId);
        }
        memberDao.remove(memberId);
        saveData();
    }

    public void issueBookToMember(String memberId, String bookId) {
        Member updateMember = getByMemberId(memberId);
        if (updateMember != null && !updateMember.getIssuedBookIds().contains(bookId)) {
            updateMember.issueBook(bookId);
            updateMember(updateMember);
        }
    }

    public void returnBookFromMember(String memberId, String bookId) {
        Member updateMember = getByMemberId(memberId);
        if (updateMember != null && updateMember.getIssuedBookIds()!=null) {
            updateMember.returnBook(bookId);
            updateMember(updateMember);
        }
    }

    public void updateMember(Member updateMember) {
        if (null == updateMember.getId() || null == updateMember.getName()) {
            throw new RuntimeException("Check the Member Fields properly:" + updateMember.toTextLine());
        }
        memberDao.update(updateMember);
        saveData();
    }

    public void loadData() {
        memberDao.load();
    }

    public void saveData() {
        memberDao.save();
    }
}