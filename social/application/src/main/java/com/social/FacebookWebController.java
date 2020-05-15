package com.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/api/webhook")
public class FacebookWebController {

    private RestTemplate restTemplate = new RestTemplate();

    private void sendMessage()
    {
        String personJsonObject = "{\"messaging_type\":\"RESPONSE\",\"recipient\":{\"id\":\"3107955045931308\"},\"message\":{\"text\":\"hello, world!\"}}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=utf-8");
        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), httpHeaders);

        String personResultAsJsonStr =
                restTemplate.postForObject("https://graph.facebook.com/v7.0/me/messages?access_token=EAAQoZAkSgQFwBABPtB6L7Ggg6QZCDQPj26pZCO688t12AUWUr5rfgo9dNE0g6PRGlxLOIV6Hog8cQkzjWhLiuVewdAthY3oZBZCZBzeVVkU1sEZBA0isZCgPs5vqoRlcFVokFhr4OnR4luUvvOo8e0UoGqFUHMD5eR5thdWiBt7jPENYwR5sEC93QuplKPGPvTkZD", request, String.class);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> sendChat(@RequestBody String jsonPayload, @RequestHeader HttpHeaders headers)
    {
        System.out.println(jsonPayload);
        sendMessage();
        return new ResponseEntity<String>("sent chat", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> subscribe(@RequestHeader HttpHeaders headers, @RequestParam(value = "hub.mode") String hubMode,
                                            @RequestParam(value = "hub.challenge") String hubChallenge, HttpServletRequest request) {

        return new ResponseEntity<String>(hubChallenge, HttpStatus.OK);
    }
}
