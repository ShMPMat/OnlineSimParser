package io.tashtabash.parser.onlinesim.number.loader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.tashtabash.parser.onlinesim.number.dao.Country;

import java.io.IOException;

public class CountryJacksonModule extends SimpleModule {
    public CountryJacksonModule() {
        addDeserializer(Country.class, new CountryJacksonDeserializer());
    }

    private static class CountryJacksonDeserializer extends JsonDeserializer<Country> {

        @Override
        public Country deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode countryJson = jsonParser.getCodec().readTree(jsonParser);

            int id = countryJson.get("country").asInt();
            String name = countryJson.get("country_text").asText();

            return new Country(id, name);
        }
    }
}
