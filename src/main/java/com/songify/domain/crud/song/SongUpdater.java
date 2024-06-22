package com.songify.domain.crud.song;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
class SongUpdater {
    
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    
    SongUpdater(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }
    
    void updateById(Long id, Song song) {
        log.info("Updating song by id: {}", song.getId());
        songRetriever.existsById(id);
        songRepository.updateById(id, song);
    }

//    DIRTY CHECKING METHOD - using setters over entity and @Transactional

//     void updateById(Long id, Song song) {
//        log.info("Updating song by id: {}", song.getId());
//        Song songById = songRetriever.findSongById(id);
//        songById.setName(song.getName());
//        songById.setArtist(song.getArtist());
//    }
    
    Song updatePartiallyById(Long id, Song updatedSong) {
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

//    DIRTY CHECKING METHOD - using setters over entity and @Transactional

//     Song updatePartiallyById(Long id, Song updatedSong) {
//        Song songById = songRetriever.findSongById(id);
//        if (updatedSong.getName() != null) {
//            songById.setName(updatedSong.getName());
//            log.info("patched song id: {}, with new name: {}", id, updatedSong.getName());
//        }
//        if (updatedSong.getArtist() != null) {
//            songById.setArtist(updatedSong.getArtist());
//            log.info("patched song id: {}, with new artist: {}", id, updatedSong.getArtist());
//        }
//        return updatedSong;
//    }
}