package com.songify.song;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {
    
    Map<Integer, String> database = new HashMap<>();
    
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/view/songs")
    public String songs(Model model) {
        database.put(1, "abc");
        database.put(2, "def");
        database.put(3, "ghi");
        database.put(4, "jkl");
        model.addAttribute("database", database);
        return "songs";
    }
}
