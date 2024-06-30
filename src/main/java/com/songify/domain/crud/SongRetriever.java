package com.songify.domain.crud;

import com.songify.infrastructure.crud.song.controller.error.SongNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongRetriever {
    
    private final SongRepository songRepository;
    
    List<Song> findAll(Pageable pageable) {
        log.info("Finding all songs");
        return songRepository.findAll(pageable);
    }
    
    Song findSongById(Long id) {
        log.info("Finding a song by id {}", id);
        return songRepository.findById(id)
                             .orElseThrow(() -> new SongNotFoundException("cannot find song with id " + id));
    }
    
    void existsById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("cannot find song with id " + id);
        }
    }
}