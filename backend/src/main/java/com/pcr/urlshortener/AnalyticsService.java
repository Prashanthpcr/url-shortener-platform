package com.pcr.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AnalyticsService {
    @Autowired
    private ClickRepository clickRepository;

    @Async // This is the magic! It runs this method in a background thread pool.
    public void recordClick(Url url, String ipAddress, String userAgent) {
        Click click = new Click();
        click.setUrl(url);
        click.setTimestamp(LocalDateTime.now());
        click.setIpAddress(ipAddress);
        click.setUserAgent(userAgent);
        clickRepository.save(click);
        System.out.println(">>> ASYNC: Recorded click for " + url.getShortCode());
    }
}