package com.songify.song.service;

import com.songify.song.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SongDeleter {
    
    private final SongRepository songRepository;
    
    public SongDeleter(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    public void deleteById(Long id) {
        log.info("Deleting song by id: {}", id);
        songRepository.deleteById(id);
    }
}
