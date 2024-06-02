package com.songify.song.service;

import com.songify.song.model.Song;
import com.songify.song.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
public class SongRetriever {
    
    private final SongRepository songRepository;
    
    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    public Map<Integer, Song> findAll() {
        log.info("Finding all songs");
        return songRepository.findAll();
    }
    
    public Map<Integer, Song> findAllLimited(Integer limit) {
        log.info("Finding all songs with limit {}", limit);
        return songRepository.findAll()
                             .entrySet()
                             .stream()
                             .limit(limit)
                             .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
