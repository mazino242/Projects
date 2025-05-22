package com.Projects.OnlyJavaUsage.LibraryManagementProject.FileAccess;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.IssueRecord;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IFileAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IssueRecordFileAccess implements IFileAccess<IssueRecord> {
    private static final Logger logger = Logger.getLogger(IssueRecordFileAccess.class.getName());

    @Override
    public List<IssueRecord> readFromFile(String filePath) {
        List<IssueRecord> issueRecords = new ArrayList<>();
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String issueRecordLine;
                while (null != (issueRecordLine = bufferedReader.readLine())) {
                    IssueRecord issueRecord = IssueRecord.fromTextLine(issueRecordLine);
                    issueRecords.add(issueRecord);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file:" + filePath, e);
        }
        return issueRecords;
    }

    @Override
    public void writeToFile(List<IssueRecord> issueRecords, String filePath) {
        if (null == issueRecords || issueRecords.isEmpty()) {
            logger.log(Level.WARNING, "Issue Record list is empty Skipping writing.");
            return;
        }
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                logger.info("Writing to: " + file.getAbsolutePath());
                issueRecords.forEach(issueRecord -> {
                    try {
                        bufferedWriter.write(issueRecord.toTextLine());
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Error writing issueRecordLine:" + issueRecord.toTextLine(), e);
                    }
                });
                logger.info("Finished writing");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing in file:" + filePath, e);
        }
    }
}