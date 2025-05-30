package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    // pick appropriate instance variables to store the data necessary for this class
    private final JSONArray jsonArray;

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // use lines to populate the instance variable
            // tip: you might find it convenient to create an iterator using lines.iterator()
            this.jsonArray = new JSONArray();
            String[] headers = lines.get(0).split("\t");
            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split("\t");
                JSONObject obj = new JSONObject();
                for (int j = 0; j < headers.length && j < values.length; j++) {
                    obj.put(headers[j].trim(), values[j].trim());
                }
                jsonArray.put(obj);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // update this code to use your instance variable to return the correct value
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject obj = jsonArray.getJSONObject(i);
            if (code.equals(obj.getString("Code"))) {
                return obj.getString("ISO Language Names");
            }
        }
        return null;
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // update this code to use your instance variable to return the correct value
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (language.equals(obj.getString("ISO Language Names"))) {
                return obj.getString("Code");
            }
        }
        return null;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // update this code to use your instance variable to return the correct value
        return jsonArray.length();
    }
}
