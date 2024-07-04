package com.songify.domain.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface AlbumRepository extends CrudRepository<Album, Integer> {

//    Optional<Album> findById(Long id);
    
    @Query("select a from Album a join fetch a.artists artists join fetch a.songs songs where a.id = :id")
    Optional<Album> findByIdWithArtistsAndSongs(Long id);
}
