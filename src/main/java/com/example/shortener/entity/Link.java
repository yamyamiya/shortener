package com.example.shortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "shortUrl")
    @NotNull
    @Getter
    private String shortUrl;

    @Column(name = "longUrl", length = 100000)
    @NotNull
    @Getter
    private String longUrl;

    public Link() {
    }

    public Link(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }
}
