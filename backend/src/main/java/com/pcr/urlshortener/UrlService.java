package com.pcr.urlshortener;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url shortenUrl(String longUrl) {
        String shortCode;
        // Keep generating a new short code until we find one that isn't already in use
        do {
            shortCode = generateShortCode();
        } while (urlRepository.findByShortCode(shortCode).isPresent());

        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);
        newUrl.setShortCode(shortCode);

        return urlRepository.save(newUrl);
    }

    // ... inside UrlService class
public List<BatchResponseItem> shortenBatch(List<String> urls) {
    List<BatchResponseItem> results = new ArrayList<>();
    for (String longUrl : urls) {
        try {
            // A simple validation
            if (longUrl == null || !longUrl.startsWith("http")) {
               throw new IllegalArgumentException("Invalid URL format");
            }
            Url shortenedUrl = shortenUrl(longUrl);
            results.add(BatchResponseItem.builder()
                    .longUrl(longUrl)
                    .shortUrl("http://localhost:8080/" + shortenedUrl.getShortCode())
                    .success(true)
                    .build());
        } catch (Exception e) {
            results.add(BatchResponseItem.builder()
                    .longUrl(longUrl)
                    .success(false)
                    .error(e.getMessage())
                    .build());
        }
    }
    return results;
}

    private String generateShortCode() {
        // Generate a random alphanumeric string of length 7
        return RandomStringUtils.randomAlphanumeric(7);
    }

    @Cacheable(value = "urls", key = "#shortCode")
public Optional<Url> getLongUrl(String shortCode) {
    System.out.println(">>> DATABASE HIT for short code: " + shortCode); // We'll use this to test
    return urlRepository.findByShortCode(shortCode);
}

    
}