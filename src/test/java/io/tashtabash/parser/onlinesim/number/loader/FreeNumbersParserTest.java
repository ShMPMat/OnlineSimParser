package io.tashtabash.parser.onlinesim.number.loader;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.tashtabash.parser.onlinesim.number.dao.Country;
import io.tashtabash.parser.onlinesim.number.dao.FreeNumbers;
import io.tashtabash.parser.onlinesim.number.dao.PhoneNumber;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class FreeNumbersParserTest {
    FreeNumbersParser freeNumbersParser = new FreeNumbersParser();

    @Test
    void parseCountriesParsesCountries() throws ParseException, JsonProcessingException {
        Country[] countries = {
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        };

        List<Country> result =  freeNumbersParser.parseCountries("{\"response\":1,\"countries\":[" +
                "{\"country\":" + countries[0].getId() + ",\"country_text\":\"" + countries[0].getId() + "\"}," +
                "{\"country\":" + countries[1].getId() + ",\"country_text\":\"" + countries[1].getId() + "\"}," +
                "{\"country\":" + countries[2].getId() + ",\"country_text\":\"" + countries[2].getId() + "\"}" +
                "]}");

        assertThat(result).containsExactlyInAnyOrderElementsOf(Arrays.stream(countries).toList());
    }

    @Test
    void parseCountriesParsesEmptyCountries() throws ParseException, JsonProcessingException {
        List<Country> result =  freeNumbersParser.parseCountries("{\"response\":1,\"countries\":[]}");

        assertThat(result).isEmpty();
    }

    @Test
    void parseCountriesThrowsOnNonJsonInput() {
        assertThrows(
                Exception.class,
                () -> freeNumbersParser.parseCountries("No JSON")
        );
    }

    @Test
    void parseCountriesThrowsOnIncorrectJson() {
        assertThrows(
                Exception.class,
                () -> freeNumbersParser.parseCountries("{\"response\":1,\"countries\":[]")
        );
    }

    @Test
    void parseCountriesThrowsOnNoResponseCode() {
        assertThrows(
                Exception.class,
                () -> freeNumbersParser.parseCountries("{\"countries\":[]}")
        );
    }

    @Test
    void parseCountriesThrowsOnNon1ResponseCode() {
        assertThrows(
                ParseException.class,
                () -> freeNumbersParser.parseCountries("{\"response\":\"Some Error\",\"countries\":[]}")
        );
    }

    @Test
    void parseCountriesThrowsOnNon1IntegerResponseCode() {
        assertThrows(
                ParseException.class,
                () -> freeNumbersParser.parseCountries("{\"response\":0,\"countries\":[]}")
        );
    }

    @Test
    void parseFreeNumbersParsesFreeNumbers() throws ParseException, JsonProcessingException {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );
        PhoneNumber[] nums = {
                new PhoneNumber("12345", 1, "Description 1"),
                new PhoneNumber("+2345", 1, "Description 2"),
                new PhoneNumber("23456", 1111, "Описание")
        };

        FreeNumbers result = freeNumbersParser.parseFreeNumbers(countries, "{\"response\":1,\"numbers\":[" +
                "{\"number\":\"" + nums[0].getNumber() + "\",\"country\":" + nums[0].getCountryId() +"," +
                "    \"data_humans\":\"" + nums[0].getDescription() + "\"}," +
                "{\"number\":\"" + nums[1].getNumber() + "\",\"country\":" + nums[1].getCountryId() + "," +
                "    \"data_humans\":\"" + nums[1].getDescription() + "\"}," +
                "{\"number\":\"" + nums[2].getNumber() + "\",\"country\":" + nums[2].getCountryId() + "," +
                "    \"data_humans\":\"" + nums[2].getDescription() + "\"}" +
                "]}");
        Map<Country, Set<PhoneNumber>> expectedMap = Map.of(
                countries.get(0), Set.of(nums[0], nums[1]),
                countries.get(1), Set.of(),
                countries.get(2), Set.of(nums[2])
        );

        assertEquals(expectedMap, result.getCountryToNumbers());
    }

    @Test
    void parseFreeNumbersParsesEmptyFreeNumbers() throws ParseException, JsonProcessingException {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );
        Map<Country, Set<PhoneNumber>> expectedMap = Map.of(
                countries.get(0), Set.of(),
                countries.get(1), Set.of(),
                countries.get(2), Set.of()
        );

        FreeNumbers result = freeNumbersParser.parseFreeNumbers(countries, "{\"response\":1,\"numbers\":[]}");

        assertEquals(expectedMap, result.getCountryToNumbers());
    }

    @Test
    void parseFreeNumbersThrowsOnNonJsonInput() {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );

        assertThrows(
                Exception.class,
                () -> freeNumbersParser.parseFreeNumbers(countries, "No JSON")
        );
    }

    @Test
    void parseFreeNumbersThrowsOnIncorrectJson() {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );

        assertThrows(
                Exception.class,
                () -> freeNumbersParser.parseFreeNumbers(countries, "{\"response\":1,\"numbers\":[]")
        );
    }

    @Test
    void parseFreeNumbersThrowsOnNoResponseCode() {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );

        assertThrows(
                Exception.class,
                () -> freeNumbersParser.parseFreeNumbers(countries, "{\"numbers\":[]}")
        );
    }

    @Test
    void parseFreeNumbersThrowsOnNon1ResponseCode() {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );

        assertThrows(
                ParseException.class,
                () -> freeNumbersParser.parseFreeNumbers(countries, "{\"response\":\"Some Error\",\"numbers\":[]}")
        );
    }

    @Test
    void parseFreeNumbersThrowsOnNon1IntegerResponseCode() {
        List<Country> countries = List.of(
                new Country(1, "Name 1"),
                new Country(2, "2"),
                new Country(1111, "Текст")
        );

        assertThrows(
                ParseException.class,
                () -> freeNumbersParser.parseFreeNumbers(countries, "{\"response\":0,\"numbers\":[]}")
        );
    }
}
