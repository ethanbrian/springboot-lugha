package com.example.demo.dto;

public class SourceLanguage {
    private long id; // Change from language_id to id
    private int deletion_status;
    private String name;
    private String language_name; // Change from language_name to language_name
    private String abbreviation;
    private String source_abbreviation;

    public SourceLanguage(long id, int deletion_status, String name, String language_name, String abbreviation, String source_abbreviation) {
        this.id = id;
        this.deletion_status = deletion_status;
        this.name = name;
        this.language_name = language_name;
        this.abbreviation = abbreviation;
        this.source_abbreviation = source_abbreviation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDeletion_status() {
        return deletion_status;
    }

    public void setDeletion_status(int deletion_status) {
        this.deletion_status = deletion_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSource_abbreviation() {
        return source_abbreviation;
    }

    public void setSource_abbreviation(String source_abbreviation) {
        this.source_abbreviation = source_abbreviation;
    }
}
