package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {
    
    private final ArtistRepository artistRepository;
    
    ArtistDto addArtist(final String name) {
        Artist artist = new Artist(name);
        Artist savedArtist = artistRepository.save(artist);
        return new ArtistDto(savedArtist.getId(),
                             savedArtist.getName());
    }
    
    ArtistDto addArtistWithDefaultAlbumAndSong(final ArtistRequestDto dto) {
        Artist save = saveArtistWithDefaultAlbumAndSong(dto);
        return new ArtistDto(save.getId(), save.getName());
    }
    
    private Artist saveArtistWithDefaultAlbumAndSong(final ArtistRequestDto dto) {
        Artist artist = new Artist(dto.name());
        
        Album album = new Album();
        album.setTitle("album: " + UUID.randomUUID());
        album.setReleaseDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        
        Song song = new Song(99L,
                             SongLanguage.OTHER,
                             "song: " + UUID.randomUUID(),
                             Instant.now());
        album.addSong(song);
        
        album.addArtist(artist);
        artist.addAlbum(album);
        return artistRepository.save(artist);
    }
}
