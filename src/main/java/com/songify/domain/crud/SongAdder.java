package com.songify.domain.crud;

import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongAdder {
    
    private final SongRepository songRepository;
    private final GenreRetriever genreRetriever;
    
    Song addSong(SongDtoForJson song) {
        log.info("adding new song: {}", song);
        Song songToSave = new Song(song.duration(),
                                   SongLanguage.valueOf(song.language().toUpperCase()),
                                   song.title(),
                                   song.releaseDate(),
                                   genreRetriever.findById(1L));
        return songRepository.save(songToSave);
    }
}
