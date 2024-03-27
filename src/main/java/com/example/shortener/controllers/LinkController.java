package com.example.shortener.controllers;

import com.example.shortener.entity.Link;
import com.example.shortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class LinkController {
    @Autowired
    private LinkService service;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/shorten")
    public Map<String, String> shortenUrl(@RequestBody Map<String, String> requestBody) {
        return service.shortenUrl(requestBody);
    }

    @GetMapping("/{shortUrl}")
    public ModelAndView redirectToOriginalUrl(@PathVariable String shortUrl) {
        return new ModelAndView(service.redirectToOriginalUrl(shortUrl));
    }

}
