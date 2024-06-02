package com.songify.song.repository;

import com.songify.song.model.Song;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {
    
    Map<Integer, Song> database = new HashMap<>();
    
    public SongRepository() {
        database.put(1, new Song("abc", "cba"));
        database.put(2, new Song("def", "fed"));
        database.put(3, new Song("ghi", "ihg"));
        database.put(4, new Song("jkl", "lkj"));
    }
    
    public Song saveToDatabase(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }
    
    public Map<Integer, Song> findAll() {
        return database;
    }
}
