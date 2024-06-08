package com.songify.song.service;

import com.songify.song.model.Song;
import com.songify.song.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SongAdder {
    
    private final SongRepository songRepository;
    
    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    public Song addSong(Song song) {
        log.info("adding new song: {}", song);
        return songRepository.save(song);
    }
}
