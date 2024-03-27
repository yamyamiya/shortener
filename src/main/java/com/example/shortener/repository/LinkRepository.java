package com.example.shortener.repository;

import com.example.shortener.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByShortUrl(String shortUrl);
}
