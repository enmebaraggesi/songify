package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemorySongRepository implements SongRepository {
    
    Map<Long, Song> songs = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);
    
    @Override
    public Song save(final Song song) {
        long id = index.getAndIncrement();
        songs.put(id, song);
        song.setId(id);
        return song;
    }
    
    @Override
    public List<Song> findAll(final Pageable pageable) {
        return songs.values()
                    .stream()
                    .toList();
    }
    
    @Override
    public Optional<Song> findById(final Long id) {
        return Optional.ofNullable(songs.get(id));
    }
    
    @Override
    public void deleteById(final Long id) {
    
    }
    
    @Override
    public void updateById(final Long id, final Song song) {
    
    }
    
    @Override
    public boolean existsById(final Long id) {
        return false;
    }
    
    @Override
    public void deleteByIdIn(final Collection<Long> ids) {
        ids.forEach(songs::remove);
    }
}
