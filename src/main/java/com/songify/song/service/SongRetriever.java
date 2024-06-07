package com.songify.song.service;

import com.songify.song.model.Song;
import com.songify.song.repository.SongRepository;
import com.songify.song.repository.SongRepositoryInMemory;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class SongRetriever {
    
    private final SongRepository songRepository;
    
    public SongRetriever(SongRepositoryInMemory songRepository) {
        this.songRepository = songRepository;
    }
    
    public List<Song> findAll() {
        log.info("Finding all songs");
        return songRepository.findAll();
    }
    
    public List<Song> findAllLimited(Integer limit) {
        log.info("Finding all songs with limit {}", limit);
        return songRepository.findAll()
                             .stream()
                             .limit(limit)
                             .toList();
    }
}
