package com.vinkas.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Vinoth on 13-5-16.
 */
public class DateTimePickerDialog implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        dateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
        if(!isTimeSet)
            getTimePickerDialog().show();
    }

    private boolean isTimeSet = false;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //isTimeSet = true;
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        timeSetListener.onTimeSet(view, hourOfDay, minute);
    }

    public void show() {
        getDatePickerDialog().show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    public DateTimePickerDialog(Context context, DatePickerDialog.OnDateSetListener dateSetListener, TimePickerDialog.OnTimeSetListener timeSetListener) {
        calendar = Calendar.getInstance();
        this.dateSetListener = dateSetListener;
        this.timeSetListener = timeSetListener;
        setTimestamp(System.currentTimeMillis());
        setDatePickerDialog(new DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
        setTimePickerDialog(new TimePickerDialog(context, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false));
    }

    public DateTimePickerDialog(Context context, DatePickerDialog.OnDateSetListener dateSetListener, TimePickerDialog.OnTimeSetListener timeSetListener, long timestamp) {
        calendar = Calendar.getInstance();
        this.dateSetListener = dateSetListener;
        this.timeSetListener = timeSetListener;
        setTimestamp(timestamp);
        setDatePickerDialog(new DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
        setTimePickerDialog(new TimePickerDialog(context, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false));
    }

    private Calendar calendar;

    public Calendar getCalendar() {
        return calendar;
    }

    public long getTimestamp() {
        return calendar.getTimeInMillis();
    }

    protected void setTimestamp(long timestamp) {
        calendar.setTimeInMillis(timestamp);
    }

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    protected void setDatePickerDialog(DatePickerDialog datePickerDialog) {
        this.datePickerDialog = datePickerDialog;
    }

    public TimePickerDialog getTimePickerDialog() {
        return timePickerDialog;
    }

    protected void setTimePickerDialog(TimePickerDialog timePickerDialog) {
        this.timePickerDialog = timePickerDialog;
    }
}
