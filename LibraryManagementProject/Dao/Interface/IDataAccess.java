package com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface;

import java.util.List;

public interface IDataAccess<T> {
    List<T> getAll();

    void add(T obj);

    void update(T obj);

    void load();

    void save();
}