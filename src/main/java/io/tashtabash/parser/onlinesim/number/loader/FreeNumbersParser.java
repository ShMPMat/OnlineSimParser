package io.tashtabash.parser.onlinesim.number.loader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tashtabash.parser.onlinesim.number.dao.Country;
import io.tashtabash.parser.onlinesim.number.dao.FreeNumbers;
import io.tashtabash.parser.onlinesim.number.dao.PhoneNumber;

import java.util.*;


public class FreeNumbersParser {
    private final ObjectMapper jsonMapper;

    public FreeNumbersParser() {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new CountryJacksonModule());
        jsonMapper.registerModule(new PhoneNumberJacksonModule());
    }

    List<Country> parseCountries(String countriesString) throws JsonProcessingException, ParseException {
        List<Country> countries = new ArrayList<>();

        JsonNode countriesJson = jsonMapper.readTree(countriesString);
        checkResponseCode(countriesJson);

        Iterator<JsonNode> countriesIterator = countriesJson.get("countries").elements();
        while (countriesIterator.hasNext()) {
            JsonNode countryJson = countriesIterator.next();

            Country country = jsonMapper.readValue(countryJson.toString(), Country.class);
            countries.add(country);
        }

        return countries;
    }

    FreeNumbers parseFreeNumbers(List<Country> countries, String numbersString) throws JsonProcessingException, ParseException {
        Map<Country, Set<PhoneNumber>> countryToNumbers = new HashMap<>();
        for (var country: countries) {
            countryToNumbers.put(country, new HashSet<>());
        }

        JsonNode numbersJson = jsonMapper.readTree(numbersString);
        checkResponseCode(numbersJson);

        Iterator<JsonNode> numbersIterator = numbersJson.get("numbers").elements();
        while (numbersIterator.hasNext()) {
            JsonNode countryJson = numbersIterator.next();

            PhoneNumber phoneNumber = jsonMapper.readValue(countryJson.toString(), PhoneNumber.class);
            countryToNumbers.get(new Country(phoneNumber.getCountryId(), null)).add(phoneNumber);
        }
        return new FreeNumbers(countryToNumbers);
    }

    private void checkResponseCode(JsonNode responseJson) throws ParseException {
        String responseCode = responseJson.get("response").asText();
        if (!responseCode.equals("1")) {
            throw new ParseException("Unexpected response field for free phone list: " + responseCode);
        }
    }
}
