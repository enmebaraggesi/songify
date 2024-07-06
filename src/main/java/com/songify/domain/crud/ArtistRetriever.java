package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.infrastructure.crud.artist.error.ArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ArtistRetriever {
    
    private final ArtistRepository artistRepository;
    
    Set<ArtistDto> findAllArtists(final Pageable pageable) {
        Set<Artist> artistSet = artistRepository.findAll(pageable);
        return artistSet.stream()
                        .map(artist -> new ArtistDto(
                                artist.getId(),
                                artist.getName()))
                        .collect(Collectors.toSet());
    }
    
    Artist findById(final Long id) {
        return artistRepository.findById(id)
                               .orElseThrow(() -> new ArtistNotFoundException("Artist with ID " + id + "not found"));
    }
}
