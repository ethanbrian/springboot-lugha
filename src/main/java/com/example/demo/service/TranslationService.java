package com.example.demo.service;

import com.example.demo.dto.Language;
import com.example.demo.dto.SourceLanguage;
import com.example.demo.entity.Translation;
import com.example.demo.repository.translationRepository;
import com.example.demo.utils.DatabaseConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);
    private final translationRepository translationRepository;


    @Autowired
    public TranslationService(translationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @PostMapping("/postSourceText")
    public ResponseEntity<String> postSourceText(@RequestBody Map<String, Object> requestBody) {
        try {
            String sourceText = (String) requestBody.get("sourceText");
            int targetLanguageId = (int) requestBody.get("targetLanguageId");
            int sourceLanguageId = (int) requestBody.get("sourceLanguageId");

            // Add your logic to insert data into the database
            saveText(sourceText, targetLanguageId, sourceLanguageId);

            return ResponseEntity.ok("Data received and processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the data");
        }
    }

    public long saveText(String sourceText, int targetLanguageId, int sourceLanguageId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO translation_new (source_text, language_id, source_language_id) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, sourceText);
            statement.setInt(2, targetLanguageId);
            statement.setInt(3, sourceLanguageId);

            // Log SQL query and parameters
            log.debug("Executing SQL query: {}", statement.toString());

            statement.executeUpdate();

            // Retrieve the generated translationId
            var resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            throw new SQLException("Error retrieving generated keys");
        } catch (SQLException se) {
            log.error("Error executing SQL query", se);
            return -1; // or handle the error as needed
        }
    }

    public long saveVoice(String sourceText, int targetLanguageId, int sourceLanguageId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO voice_new (source_text, language_id, source_language_id) VALUES (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, sourceText);
            statement.setInt(2, targetLanguageId);
            statement.setInt(3, sourceLanguageId);

            // Log SQL query and parameters
            log.debug("Executing SQL query: {}", statement.toString());

            statement.executeUpdate();

            // Retrieve the generated translationId
            var resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }

            throw new SQLException("Error retrieving generated keys");
        } catch (SQLException se) {
            log.error("Error executing SQL query", se);
            return -1; // or handle the error as needed
        }
    }

//    public void insertSourceText(String sourceText, int targetLanguageId, int sourceLanguageId) {
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(
//                     "INSERT INTO translation_new (source_text, language_id, source_language_id) VALUES (?, ?, ?)")) {
//            statement.setString(1, sourceText);
//            statement.setInt(2, targetLanguageId);
//            statement.setInt(3, sourceLanguageId);
//            statement.executeUpdate();
//        } catch (SQLException se) {
//            se.printStackTrace();
//            System.out.println("SQL State: " + se.getSQLState());
//            System.out.println("Error Code: " + se.getErrorCode());
//            System.out.println("Message: " + se.getMessage());
//        }
//    }

    public void saveTranslation(Translation translation) {
        translationRepository.save(translation);
    }

    public Translation fetchTranslation(Long translationId, Long languageId) {
        log.info("Fetching translation for translationId: {} and languageId: {}", translationId, languageId);
        return translationRepository.findTranslationWithLanguage(translationId, languageId);
    }

    public String getSourceText() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT source_text FROM translation_new WHERE id = ?");
            if (resultSet.next()) {
                return resultSet.getString("source_text");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public String getSourceLanguage() {
        List<SourceLanguage> sourceLanguages = getSourceLanguages();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(sourceLanguages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public List<SourceLanguage> getSourceLanguages() {
        List<SourceLanguage> languages = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM languages_new");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                int deletionStatus = resultSet.getInt("deletion_status");
                String name = resultSet.getString("name");
                String languageName = resultSet.getString("language_name");
                String abbreviation = resultSet.getString("abbreviation");
                String sourceAbbreviation = resultSet.getString("source_abbreviation");

                // Create a new SourceLanguage object using the constructor that takes all fields
                SourceLanguage sourceLanguage = new SourceLanguage(id, deletionStatus, name, languageName, abbreviation, sourceAbbreviation);

                languages.add(sourceLanguage);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return languages;
    }





    public String getTargetLanguage() {
        List<Language> languages = getTargetLanguages();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(languages);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public List<Language> getTargetLanguages() {
        List<Language> languages = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM languages_new");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String languageName = resultSet.getString("language_name");
                String abbreviation = resultSet.getString("abbreviation");
                String sourceAbbreviation = resultSet.getString("source_abbreviation");
                int languageId = resultSet.getInt("language_id");
                int id = resultSet.getInt("id");
                int deletion_status = resultSet.getInt("deletion_status");

                // Create a new Language object using the constructor that takes all fields
                languages.add(new Language(languageId, deletion_status,name,id, languageName, abbreviation, sourceAbbreviation));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return languages;
    }

}
