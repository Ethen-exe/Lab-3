package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved)

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    private final JSONArray jsonArray;
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    @SuppressWarnings("checkstyle:EmptyLineSeparator")
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            // pick appropriate instance variable(s) to store the data necessary for this class
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // use lines to populate the instance variable(s)
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
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        // update this code to use an instance variable to return the correct value
        String temp = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (code.toUpperCase().equals(obj.getString("Alpha-3 code"))) {
                temp = obj.getString("Country");
            }
        }
        return temp;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // update this code to use an instance variable to return the correct value
        String temp = "1";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (country.equals(obj.getString("Country"))) {
                temp = obj.getString("Alpha-3 code");
            }
        }
        return temp;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // update this code to use an instance variable to return the correct value
        return jsonArray.length();
    }
}
