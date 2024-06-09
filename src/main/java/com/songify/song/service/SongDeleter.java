package com.songify.song.service;

import com.songify.song.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
public class SongDeleter {
    
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    
    public SongDeleter(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }
    
    public void deleteById(Long id) {
        log.info("Deleting song by id: {}", id);
        songRetriever.existsById(id);
        songRepository.deleteById(id);
    }
}
