package com.songify.domain.crud.song;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface SongRepository extends Repository<Song, Long> {
    
    Song save(Song song);
    
    @Query("SELECT s FROM Song s")
    List<Song> findAll(Pageable pageable);
    
    @Query("SELECT s FROM Song s WHERE s.id = :id")
    Optional<Song> findById(Long id);
    
    @Modifying
    @Query("DELETE FROM Song s WHERE s.id = :id")
    void deleteById(Long id);
    
    @Modifying
    @Query("UPDATE Song s SET s.name = :#{#song.name}, s.artist = :#{#song.artist} WHERE s.id = :id")
    void updateById(Long id, Song song);
    
    boolean existsById(Long id);
}
