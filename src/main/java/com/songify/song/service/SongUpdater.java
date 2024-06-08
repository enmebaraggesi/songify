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
    private final SongRetriever songRetriever;
    
    public SongUpdater(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }
    
    public void updateById(Long id, Song song) {
        log.info("Updating song by id: {}", song.getId());
        songRetriever.existsById(id);
        songRepository.updateById(id, song);
    }
    
    public Song updatePartiallyById(Long id, Song updatedSong) {
        Song songById = songRetriever.findSongById(id);
        Song.SongBuilder builder = Song.builder();
        if (updatedSong.getName() != null) {
            builder.name(updatedSong.getName());
            log.info("patched song id: {}, with new name: {}", id, updatedSong.getName());
        } else {
            builder.name(songById.getName());
        }
        if (updatedSong.getArtist() != null) {
            builder.artist(updatedSong.getArtist());
            log.info("patched song id: {}, with new artist: {}", id, updatedSong.getArtist());
        } else {
            builder.artist(songById.getArtist());
        }
        Song newSong = builder.build();
        updateById(id, newSong);
        return newSong;
    }
}
