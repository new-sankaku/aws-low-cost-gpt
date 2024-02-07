package com.yksc.model.db.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class DateConverter implements DynamoDBTypeConverter<String, Date> {

    private static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final SimpleDateFormat dateFormat;

    public DateConverter() {
        dateFormat = new SimpleDateFormat(ISO_8601_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public String convert(Date object) {
        synchronized (dateFormat) {
            return dateFormat.format(object); // Date:ISO 8601
        }
    }

    @Override
    public Date unconvert(String object) {
        synchronized (dateFormat) {
            try {
                return dateFormat.parse(object); // Date:ISO 8601
            } catch (ParseException e) {
                throw new RuntimeException("Unable to parse date string: " + object, e);
            }
        }
    }
}
