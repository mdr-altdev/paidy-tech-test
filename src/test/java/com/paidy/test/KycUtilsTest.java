package com.paidy.test;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import com.paidy.test.exceptions.NegativeIntegerException;

/**
 * Unit test for simple App.
 */
public class KycUtilsTest {
    /**
     * Nominal test for the ordinal indicator processing function
     */
    @Test
    public void testNominalAppendOrdinalSuffix() throws NegativeIntegerException {
        assertTrue(KycUtils.appendOrdinalSuffix(1).equals("1st"));
        assertTrue(KycUtils.appendOrdinalSuffix(2).equals("2nd"));
        assertTrue(KycUtils.appendOrdinalSuffix(3).equals("3rd"));
        assertTrue(KycUtils.appendOrdinalSuffix(4).equals("4th"));
        assertTrue(KycUtils.appendOrdinalSuffix(5).equals("5th"));
        assertTrue(KycUtils.appendOrdinalSuffix(6).equals("6th"));
        assertTrue(KycUtils.appendOrdinalSuffix(7).equals("7th"));
        assertTrue(KycUtils.appendOrdinalSuffix(8).equals("8th"));
        assertTrue(KycUtils.appendOrdinalSuffix(9).equals("9th"));
        assertTrue(KycUtils.appendOrdinalSuffix(10).equals("10th"));
        assertTrue(KycUtils.appendOrdinalSuffix(11).equals("11th"));
        assertTrue(KycUtils.appendOrdinalSuffix(12).equals("12th"));
        assertTrue(KycUtils.appendOrdinalSuffix(13).equals("13th"));
        assertTrue(KycUtils.appendOrdinalSuffix(14).equals("14th"));
        assertTrue(KycUtils.appendOrdinalSuffix(20).equals("20th"));
        assertTrue(KycUtils.appendOrdinalSuffix(21).equals("21st"));
        assertTrue(KycUtils.appendOrdinalSuffix(22).equals("22nd"));
        assertTrue(KycUtils.appendOrdinalSuffix(23).equals("23rd"));
        assertTrue(KycUtils.appendOrdinalSuffix(31).equals("31st"));
        assertTrue(KycUtils.appendOrdinalSuffix(32).equals("32nd"));
        assertTrue(KycUtils.appendOrdinalSuffix(33).equals("33rd"));
        assertTrue(KycUtils.appendOrdinalSuffix(Integer.MAX_VALUE).equals("2147483647th"));

        // "Zeroth" is not common, but it is supposedly correct. In a real world
        // situation, I would have qualified this edge case with the product owner to
        // make sure this is an acceptable value
        assertTrue(KycUtils.appendOrdinalSuffix(0).equals("0th"));
    }

    /**
     * Error case for the ordinal indicator processing function:
     * negative numbers are not supported
     */
    @Test(expected = NegativeIntegerException.class)
    public void testInvalidNumOrdinalSuffix() throws NegativeIntegerException {
        KycUtils.appendOrdinalSuffix(-1);
    }
}
