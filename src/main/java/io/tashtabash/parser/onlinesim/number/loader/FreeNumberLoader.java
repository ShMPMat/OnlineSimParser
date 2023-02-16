package io.tashtabash.parser.onlinesim.number.loader;

import io.tashtabash.parser.onlinesim.number.dao.Country;
import io.tashtabash.parser.onlinesim.number.dao.FreeNumbers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

import static java.time.temporal.ChronoUnit.SECONDS;


public class FreeNumberLoader {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final FreeNumbersParser parser;

    public FreeNumberLoader(FreeNumbersParser parser) {
        this.parser = parser;
    }

    public FreeNumbers load() throws IOException, InterruptedException, ParseException {
        String countriesString = loadCountries();
        List<Country> countries = parser.parseCountries(countriesString);

        String freeNumbersString = loadFreeNumbers();

        return parser.parseFreeNumbers(countries, freeNumbersString);
    }

    private String loadCountries() throws IOException, InterruptedException {
        HttpRequest countriesRequest = HttpRequest.newBuilder(URI.create("https://onlinesim.ru/api/getFreeCountryList"))
                .GET()
                .timeout(Duration.of(10, SECONDS))
                .build();

        HttpResponse<String> countriesResponse = httpClient.send(countriesRequest, HttpResponse.BodyHandlers.ofString());

        return countriesResponse.body();
    }

    private String loadFreeNumbers() throws IOException, InterruptedException {
        HttpRequest countriesRequest = HttpRequest.newBuilder(URI.create("https://onlinesim.ru/api/getFreePhoneList"))
                .GET()
                .timeout(Duration.of(10, SECONDS))
                .build();

        HttpResponse<String> numbersResponse = httpClient.send(countriesRequest, HttpResponse.BodyHandlers.ofString());

        return numbersResponse.body();
    }
}
