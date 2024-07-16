package com.songify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(value = {JwtConfigurationProperties.class})
public class SongifyApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SongifyApplication.class, args);
    }
    
}
