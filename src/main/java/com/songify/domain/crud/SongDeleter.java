package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongDeleter {
    
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    
    void deleteById(Long id) {
        log.info("Deleting song by id: {}", id);
        songRetriever.existsById(id);
        songRepository.deleteById(id);
    }
    
    void deleteAllSongsByIds(final Set<Long> songsIds) {
        songRepository.deleteByIdIn(songsIds);
    }
}
