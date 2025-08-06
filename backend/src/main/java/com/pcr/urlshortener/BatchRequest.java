package com.pcr.urlshortener;
import lombok.Data;
import java.util.List;

@Data
public class BatchRequest {
    private List<String> urls;
}