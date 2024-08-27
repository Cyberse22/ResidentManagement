package com.nhom13.formatters;

import com.nhom13.pojo.ElectronicLocker;
import com.nhom13.pojo.Resident;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class ResidentFormatter  implements Formatter<Resident> {
    @Override
    public Resident parse(String text, Locale locale) throws ParseException {
        return new Resident(Integer.valueOf(text));
    }

    @Override
    public String print(Resident object, Locale locale) {
        return object.getId().toString();
    }
}