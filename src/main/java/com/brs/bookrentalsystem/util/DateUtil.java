package com.brs.bookrentalsystem.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String dateToString(LocalDate date){
            return dateFormatter.format(date);
    }

    public LocalDate stringToDate(String dateString){
        return LocalDate.parse(dateString, dateFormatter);
    }

    public LocalDate getCurrentDate(DateTimeFormatter dateFormatter){
        return LocalDate.parse(LocalDate.now().format(dateFormatter));
    }

    public LocalDate addDayToDate(Integer dayToAdd){
        return getCurrentDate(dateFormatter).plusDays(dayToAdd);
    }

}
