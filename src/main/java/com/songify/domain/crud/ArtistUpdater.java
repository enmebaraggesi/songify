package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistUpdater {
    
    private final ArtistRetriever artistRetriever;
    
    @Transactional
    ArtistDto updateArtistNameById(final Long id, final String name) {
        Artist artist = artistRetriever.findById(id);
        artist.setName(name);
        return new ArtistDto(artist.getId(), artist.getName());
    }
}
