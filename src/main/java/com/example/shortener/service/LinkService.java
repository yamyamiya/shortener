package com.example.shortener.service;

import com.example.shortener.entity.Link;
import com.example.shortener.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class LinkService {
    @Autowired
    private LinkRepository linkRepository;

    private final String baseUrl = "http://localhost:8080/";

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newUrl = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            newUrl.append(characters.charAt(rand.nextInt(characters.length())));
        }
        return newUrl.toString();
    }

    public String redirectToOriginalUrl(String shortUrl) {
        Optional<Link> link = Optional.ofNullable(linkRepository.findByShortUrl(shortUrl));
        if (link.isPresent()) {
            return "redirect:" + link.get().getLongUrl();
        } else {
            return "Short URL is not found";
        }
    }

    public Map<String, String> shortenUrl(Map<String, String> requestBody) {
        String longUrl = requestBody.get("url");
        String shortUrl = generateShortUrl();
        Link link = new Link(shortUrl, longUrl);
        linkRepository.save(link);
        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", baseUrl + shortUrl);
        return response;
    }


}
