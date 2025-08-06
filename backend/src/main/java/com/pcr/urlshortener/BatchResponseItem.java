package com.pcr.urlshortener;
import lombok.Builder;
import lombok.Data;

@Data
@Builder // A nice way to construct objects
public class BatchResponseItem {
    private String longUrl;
    private String shortUrl;
    private boolean success;
    private String error;
}