package com.brs.bookrentalsystem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomAlphaNumericString {

    private Random random = new Random();

    private Logger logger = LoggerFactory.getLogger(RandomAlphaNumericString.class);

    public String generateRandomString(int length) {

        // ASCII values of [0 - 9] : 48 to 57
        // ASCII values of [A - Z] : 65 to 90
        // ASCII values of [a - z] : 97 to 122

        int lowerBound = 48;        //  '0'
        int upperBound = 122;        //  'z'

        String alphaNumericString = random.ints(lowerBound, upperBound + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        logger.info("Random AlphaNumeric String generated : {}", alphaNumericString);

        return alphaNumericString;
    }
}
