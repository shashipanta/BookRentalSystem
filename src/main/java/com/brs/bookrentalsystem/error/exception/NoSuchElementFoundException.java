package com.brs.bookrentalsystem.error.exception;


import com.brs.bookrentalsystem.error.codes.ErrorCodes;

public class NoSuchElementFoundException extends RuntimeException  {

    private ErrorCodes errorCode;

    public NoSuchElementFoundException(ErrorCodes errorCode, String reason){
        super(reason);
        this.errorCode = errorCode;
    }
}
