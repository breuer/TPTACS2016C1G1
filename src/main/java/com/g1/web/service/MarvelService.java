package com.g1.web.service;

import com.g1.web.model.Character;

import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;



public class MarvelService {
    private static String urlprefix = "http://gateway.marvel.com/";
    private static String version = "v1";
    private static String urlsufix = "?ts={ts}&apikey={publicKey}&hash={hash}";
    private static String publicKey = "c87152306558584d78fa129dcf252cde";
    private static String privateKey = "cbdbd8b46fd5da03f46483ada1ea246feab17fcb";

    public static String apiKey(){
        return publicKey;
    }

    private static String getCompleteURL(String inputStr){
        String url = urlprefix;
        url = url.concat(version);
        url = url.concat("/public");
        //http://gateway.marvel.com/v1/public
        url = url.concat(inputStr);
        url = url.concat(urlsufix);
        //http://gateway.marvel.com/v1/public/inputStr?...
        return url;
    }

    public static String dummyTs(){
        return "1"; //dummy time stamp
    }

    public static String hashedString(String inputTs){
        String concatStr = inputTs;
        concatStr = concatStr.concat(privateKey);
        concatStr = concatStr.concat(publicKey);
        return Hashing.MD5(concatStr);
    }

    public static String charactersURL(){
        return getCompleteURL("/characters");
    }

    public static List<Character> getAllCharacters(){
        RestTemplate restTemplate = new RestTemplate();
        String jsonStr = restTemplate.getForObject(charactersURL(),
                                                  String.class,
                                                  dummyTs(),
                                                  apiKey(),
                                                  hashedString(dummyTs()));

        return getCharactersFromJsonStr(jsonStr);
    }

    private static List<Character> getCharactersFromJsonStr(String JsonStr){
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