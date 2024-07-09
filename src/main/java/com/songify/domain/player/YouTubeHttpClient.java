package com.songify.domain.player;

import org.springframework.stereotype.Service;

@Service
public interface YouTubeHttpClient {
    
    String playSongById(String name);
}
