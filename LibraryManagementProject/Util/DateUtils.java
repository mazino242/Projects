package com.Projects.OnlyJavaUsage.LibraryManagementProject.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String format(LocalDate date) {
        return date.format(formatter);
    }

    public static LocalDate parse(String dateStr) {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static String getCurrentDateStr() {
        return LocalDate.now().format(formatter);
    }
}