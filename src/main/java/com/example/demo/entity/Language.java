package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "languages_new")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Adjusted to match the column name in the database
    private Long id;

    @Column(name = "deletion_status")
    private Integer deletionStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "language_name")
    private String languageName;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "source_abbreviation")
    private String sourceAbbreviation;

    // Constructors, getters, and setters

    public Language() {
    }

    public Language(Integer deletionStatus, String name, String languageName, String abbreviation, String sourceAbbreviation) {
        this.deletionStatus = deletionStatus;
        this.name = name;
        this.languageName = languageName;
        this.abbreviation = abbreviation;
        this.sourceAbbreviation = sourceAbbreviation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeletionStatus() {
        return deletionStatus;
    }

    public void setDeletionStatus(Integer deletionStatus) {
        this.deletionStatus = deletionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSourceAbbreviation() {
        return sourceAbbreviation;
    }

    public void setSourceAbbreviation(String sourceAbbreviation) {
        this.sourceAbbreviation = sourceAbbreviation;
    }
}
