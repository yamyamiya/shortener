package com.example.shortener.controllers;

import com.example.shortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LinkController {
    @Autowired
    private LinkService service;

    @GetMapping(value = "/")
    public String home(Model model) {
        return "home";
    }

    @PostMapping("/")
    public String shortenUrl(@RequestParam("longUrl") String longUrl, Model model) {
        model.addAttribute("shortUrl", service.shortenUrl(longUrl));
        return "short_url";
    }

    @GetMapping("/{shortUrl}")
    public ModelAndView redirectToOriginalUrl(@PathVariable String shortUrl) {
        return new ModelAndView(service.redirectToOriginalUrl(shortUrl));
    }
}
