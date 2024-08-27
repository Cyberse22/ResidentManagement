package com.nhom13.formatters;

import org.springframework.format.Formatter;

import javax.ejb.Local;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        org.springframework.format.datetime.DateFormatter formatter = new org.springframework.format.datetime.DateFormatter();
        formatter.setPattern("yyyy-MM-dd");
        return formatter.parse(text, Locale.ENGLISH);
    }

    @Override
    public String print(Date object, Locale locale) {
        org.springframework.format.datetime.DateFormatter formatter = new org.springframework.format.datetime.DateFormatter();
        formatter.setPattern("yyyy-MM-dd");
        return formatter.print(object, Locale.ENGLISH);
    }
}
