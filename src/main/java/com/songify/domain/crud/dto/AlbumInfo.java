package com.songify.domain.crud.dto;

import java.time.Instant;
import java.util.Set;

/**
 * Projection for Album
 */
public interface AlbumInfo {
    
    Long getId();
    
    String getTitle();
    
    Instant getReleaseDate();
    
    Set<SongInfo> getSongs();
    
    Set<ArtistInfo> getArtists();
    
    /**
     * Projection for Song
     */
    interface SongInfo {
        
        Long getId();
        
        String getName();
        
        Instant getReleaseDate();
        
        Long getDuration();
    }
    
    /**
     * Projection for Artist
     */
    interface ArtistInfo {
        
        Long getId();
        
        String getName();
    }
}