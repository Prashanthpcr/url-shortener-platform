package com.pcr.urlshortener;

import lombok.Data;

@Data
public class ShortenRequest {
    private String longUrl;
}