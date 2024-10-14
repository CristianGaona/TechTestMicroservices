package com.crisda24.neoris.transactionaccount.infrastructure.utils;

import com.crisda24.neoris.transactionaccount.domain.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GeneralUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean isValidDateRange(String startDateStr, String endDateStr) {
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);
            return !startDate.isAfter(endDate);
        } catch (DateTimeParseException e) {
            throw new GeneralException("Invalid format: " + startDateStr + " - " + endDateStr, HttpStatus.BAD_REQUEST);
        }
    }
}
