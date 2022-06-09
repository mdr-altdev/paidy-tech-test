package com.paidy.test;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.paidy.test.exceptions.UnsupportedPatternException;

public final class KycObfuscator {

  // Simple regex to match anything which looks like *@*.*. Assume that the input
  // string has already been sanitized (no SQL injections downstream)
  final static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
  // Phone number regex which implements the spec: at least 9 digits (0-9), and
  // (' ', '+'') where '+'' is only accepted when it is the first character.
  final static Pattern PHONE_PATTERN = Pattern.compile("^\\+?(\\d+\\s?){9,}");

  KycObfuscator() {
  }

  /**
   * Obfuscate a string - depending on the string pattern, the appropriate
   * strategy is chosen automatically
   * 
   * @param input String which needs to be obfuscated
   * @return Obfuscated string
   */
  public static String obfuscatePersonalInfo(String input) throws UnsupportedPatternException {
    ObfuscatingStrategy strat;

    if (EMAIL_PATTERN.matcher(input).matches()) {
      strat = ObfuscatingStrategy.EMAIL;
    } else if (PHONE_PATTERN.matcher(input).matches()) {
      strat = ObfuscatingStrategy.PHONE;
    } else {
      throw new UnsupportedPatternException("No obfuscation pattern implemented for " + input);
    }

    return strat.applyObfuscatingStrategy(input);
  }

  // Use an enum to implement the different obfuscation strategies. Extending the
  // obfuscator to support new strings (eg. address) can be made by adding new
  // values to the enum with the appropriate closure (+ the necessary regex to
  // match the case)
  enum ObfuscatingStrategy {

    EMAIL(input -> {
      // The output must be lowercase
      input = input.toLowerCase();
      String[] splt = input.split("@");
      // For the part before the "@", take the first and last char of the substring,
      // and add some * in the middle
      splt[0] = splt[0].substring(0, 1) + "*****" + splt[0].substring(splt[0].length() - 1, splt[0].length());
      return String.join("@", splt);
    }),
    PHONE(input -> {
      input = input.replace(" ", "-");
      // Obfuscate all digits, before looping to re-fill the last 4 digits
      String[] ret = input.replaceAll("\\d", "\\*").split("");
      int cpt = 0, i = 0;
      while (cpt < 4) {
        if (Character.isDigit(input.charAt(input.length() - i - 1))) {
          ret[input.length() - i - 1] = input.substring(input.length() - i - 1, input.length() - i);
          cpt += 1;
        }
        i++;
      }
      return String.join("", ret);
    }),
    ;

    private final UnaryOperator<String> strategy;

    ObfuscatingStrategy(UnaryOperator<String> strategy) {
      this.strategy = strategy;
    }

    String applyObfuscatingStrategy(String input) {
      return this.strategy.apply(input);
    }
  }

}
