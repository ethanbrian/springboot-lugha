package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "translation_new")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id")
    private Long translationId;

    @Column(name = "source_text")
    private String sourceText;

    @Column(name = "translated_text")
    private String translatedText;

    @ManyToOne
    @JoinColumn(name = "language_id")  // Adjusted to match the column name in the database
    private Language language;

    @Column(name = "reviewed_translation")
    private String reviewedTranslation;

    @ManyToOne
    @JoinColumn(name = "source_language_id")  // Adjusted to match the column name in the database
    private Language sourceLanguage;  // New field for source language

    // Constructors, getters, and setters

    // Constructor
    public Translation() {
    }

    // Getters and setters
    public Long getTranslationId() {
        return translationId;
    }

    public void setTranslationId(Long translationId) {
        this.translationId = translationId;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getReviewedTranslation() {
        return reviewedTranslation;
    }

    public void setReviewedTranslation(String reviewedTranslation) {
        this.reviewedTranslation = reviewedTranslation;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }
}
