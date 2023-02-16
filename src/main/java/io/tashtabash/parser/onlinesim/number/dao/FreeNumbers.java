package io.tashtabash.parser.onlinesim.number.dao;

import java.util.Map;
import java.util.Set;


public class FreeNumbers {
    private final Map<Country, Set<PhoneNumber>> countryToNumbers;

    public FreeNumbers(Map<Country, Set<PhoneNumber>> countryToNumbers) {
        this.countryToNumbers = countryToNumbers;
    }

    public Map<Country, Set<PhoneNumber>> getCountryToNumbers() {
        return countryToNumbers;
    }
}
