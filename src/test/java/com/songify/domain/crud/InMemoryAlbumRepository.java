package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("ALL")
class InMemoryAlbumRepository implements AlbumRepository {
    
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
        return Set.of();
    }
    
    @Override
    public void deleteByIdIn(final Collection<Long> ids) {
    
    }
    
    @Override
    public Album save(final Album album) {
        return null;
    }
}
