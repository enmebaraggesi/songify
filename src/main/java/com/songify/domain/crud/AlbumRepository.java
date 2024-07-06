package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

interface AlbumRepository extends CrudRepository<Album, Integer> {

//    Optional<Album> findById(Long id);

//    @Query("select a from Album a join fetch a.artists artists join fetch a.songs songs where a.id = :id")
//    Optional<Album> findByIdWithArtistsAndSongs(Long id);
    
    Optional<AlbumInfo> findById(Long id);
    
    @Query("select a from Album a where a.id = :id")
    Optional<Album> findAlbumById(Long id);
    
    @Query("""
           select a from Album a
           inner join a.artists artists
           where artists.id = :id
           """)
    Set<Album> findAllAlbumsByArtistId(Long id);
    
    @Modifying
    @Query("delete from Album a where a.id in :ids")
    void deleteByIdIn(Collection<Long> ids);
}
