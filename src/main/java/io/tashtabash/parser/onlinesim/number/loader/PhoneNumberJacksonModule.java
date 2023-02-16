package io.tashtabash.parser.onlinesim.number.loader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.tashtabash.parser.onlinesim.number.dao.PhoneNumber;

import java.io.IOException;

public class PhoneNumberJacksonModule extends SimpleModule {
    public PhoneNumberJacksonModule() {
        addDeserializer(PhoneNumber.class, new PhoneNumberJacksonDeserializer());
    }

    private static class PhoneNumberJacksonDeserializer extends JsonDeserializer<PhoneNumber> {

        @Override
        public PhoneNumber deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode numberJson = jsonParser.getCodec().readTree(jsonParser);

            int countryId = numberJson.get("country").asInt();
            String number = numberJson.get("number").asText();
            String description = numberJson.get("data_humans").asText();

            return new PhoneNumber(number, countryId, description);
        }
    }
}
