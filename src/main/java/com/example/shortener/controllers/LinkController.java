package com.example.shortener.controllers;

import com.example.shortener.entity.ui.SubmitForm;
import com.example.shortener.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LinkController {
    @Autowired
    private LinkService service;

    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("submitForm", new SubmitForm());
        return "home";
    }

    @PostMapping("/")
    public String shortenUrl(@Valid @ModelAttribute("submitForm") SubmitForm submitForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "home";
        }
        model.addAttribute("shortUrl", service.shortenUrl(submitForm.getUrl()));
        return "short_url";
    }

    @GetMapping("/{shortUrl}")
    public ModelAndView redirectToOriginalUrl(@PathVariable String shortUrl) {
        return new ModelAndView(service.redirectToOriginalUrl(shortUrl));
    }
}
