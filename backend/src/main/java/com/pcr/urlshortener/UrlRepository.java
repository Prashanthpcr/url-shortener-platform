package com.pcr.urlshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    // This custom method allows us to find a URL by its unique short code
    Optional<Url> findByShortCode(String shortCode);
}