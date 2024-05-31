package com.songify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongViewController {
    
    @GetMapping()
    public String home() {
        return "home.html";
    }
}
