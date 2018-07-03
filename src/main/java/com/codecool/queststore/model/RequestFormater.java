package com.codecool.queststore.model;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestFormater {

    public Map getMapFromRequest(HttpExchange httpExchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String formData = bufferedReader.readLine();
        Map<String, String> formMap = parseFormData(formData);
        return formMap;
    }

    private Map parseFormData(String formData) {
        Map<String, String> formDataMap = new HashMap<>();
        String[] pairsPool = formData.split("&");
        int INDEX_KEY = 0;
        int INDEX_VALUE = 1;
        for (String record : pairsPool) {
            String[] keyAndValue = record.split("=");
            String value = null;
            try {
                value = new URLDecoder().decode(keyAndValue[INDEX_VALUE], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            formDataMap.put(keyAndValue[INDEX_KEY], value);
        }

        return formDataMap;
    }
}
