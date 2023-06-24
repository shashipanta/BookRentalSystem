package com.brs.bookrentalsystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomAlphaNumericString {

    public String generateRandomString(int length) {

        // ASCII values of [0 - 9] : 48 to 57
        // ASCII values of [A - Z] : 65 to 90
        // ASCII values of [a - z] : 97 to 122

        int lowerBound = 48;        //  '0'
        int upperBound = 122;        //  'z'

        Random random = new Random();

        String alphaNumericString = random.ints(lowerBound, upperBound + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println("AlphaNumeric String : " + alphaNumericString);

        return alphaNumericString;
    }
}
