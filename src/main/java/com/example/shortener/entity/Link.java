package com.example.shortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Objects;

@Entity
@Table(name="link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "short_url")
    @NotNull
    @Getter
    private String shortUrl;

    @Column(name = "long_url", length = 100000)
    @NotNull
    @Getter
    private String longUrl;

    public Link() {
    }

    public Link(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(shortUrl, link.shortUrl) && Objects.equals(longUrl, link.longUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, longUrl);
    }
}
