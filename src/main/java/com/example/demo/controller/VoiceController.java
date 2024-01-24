package com.example.demo.controller;

import com.example.demo.dto.Language;
import com.example.demo.dto.SourceLanguage;
import com.example.demo.entity.Translation;
import com.example.demo.service.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.crypto.SecretKey;
import java.util.*;

public class VoiceController {

    private final Logger logger = LoggerFactory.getLogger(TranslationController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final TranslationService translationService;

    @Autowired
    public VoiceController(TranslationService translationService) {
        this.translationService = translationService;
    }


    @PostMapping("/translate_voice")
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

        String generatedTranslationId = String.valueOf(translationService.saveVoice(sourceText, targetLanguageId, sourceLanguageId));

        // Return the generated translation ID in the response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("translationId", generatedTranslationId);
        responseData.put("languageId", sourceLanguageId); // Assuming you want to include sourceLanguageId

        // Log the response data using JSON serialization
        logger.info("Response data: {}", responseData);

        // Return the generated translation ID in the response
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/save_voice")
    public ResponseEntity<String> saveText(@RequestBody Map<String, Object> payload) {
        String sourceText = (String) payload.get("sourceText");
        int targetLanguageId = (int) payload.get("targetLanguageId");
        int sourceLanguageId = (int) payload.get("sourceLanguageId");

        // Insert the new record and get the generated translationId
        long translationId = translationService.saveVoice(sourceText, targetLanguageId, sourceLanguageId);

        // Return the generated translationId in the response
        return new ResponseEntity<>("" + translationId, HttpStatus.OK);
    }






//    @PostMapping("/save_review")
//    public String saveReviewedTranslation(@RequestBody String reviewedTranslation) {
//        // Implement the logic to save the reviewed translation to the database
//        translationService.saveReviewedTranslation(reviewedTranslation);
//        return "Reviewed translation saved successfully";
//    }




}
