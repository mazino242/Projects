package com.Projects.OnlyJavaUsage.LibraryManagementProject.FileAccess;

import com.Projects.OnlyJavaUsage.LibraryManagementProject.Model.Member;
import com.Projects.OnlyJavaUsage.LibraryManagementProject.Dao.Interface.IFileAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberFileAccess implements IFileAccess<Member> {
    private static final Logger logger = Logger.getLogger(MemberFileAccess.class.getName());

    @Override
    public List<Member> readFromFile(String filePath) {
        List<Member> members = new ArrayList<>();
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String memberLine;
                while (null != (memberLine = bufferedReader.readLine())) {
                    Member member = Member.fromTextLine(memberLine);
                    members.add(member);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file:" + filePath, e);
        }
        return members;
    }

    @Override
    public void writeToFile(List<Member> members, String filePath) {
        if (null == members || members.isEmpty()) {
            logger.log(Level.WARNING, "Member list is empty Skipping writing.");
            return;
        }
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                logger.info("Writing to: " + file.getAbsolutePath());
                members.forEach(member -> {
                    try {
                        bufferedWriter.write(member.toTextLine());
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, "Error writing memberLine:" + member.toTextLine(), e);
                    }
                });
                logger.info("Finished writing");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing in file:" + filePath, e);
        }
    }
}