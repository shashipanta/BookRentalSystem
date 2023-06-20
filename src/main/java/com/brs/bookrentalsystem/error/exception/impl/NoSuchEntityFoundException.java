package com.brs.bookrentalsystem.error.exception.impl;


import com.brs.bookrentalsystem.error.codes.ErrorCodes;
import com.brs.bookrentalsystem.error.exception.BaseException;

public class NoSuchEntityFoundException extends BaseException {

    public NoSuchEntityFoundException(String message, ErrorCodes errorCode) {
        super(message, errorCode);
    }
}
