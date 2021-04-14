package com.nilcaream.activities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Definition {

    private final List<DayOfWeek> daysOfWeek;
    private final LocalTime from;
    private final LocalTime to;
    private final Pattern windowTitle;

    public Definition(List<DayOfWeek> daysOfWeek, LocalTime from, LocalTime to, Pattern windowTitle) {
        this.daysOfWeek = daysOfWeek;
        this.from = from;
        this.to = to;
        this.windowTitle = windowTitle;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add(daysOfWeek.stream().map(x -> x.getDisplayName(TextStyle.FULL, Locale.ENGLISH)).collect(Collectors.joining(",")))
                .add(from.format(DateTimeFormatter.ISO_LOCAL_TIME))
                .add("- " + to.format(DateTimeFormatter.ISO_LOCAL_TIME))
                .add(windowTitle.pattern())
                .toString();
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    public Pattern getWindowTitle() {
        return windowTitle;
    }
}
