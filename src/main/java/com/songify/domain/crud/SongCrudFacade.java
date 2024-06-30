package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.songify.domain.crud.SongCrudMapper.mapSongDtoForJsonToSongDto;
import static com.songify.domain.crud.SongCrudMapper.mapSongToSongDto;

@AllArgsConstructor
@Service
public class SongCrudFacade {
    
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    
    public List<SongDto> findAll(final Pageable pageable) {
        return songRetriever.findAll(pageable)
                            .stream()
                            .map(SongCrudMapper::mapSongToSongDto)
                            .toList();
    }
    
    public SongDto findSongById(Long id) {
        Song songById = songRetriever.findSongById(id);
        return mapSongToSongDto(songById);
    }
    
    public SongDto addSong(final SongDtoForJson songDto) {
        // some domain validator
        Song vaidatedAndReadytoSaveSong = new Song(songDto.name());
        // some domain validator ended checking
        Song addedSong = songAdder.addSong(vaidatedAndReadytoSaveSong);
        return mapSongToSongDto(addedSong);
    }
    
    public void deleteById(Long id) {
        songDeleter.deleteById(id);
    }
    
    public void updateById(Long id, SongDtoForJson newSongDto) {
        // some domain validator
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        // some domain validator ended checking
        songUpdater.updateById(id, songValidatedAndReadyToUpdate);
    }
    
    public SongDto updatePartiallyById(Long id, SongDtoForJson updatedSong) {
        SongDto songDto = mapSongDtoForJsonToSongDto(updatedSong);
        Song song = songUpdater.updatePartiallyById(id, songDto);
        return mapSongToSongDto(song);
    }
}
