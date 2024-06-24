package com.songify.domain.crud.song;

import com.songify.domain.crud.song.dto.SongDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongUpdater {
    
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    
    void updateById(Long id, Song song) {
        log.info("Updating song by id: {}", id);
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
    
    Song updatePartiallyById(Long id, SongDto updatedSong) {
        Song songById = songRetriever.findSongById(id);
        Song toSave = new Song();
        if (updatedSong.name() != null) {
            toSave.setName(updatedSong.name());
            log.info("patched song id: {}, with new name: {}", id, updatedSong.name());
        } else {
            toSave.setName(songById.getName());
        }
//        if (updatedSong.getArtist() != null) {
//            toSave.artist(updatedSong.getArtist());
//            log.info("patched song id: {}, with new artist: {}", id, updatedSong.getArtist());
//        } else {
//            toSave.artist(songById.getArtist());
//        }
        updateById(id, toSave);
        return toSave;
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
