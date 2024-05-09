package com.example.shortener.service;

import com.example.shortener.entity.Link;
import com.example.shortener.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class LinkServiceTest {
    private LinkRepository linkRepository;

    private LinkService linkService;

    @BeforeEach
    void init() {
        linkRepository = Mockito.mock();
        linkService = Mockito.spy(new LinkService(linkRepository));
    }


    @Test
    public void shouldCorrectlyGenerateShortUrl() {
        String shortUrl = linkService.generateShortUrl();
        assertNotNull(shortUrl);
        assertEquals(6, shortUrl.length());
        Pattern pattern = Pattern.compile("[A-Za-z0-9]+");
        assertTrue(pattern.matcher(shortUrl).matches());
    }

    @Test
    public void shouldRedirectToOriginalUrlWhenShotUrlIsExisting() {
        when(linkRepository.findByShortUrl("existingShortUrl")).thenReturn(new Link("existingShortUrl", "existingLongUrl"));
        String redirectUrl = linkService.redirectToOriginalUrl("existingShortUrl");
        assertEquals("redirect:existingLongUrl", redirectUrl);
    }

    @Test
    public void shouldShowCorrespondingMessageWhenShotUrlIsNotExisting() {
        when(linkRepository.findByShortUrl("NotExistingShortUrl")).thenReturn(null);
        String redirectUrl = linkService.redirectToOriginalUrl("NotExistingShortUrl");
        assertEquals("error", redirectUrl);
    }

    @Test
    public void shouldCorrectlyShortenUrl() {
        when(linkService.generateShortUrl()).thenReturn("rfg562");
        String longUrl = "https://example.com/longUrl";
        String shortendUrl = linkService.shortenUrl(longUrl);
        assertEquals("http://localhost:8080/rfg562", shortendUrl);
        verify(linkRepository).save(new Link("rfg562", longUrl));
    }

    @Test
    public void shouldReturnOriginalUrlWhenShotUrlIsExisting(){
        when(linkRepository.findByShortUrl("existingShortUrl")).thenReturn(new Link("existingShortUrl", "existingLongUrl"));
        String originalUrl = linkService.returnOriginalUrl("existingShortUrl");
        assertEquals("existingLongUrl", originalUrl);
    }

    @Test
    public void shouldShowErrorMessageWhenShotUrlIsNotExisting() {
        when(linkRepository.findByShortUrl("NotExistingShortUrl")).thenReturn(null);
        String originalUrl = linkService.returnOriginalUrl("NotExistingShortUrl");
        assertEquals("error", originalUrl);
    }
}