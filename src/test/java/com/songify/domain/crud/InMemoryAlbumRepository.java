package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
class InMemoryAlbumRepository implements AlbumRepository {
    
    Map<Long, Album> albums = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);
    
    @Override
    public Optional<AlbumInfo> findById(final Long id) {
        Album album = albums.get(id);
        if (album == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(new AlbumInfoTestImpl(album));
    }
    
    @Override
    public Optional<Album> findAlbumById(final Long id) {
        return Optional.ofNullable(albums.get(id));
    }
    
    @Override
    public Set<Album> findAllAlbumsByArtistId(final Long id) {
        return albums.values()
                     .stream()
                     .filter(album -> album.getArtists()
                                           .stream()
                                           .anyMatch(artist -> artist.getId()
                                                                     .equals(id)))
                     .collect(Collectors.toSet());
    }
    
    @Override
    public void deleteByIdIn(final Collection<Long> ids) {
        ids.forEach(albums::remove);
    }
    
    @Override
    public Album save(final Album album) {
        long id = index.getAndIncrement();
        albums.put(id, album);
        album.setId(id);
        return album;
    }
    
    @Override
    public Set<Album> findAll(final Pageable pageable) {
        return Set.copyOf(albums.values());
    }
}
