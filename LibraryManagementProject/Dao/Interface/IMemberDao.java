package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Member;

public interface IMemberDao extends IDataAccess<Member> {
    Member getById(String memberId);
    void remove(String memberId);
}