package com.paidy.test;

import com.paidy.test.exceptions.NegativeIntegerException;

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

}
