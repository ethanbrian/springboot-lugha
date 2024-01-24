package com.example.demo.dto;

import com.example.demo.dto.Language;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class LanguageDeserializer extends JsonDeserializer<Language> {

    @Override
    public Language deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();
        String abbreviation = node.get("abbreviation").asText();
        String source_abbreviation = node.get("source_abbreviation").asText();
        String language_name = node.get("language_name").asText();
        int language_id = node.get("language_id").asInt();
        int id = node.get("id").asInt();
        int deletion_status = node.get("deletion_status").asInt();
        return new Language(language_id,deletion_status,name,id,abbreviation,language_name, source_abbreviation);
    }
}
