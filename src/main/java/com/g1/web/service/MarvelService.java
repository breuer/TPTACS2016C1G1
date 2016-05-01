package com.g1.web.service;

import com.g1.web.model.Character;
import com.g1.config.MarvelConfig;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private String getCompleteURL(String inputStr){
        String url = marvelConfig.getUrlprefix();
        url = url.concat(marvelConfig.getApiversion());
        url = url.concat("/public");
        //http://gateway.marvel.com/v1/public
        url = url.concat(inputStr);
        url = url.concat(marvelConfig.getUrlsufix());
        //http://gateway.marvel.com/v1/public/inputStr?...
        return url;
    }

    public String dummyTs(){
        return "1"; //dummy time stamp
    }

    public String hashedString(String inputTs){
        String concatStr = inputTs;
        concatStr = concatStr.concat(marvelConfig.getPrivateKey());
        concatStr = concatStr.concat(marvelConfig.getPublicKey());
        return Hashing.MD5(concatStr);
    }

    public String charactersURL(){
        return getCompleteURL("/characters");
    }

    public List<Character> getAllCharacters(){
        RestTemplate restTemplate = new RestTemplate();
        String jsonStr = restTemplate.getForObject(charactersURL(),
                                                  String.class,
                                                  dummyTs(),
                                                  marvelConfig.getPublicKey(),
                                                  hashedString(dummyTs()));

        return getObjectsFromJsonStr(jsonStr);
    }

    private List<Character> getObjectsFromJsonStr(String JsonStr){
        List<Character> characters = new ArrayList<Character>();

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //read JSON like DOM Parser
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(JsonStr);
            JsonNode dataNode = rootNode.path("data");
            JsonNode resultsNode = dataNode.path("results");
            Iterator<JsonNode> elements = resultsNode.elements();
            while(elements.hasNext()){
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
}