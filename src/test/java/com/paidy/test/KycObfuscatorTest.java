package com.paidy.test;

import org.junit.Test;

import com.paidy.test.exceptions.UnsupportedPatternException;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Paidy string obfuscator class
 * All functions are static, so no need for concurrency tests
 */
public class KycObfuscatorTest {
  /**
   * Nominal test for the email obfuscator
   */
  @Test
  public void testObfuscateEmail() throws UnsupportedPatternException {
    assertTrue(KycObfuscator.obfuscatePersonalInfo("local-part@domain-name.com").equals("l*****t@domain-name.com"));
    assertTrue(KycObfuscator.obfuscatePersonalInfo("john.doe@gmail.com").equals("j*****e@gmail.com"));
    assertTrue(KycObfuscator.obfuscatePersonalInfo("a@gmail.com").equals("a*****a@gmail.com"));
    assertTrue(KycObfuscator.obfuscatePersonalInfo("LOCAL-PART@DOMAIN-NAME.COM").equals("l*****t@domain-name.com"));
  }

  /**
   * Error case #1: invalid mail address
   */
  @Test(expected = UnsupportedPatternException.class)
  public void testInvalidMailAddress() throws UnsupportedPatternException {
    KycObfuscator.obfuscatePersonalInfo("@paidy.com");
  }

  /**
   * Error case #2: invalid mail address
   */
  @Test(expected = UnsupportedPatternException.class)
  public void testInvalidMailAddress2() throws UnsupportedPatternException {
    KycObfuscator.obfuscatePersonalInfo("hello world@paidy.com");
  }

  /**
   * Nominal test for the phone obfuscator
   */
  @Test
  public void testObfuscatePhone() throws UnsupportedPatternException {
    assertTrue(KycObfuscator.obfuscatePersonalInfo("+33 6 12 34 56 78").equals("+**-*-**-**-56-78"));
    assertTrue(KycObfuscator.obfuscatePersonalInfo("+44 123 456 789").equals("+**-***-**6-789"));
    assertTrue(KycObfuscator.obfuscatePersonalInfo("0123456789").equals("******6789"));

    // Technically, as per the exercice's spec, this should be accepted
    // TODO: in a real world situation, I would have confirmed this edge case with
    // the PO (and maybe adapted the implementation to throw an exception here)
    assertTrue(
        KycObfuscator.obfuscatePersonalInfo("+44 123 456 789 876 543 210").equals("+**-***-***-***-***-**3-210"));
  }

  /**
   * Error case #1: input with a non-supported pattern
   * negative numbers are not supported
   */
  @Test(expected = UnsupportedPatternException.class)
  public void testInvalidPhoneNumber() throws UnsupportedPatternException {
    KycObfuscator.obfuscatePersonalInfo("+33 6 12 34 5");
  }

  /**
   * Error case #2: input with a non-supported pattern
   * negative numbers are not supported
   */
  @Test(expected = UnsupportedPatternException.class)
  public void testInvalidPhoneNumber2() throws UnsupportedPatternException {
    KycObfuscator.obfuscatePersonalInfo("33 6 12 34 56 78+");
  }

  /**
   * Error case #3: input with a non-supported pattern
   * negative numbers are not supported
   */
  @Test(expected = UnsupportedPatternException.class)
  public void testInvalidPhoneNumber3() throws UnsupportedPatternException {
    KycObfuscator.obfuscatePersonalInfo("+33 6 12 34 56 78+");
  }

}
