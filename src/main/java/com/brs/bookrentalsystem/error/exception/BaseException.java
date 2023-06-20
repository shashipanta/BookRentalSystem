package com.brs.bookrentalsystem.error.exception;


import com.brs.bookrentalsystem.error.codes.ErrorCodes;

public class BaseException extends RuntimeException{

    private ErrorCodes errorCode;

    public BaseException(String message, ErrorCodes errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
