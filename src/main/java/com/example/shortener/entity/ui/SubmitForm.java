package com.example.shortener.entity.ui;

import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

public class SubmitForm {
    @URL
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}
