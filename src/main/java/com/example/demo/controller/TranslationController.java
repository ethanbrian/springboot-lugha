package com.example.demo.controller;

import com.example.demo.dto.Language;
import com.example.demo.dto.SourceLanguage;
import com.example.demo.dto.TranslationDTO;
import com.example.demo.entity.Translation;
import com.example.demo.service.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;


@RestController
public class TranslationController {

    private final Logger logger = LoggerFactory.getLogger(TranslationController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping("/get_source_text")
    public String getSourceText() {
        // Implement the logic to retrieve the source text from the database
        return translationService.getSourceText();
    }

    @PostMapping("/translate_text")
    public ResponseEntity<Map<String, Object>> postSourceText(@RequestBody Map<String, Object> requestBody) {
        String sourceText = (String) requestBody.get("sourceText");
        Object targetLanguageObj = requestBody.get("targetLanguageId");
        Object sourceLanguageObj = requestBody.get("sourceLanguageId");


        if (sourceText == null || targetLanguageObj == null || sourceLanguageObj == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid request body"));
        }

        int targetLanguageId;
        int sourceLanguageId;

        try {
            targetLanguageId = Integer.parseInt(targetLanguageObj.toString());
            sourceLanguageId = Integer.parseInt(sourceLanguageObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Invalid language ID format"));
        }

        String generatedTranslationId = String.valueOf(translationService.saveText(sourceText, targetLanguageId, sourceLanguageId));

        // Return the generated translation ID in the response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("translationId", generatedTranslationId);
        responseData.put("languageId", sourceLanguageId); // Assuming you want to include sourceLanguageId

        // Log the response data using JSON serialization
        logger.info("Response data: {}", responseData);

        // Return the generated translation ID in the response
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/save_text")
    public ResponseEntity<String> saveText(@RequestBody Map<String, Object> payload) {
        String sourceText = (String) payload.get("sourceText");
        int targetLanguageId = (int) payload.get("targetLanguageId");
        int sourceLanguageId = (int) payload.get("sourceLanguageId");

        // Insert the new record and get the generated translationId
        long translationId = translationService.saveText(sourceText, targetLanguageId, sourceLanguageId);

        // Return the generated translationId in the response
        return new ResponseEntity<>("" + translationId, HttpStatus.OK);
    }

    @GetMapping("/get_target_languages")
    public String getTargetLanguages() {
        List<Language> targetLanguages = new ArrayList<>();
        targetLanguages.add(new Language(1, 0, "English", 1, "English", "en", "en"));
        targetLanguages.add(new Language(2, 0, "Kikuyu", 2, "Kikuyu", "kik", "kik"));
        targetLanguages.add(new Language(3, 0, "Kimeru", 3, "Kimeru", "mer", "mer"));
        targetLanguages.add(new Language(4, 0, "Luo", 4, "Luo", "luo", "luo"));
        targetLanguages.add(new Language(5, 0, "Somali", 5, "Somali", "som", "som"));
        targetLanguages.add(new Language(6, 0, "Swahili", 6, "Swahili", "swa", "swa"));


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(targetLanguages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }



    @GetMapping("/get_source_languages")
    public String getSourceLanguages() {
        List<SourceLanguage> sourceLanguages = new ArrayList<>();
        sourceLanguages.add(new SourceLanguage(1, 0, "English", "English", "en", "en"));
        sourceLanguages.add(new SourceLanguage(2, 0, "Kikuyu", "Kikuyu", "kik", "kik"));
        sourceLanguages.add(new SourceLanguage(3, 0, "Kimeru", "Kimeru", "mer", "mer"));
        sourceLanguages.add(new SourceLanguage(4, 0, "Luo", "Luo", "luo", "luo"));
        sourceLanguages.add(new SourceLanguage(5, 0, "Somali","Somali", "som", "som"));
        sourceLanguages.add(new SourceLanguage(6, 0, "Swahili","Swahili", "swa", "swa"));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(sourceLanguages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }
    @PostMapping("/get_target_language")
    public String getTargetLanguage() {
        // Implement the logic to retrieve the source language from the database
        return translationService.getTargetLanguage();
    }

//    @PostMapping("/save_review")
//    public String saveReviewedTranslation(@RequestBody String reviewedTranslation) {
//        // Implement the logic to save the reviewed translation to the database
//        translationService.saveReviewedTranslation(reviewedTranslation);
//        return "Reviewed translation saved successfully";
//    }

    @GetMapping("/generate_token")
    public String generateToken() {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject("authentication")
                .signWith(key)
                .compact();
    }
    // Service class
//    public Translation fetchTranslation(Long translationId, Long languageId) {
//        // Implement logic to fetch the Translation object with language details from the database
//        return translationRepository.findTranslationWithLanguage(translationId, languageId);
//    }

    // Controller class
    @PostMapping("/get_translation_details")
    public ResponseEntity<Map<String, String>> getTranslationDetails(@RequestBody Map<String, Long> requestBody) {
        try {
            Long translationId = requestBody.get("translationId");
            Long languageId = requestBody.get("languageId");

            String endpointUrl = String.format("/get_translation_details/%d/%d", translationId, languageId);
            Translation translation = translationService.fetchTranslation(translationId, languageId);

            if (translation != null) {
                logger.info("Translation found: {}", translation);
                Map<String, String> response = new HashMap<>();
                response.put("source_text", translation.getSourceText());
                response.put("source_name", translation.getSourceLanguage().getName());
                response.put("source_language_name", translation.getSourceLanguage().getLanguageName());
                response.put("source_abbreviation", translation.getSourceLanguage().getSourceAbbreviation());
                response.put("target_name", translation.getLanguage().getName());
                response.put("translated_text", translation.getTranslatedText());
                response.put("translation_id", String.valueOf(translation.getTranslationId()));
                response.put("target_abbreviation", translation.getLanguage().getAbbreviation());

                return ResponseEntity.ok(response);
            } else {
                logger.info("No translation found for translationId: {} and languageId: {}", translationId, languageId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "No Source text and language found"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            logger.info("Exception occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    }


