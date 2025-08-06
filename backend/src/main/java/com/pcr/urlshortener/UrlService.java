package com.pcr.urlshortener;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Url> getLongUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

    private String generateShortCode() {
        // Generate a random alphanumeric string of length 7
        return RandomStringUtils.randomAlphanumeric(7);
    }
}