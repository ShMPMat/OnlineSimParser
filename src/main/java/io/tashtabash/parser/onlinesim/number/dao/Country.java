package io.tashtabash.parser.onlinesim.number.dao;


public class Country {
    private final int id;

    private final String name;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return id == country.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
