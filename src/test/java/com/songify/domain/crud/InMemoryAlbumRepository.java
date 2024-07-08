package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
class InMemoryAlbumRepository implements AlbumRepository {
    
    Map<Long, Album> albums = new HashMap<>();
    
    @Override
    public Optional<AlbumInfo> findById(final Long id) {
        return Optional.empty();
    }
    
    @Override
    public Optional<Album> findAlbumById(final Long id) {
        return Optional.empty();
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
    
    }
    
    @Override
    public Album save(final Album album) {
        return null;
    }
}
