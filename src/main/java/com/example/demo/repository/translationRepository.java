package com.example.demo.repository;

import com.example.demo.entity.Translation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface translationRepository extends CrudRepository<Translation, Long> {

    @Query("SELECT t, s.name AS sourceName, s.languageName AS sourceLanguageName, s.sourceAbbreviation AS sourceAbbreviation, " +
            "l.name AS targetName, l.languageName AS targetLanguageName, l.abbreviation AS targetAbbreviation, " +
            "t.translatedText " +
            "FROM Translation t " +
            "JOIN Language s ON t.sourceLanguage.id = s.id " +
            "JOIN Language l ON t.language.id = l.id " +
            "WHERE t.translationId = :translationId AND s.id = :languageId")
    Translation findTranslationWithLanguage(@Param("translationId") Long translationId, @Param("languageId") Long languageId);

    @Modifying
    @Query("UPDATE Translation t SET t.translatedText = :translatedText WHERE t.translationId = :translationId")
    void updateTranslatedText(@Param("translationId") Long translationId, @Param("translatedText") String translatedText);


}

