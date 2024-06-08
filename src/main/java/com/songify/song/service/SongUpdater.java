package com.songify.song.service;

import com.songify.song.model.Song;
import com.songify.song.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
public class SongUpdater {
    
    private final SongRepository songRepository;
    
    public SongUpdater(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    public void updateSong(Long id, Song song) {
        log.info("Updating song by id: {}", song.getId());
        songRepository.updateById(id, song);
    }
}
