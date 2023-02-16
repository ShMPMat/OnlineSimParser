package io.tashtabash.parser.onlinesim.number.dao;


public class PhoneNumber {
    private final String number;

    private final int countryId;

    private final String description;

    public PhoneNumber(String number, int countryId, String description) {
        this.number = number;
        this.countryId = countryId;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
