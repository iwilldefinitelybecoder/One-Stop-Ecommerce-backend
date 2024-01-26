package com.Onestop.ecommerce.utils;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateObject {
    private Date date;
    private int count;

    public DateObject(Date date, int count) {
        this.date = date;
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "date=" + date +
                ", count=" + count +
                '}';
    }

    public JSONObject toJsonObject() {
        return new JSONObject().put("date", convertToLocalDate(date).toString()).put("count", count);
    }

    public LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
