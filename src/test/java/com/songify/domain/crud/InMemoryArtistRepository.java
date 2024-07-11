package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryArtistRepository implements ArtistRepository {
    
    Map<Long, Artist> artists = new HashMap<>();
    AtomicInteger index = new AtomicInteger(1);
    
    @Override
    public Set<Artist> findAll(final Pageable pageable) {
        return Set.copyOf(artists.values());
    }
    
    @Override
    public Optional<Artist> findById(final Long id) {
        return Optional.ofNullable(artists.get(id));
    }
    
    @Override
    public void deleteById(final Long id) {
        artists.remove(id);
    }
    
    @Override
    public Artist save(final Artist artist) {
        long id = index.getAndIncrement();
        artists.put(id, artist);
        artist.setId(id);
        return artist;
    }
}
