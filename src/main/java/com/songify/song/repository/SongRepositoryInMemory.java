package com.songify.song.repository;

import com.songify.song.model.Song;

import java.util.*;

public class SongRepositoryInMemory implements SongRepository {
    
    Map<Integer, Song> database = new HashMap<>();
    
    public SongRepositoryInMemory() {
        database.put(1, new Song("abc", "cba"));
        database.put(2, new Song("def", "fed"));
        database.put(3, new Song("ghi", "ihg"));
        database.put(4, new Song("jkl", "lkj"));
    }
    
    public Song save(Song song) {
        database.put(database.size() + 1, song);
        return song;
    }
    
    public List<Song> findAll() {
        return database.values()
                       .stream()
                       .toList();
    }
}
