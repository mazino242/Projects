package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface;

import java.util.List;

public interface IFileAccess<T> {
    List<T> readFromFile(String path);

    void writeToFile(List<T> data, String path);
}