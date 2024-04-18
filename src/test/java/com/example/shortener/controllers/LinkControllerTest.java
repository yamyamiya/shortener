package com.example.shortener.controllers;

import com.example.shortener.entity.ui.SubmitForm;
import com.example.shortener.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LinkControllerTest {

    @MockBean //to replace the real Bean that Spring creates and uses while making the test environment by Mock object that without this annotation was not used by Spring
    private LinkService service;
    @Autowired
    private LinkController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnCorrectView() {
        Model model = Mockito.mock(Model.class);
        String viewName = controller.home(model);
        assertEquals("home", viewName);

        //  See AdditionalMatchers.java
        verify(model).addAttribute(eq("submitForm"), any(SubmitForm.class));
    }

    @Test
    public void shouldReturnCorrectViewWhenValidationPassed() throws Exception {
        mockMvc.perform(post("/").param("url", "https://example.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("shortUrl"));
    }

    @Test
    public void shouldReturnCorrectViewWhenValidationFailed() throws Exception {
        mockMvc.perform(post("/").param("url", "hpp"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void shouldCorrectlyRedirectToOriginalUrl() throws Exception {
        String longUrl = "https://example.com";
        when(service.redirectToOriginalUrl("short-url")).thenReturn("redirect:"+longUrl);
        mockMvc.perform(get("/short-url"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(longUrl));
    }
}