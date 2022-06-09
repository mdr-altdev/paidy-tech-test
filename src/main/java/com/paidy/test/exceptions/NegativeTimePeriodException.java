package com.paidy.test.exceptions;

public class NegativeTimePeriodException extends Exception {
    public NegativeTimePeriodException(String msg) {
        super(msg);
    }
}
