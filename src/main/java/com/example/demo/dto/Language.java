package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonSerialize(using = LanguageSerializer.class)
@JsonDeserialize(using = LanguageDeserializer.class)
public class Language {
    private long language_id; // Rename to match the schema
    private int deletion_status;
    private String name;
    private long id;
    private String language_name; // Rename to match the schema
    private String abbreviation;
    private String source_abbreviation;



    public Language(long language_id, int deletion_status, String name, long id, String language_name,
                    String abbreviation, String source_abbreviation) {
        this.language_id = language_id;
        this.deletion_status = deletion_status;
        this.name = name;
        this.id = id;
        this.language_name = language_name;
        this.abbreviation = abbreviation;
        this.source_abbreviation = source_abbreviation;
    }



    // Getters and setters for the fields

    public long getLanguageId() {
        return language_id;
    }

    public void setLanguageId(long language_id) {
        this.language_id = language_id;
    }

    public int getDeletionStatus() {
        return deletion_status;
    }

    public void setDeletionStatus(int deletion_status) {
        this.deletion_status = deletion_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguageName() {
        return language_name;
    }

    public void setLanguageName(String language_name) {
        this.language_name = language_name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSourceAbbreviation() {
        return source_abbreviation;
    }

    public void setSourceAbbreviation(String source_abbreviation) {
        this.source_abbreviation = source_abbreviation;
    }

    // Additional methods and logic for the Language class
}
