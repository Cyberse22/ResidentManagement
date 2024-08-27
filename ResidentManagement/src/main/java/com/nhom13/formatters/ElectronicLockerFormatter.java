package com.nhom13.formatters;

import com.nhom13.pojo.ElectronicLocker;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class ElectronicLockerFormatter implements Formatter<ElectronicLocker> {
    @Override
    public ElectronicLocker parse(String text, Locale locale) throws ParseException {
        return new ElectronicLocker(Integer.valueOf(text));
    }

    @Override
    public String print(ElectronicLocker object, Locale locale) {
        return object.getId().toString();
    }
}
