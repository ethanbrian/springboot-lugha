package com.example.demo.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class LanguageSerializer extends JsonSerializer<Language> {

    @Override
    public void serialize(Language language, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", language.getName());
        jsonGenerator.writeStringField("language_name", language.getLanguageName());
        jsonGenerator.writeStringField("source_abbreviation", language.getSourceAbbreviation());
        jsonGenerator.writeStringField("abbreviation", language.getAbbreviation());
        jsonGenerator.writeNumberField("id", language.getId());
        jsonGenerator.writeEndObject();
    }
}

