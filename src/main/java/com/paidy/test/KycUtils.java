package com.paidy.test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.paidy.test.exceptions.InvalidDateException;
import com.paidy.test.exceptions.NegativeIntegerException;
import com.paidy.test.exceptions.NegativeTimePeriodException;

/**
 * Features required by the Paidy KYC software engineer tech test
 * - Ordinal suffix processing
 * - Date processing
 * - String obfuscation: emails and phone numbers
 * Implement these in a class (no need for a main())
 * Note: all methods are static, as such this class is thread-safe
 */
public final class KycUtils {
    private KycUtils() {
    }

    /**
     * For any given positive integer, append an ordinal suffix
     * 
     * @param input positive integer value
     * @return String value, the input + its ordinal suffix
     */
    public static String appendOrdinalSuffix(int input) throws NegativeIntegerException {
        if (input < 0) {
            throw new NegativeIntegerException("Ordinal suffixes: negative integers are not supported: " + input);
        }

        String suffix;
        if (input >= 11 && input <= 13) {
            // Special edge cases: 11th, 12th, 13th
            suffix = "th";
        } else {
            // General case, the normal rule applies
            switch (input % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        return String.format("%d%s", input, suffix);
    }

    /**
     * For any two given dates, return the number of sundays during the period.
     * 
     * @param date_from Start date (dd-mm-yyyy format)
     * @param date_to   End date, >= date1 (if <, an exception will be thrown).
     * @return int Number of Sundays during the period
     */
    public static int calculateSundays(String date_from, String date_to)
            throws NegativeTimePeriodException, InvalidDateException {
        return calculateNbSpecificDaysInPeriod(date_from, date_to, DayOfWeek.SUNDAY);
    }

    /**
     * For any two given dates, return the number of specified days during the
     * period. The range is inclusive (ie. if either date_from or date_to is a
     * Sunday, it will be included as well)
     * 
     * @param date_from   Start date (dd-mm-yyyy format)
     * @param date_to     End date, >= date1 (if <, an exception will be thrown).
     * @param specificDay DayOfWeek enum, eg. SUNDAY or SATURDAY
     * @return int Number of specified days during the period
     */
    static int calculateNbSpecificDaysInPeriod(String date_from, String date_to, DayOfWeek specificDay)
            throws NegativeTimePeriodException, InvalidDateException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1;
        LocalDate date2;
        try {
            date1 = LocalDate.parse(date_from, dtf);
            date2 = LocalDate.parse(date_to, dtf);
        } catch (Exception e) {
            throw new InvalidDateException("Failed to parse date: " + e.getMessage());
        }

        if (date1.compareTo(date2) > 0) {
            throw new NegativeTimePeriodException("Invalid date range: date_to cannot be < date_from");
        }

        // Naive O(n) algorithm: loop over the period to find all Sundays
        // There might be a more efficient O(1) approach, but I prefer a working
        // solution which doesn't fail any unit test (including leap years, summertime
        // transitions and short months like February)
        int cpt = 0;
        while (date1.compareTo(date2) <= 0) {
            if (date1.getDayOfWeek() == specificDay) {
                cpt += 1;
            }
            date1 = date1.plusDays(1);
        }

        return cpt;
    }

}
