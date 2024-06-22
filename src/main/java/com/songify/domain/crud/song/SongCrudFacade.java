package com.songify.domain.crud.song;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SongCrudFacade {
    
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    
    public List<Song> findAll(final Pageable pageable) {
        return songRetriever.findAll(pageable);
    }
    
    public Song findSongById(final Long id) {
        return songRetriever.findSongById(id);
    }
    
    public Song addSong(final Song song) {
        return songAdder.addSong(song);
    }
    
    public void deleteById(final Long id) {
        songDeleter.deleteById(id);
    }
    
    public void updateById(final Long id, final Song newSong) {
        songUpdater.updateById(id, newSong);
    }
    
    public Song updatePartiallyById(final Long id, final Song updatedSong) {
        return songUpdater.updatePartiallyById(id, updatedSong);
    }
}
