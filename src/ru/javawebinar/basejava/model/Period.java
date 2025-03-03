package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String post;
    private final String description;


    public Period(LocalDate fromDate, LocalDate toDate, String post, String description) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.post = post;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return fromDate.equals(period.fromDate) && toDate.equals(period.toDate) && post.equals(period.post) && description.equals(period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate, post, description);
    }

    @Override
    public String toString() {
        return fromDate +  " - " + toDate + " " + post + '\n' +
                "                     " + description;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getPost() {
        return post;
    }

    public String getDescription() {
        return description;
    }
}
