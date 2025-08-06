package com.pcr.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class UrlController {

    @Autowired
    private UrlService urlService;

    // Endpoint to create a new short URL
    @PostMapping("/api/v1/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestBody ShortenRequest request) {
        Url shortenedUrl = urlService.shortenUrl(request.getLongUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(shortenedUrl);
    }

    // Endpoint to redirect to the long URL
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        Optional<Url> urlOptional = urlService.getLongUrl(shortCode);

        if (urlOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(urlOptional.get().getLongUrl()));
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found redirect
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    // ... inside UrlController class
    @PostMapping("/api/v1/shorten-batch")
    public ResponseEntity<List<BatchResponseItem>> shortenBatch(@RequestBody BatchRequest request) {
        List<BatchResponseItem> response = urlService.shortenBatch(request.getUrls());
        // 207 Multi-Status indicates partial success
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(response);
    }
}