package com.example.demo.dto;

public class TranslationDTO {
    private String sourceText;

    // Constructors
    public TranslationDTO(String sourceText) {
        this.sourceText = sourceText;
    }

    // Getters and setters
    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }
}
