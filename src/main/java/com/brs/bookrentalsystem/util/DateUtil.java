package com.brs.bookrentalsystem.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class DateUtil {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter htmlDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyy");

    public String dateToString(LocalDate date){
            return dateFormatter.format(date);
    }

    public LocalDate stringToDate(String dateString){
        return LocalDate.parse(dateString, dateFormatter);
    }

    public LocalDate getCurrentDate(DateTimeFormatter dateFormatter){
        return LocalDate.parse(LocalDate.now().format(dateFormatter));
    }

    public String localDateToHtmlDateFormat(LocalDate date){
        return htmlDateFormatter.format(date);
    }

    public LocalDate formatLocalDate(LocalDate date){
        String passedDate = dateFormatter.format(date);

        return LocalDate.parse(passedDate);
    }

    public LocalDate addDayToDate(Integer dayToAdd){
        return getCurrentDate(dateFormatter).plusDays(dayToAdd);
    }



}
