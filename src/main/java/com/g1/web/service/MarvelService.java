package com.g1.web.service;

import com.g1.web.model.Character;
import com.g1.config.MarvelConfig;
import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class MarvelService {

    private MarvelConfig marvelConfig;

    public MarvelService() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/MarvelConfig.xml");
        marvelConfig = (MarvelConfig) applicationContext.getBean("MarvelConfig");
    }

    private String getCompleteURL(String inputStr) {
        String url = marvelConfig.getUrlprefix();
        url = url.concat(marvelConfig.getApiversion());
        url = url.concat("/public");
        //http://gateway.marvel.com/v1/public
        url = url.concat(inputStr);
        url = url.concat(marvelConfig.getUrlsufix());
        //http://gateway.marvel.com/v1/public/inputStr?...
        return url;
    }

    public String dummyTs() {
        return "1"; //dummy time stamp
    }

    public String hashedString(String inputTs) {
        String concatStr = inputTs;
        concatStr = concatStr.concat(marvelConfig.getPrivateKey());
        concatStr = concatStr.concat(marvelConfig.getPublicKey());
        return Hashing.MD5(concatStr);
    }

    public String charactersURL(boolean addOffset, boolean addLimit) {
        String charactersStr = "/characters";
        if (addLimit)
            charactersStr = charactersStr.concat("?&offset={offset}");

        if (addOffset)
            charactersStr = charactersStr.concat("?&limit={limit}");

        return getCompleteURL(charactersStr);
    }

    public List<Character> getAllCharacters() {

        List<Character> characters = new ArrayList<Character>();
        RestTemplate restTemplate = new RestTemplate();
        String jsonStr;
        int charactersAmt;
        int offset = 0;
        int limit = 20;
        DateTime timestamp;

        try {
            do {
                timestamp = new DateTime();
                jsonStr = restTemplate.getForObject(charactersURL(true, true),
                        String.class,
                        offset,
                        limit,
                        timestamp,
                        marvelConfig.getPublicKey(),
                        hashedString(timestamp.toString()));

                charactersAmt = getCountFromJsonStr(jsonStr);
                if (charactersAmt != 0) {
                    characters.addAll(getObjectsFromJsonStr(jsonStr));
                    offset += limit;
                }
            } while (charactersAmt != 0);
        } catch (HttpServerErrorException e) {
            //TODO: ver
            return characters;
        } catch (HttpClientErrorException e) {
            //TODO: ver
            return characters;
        }
        return characters;
    }

    public List<Character> getCharacters(int offset, int limit) {
        boolean addOffset = false;
        boolean addLimit = false;

        if (offset != 0) addOffset = true;
        if (limit != 0) addLimit = true;

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> vars = new HashMap<String, String>();
            if (addOffset)
                vars.put("offset", Integer.toString(offset));
            if (addLimit)
                vars.put("limit", Integer.toString(limit));
            vars.put ("ts", dummyTs());
            vars.put("publicKey", marvelConfig.getPublicKey());
            vars.put("hash", hashedString(dummyTs()));

            String jsonStr = restTemplate.getForObject(charactersURL(addOffset, addLimit),
                    String.class,
                    vars);
            return getObjectsFromJsonStr(jsonStr);
        } catch (HttpServerErrorException e) {
            throw e;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    private List<Character> getObjectsFromJsonStr(String JsonStr) {
        List<Character> characters = new ArrayList<Character>();

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            //read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(JsonStr);
            JsonNode dataNode = rootNode.path("data");
            JsonNode resultsNode = dataNode.path("results");
            Iterator<JsonNode> elements = resultsNode.elements();
            while (elements.hasNext()) {
                JsonNode result = elements.next();

                JsonNode thumbnail = result.path("thumbnail");
                characters.add(new Character(result.path("id").asLong(),
                        result.path("name").asText(),
                        result.path("description").asText(),
                        thumbnail.path("path").asText(),
                        thumbnail.path("extension").asText()));
            }
        } catch (JsonProcessingException e) {
            // Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return characters;
    }

    private int getCountFromJsonStr(String JsonStr) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        int count = -1;

        try {
            //read JSON like DOM Parser
            JsonNode rootNode = objectMapper.readTree(JsonStr);
            JsonNode dataNode = rootNode.path("data");
            count = dataNode.path("count").asInt();
        } catch (JsonProcessingException e) {
            // Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }
}