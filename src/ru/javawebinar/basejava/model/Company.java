package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Company {
    private final String name;
    private final String url;
    private final List<Period> periods;

    public Company(String name, String url, List<Period> periods) {
        this.name = name;
        this.url = url;
        this.periods = periods;

    }

    @Override
    public String toString() {
        return name + '\n' + periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return name.equals(company.name) && url.equals(company.url) && periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, periods);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
