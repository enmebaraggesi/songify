package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumAdder {
    
    private final SongRetriever songRetriever;
    private final AlbumRepository albumRepository;
    
    @Transactional
    AlbumDto addAlbum(final String title, final Instant releaseDate, final Long songId) {
        Song song = songRetriever.findById(songId);
        Album album = new Album();
        album.setTitle(title);
        album.setReleaseDate(releaseDate);
        album.addSong(song);
        Album saved = albumRepository.save(album);
        Set<ArtistDto> artistDtos = saved.getArtists()
                                         .stream()
                                         .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                                         .collect(Collectors.toSet());
        Set<SongDto> songDtos = saved.getSongs()
                                     .stream()
                                     .map(song1 -> new SongDto(song1.getId(),
                                                               song1.getName(),
                                                               song1.getReleaseDate(),
                                                               song1.getDuration(),
                                                               song1.getLanguage().toString(),
                                                               new GenreDto(song1.getGenre().getId(),
                                                                            song1.getGenre().getName())))
                                     .collect(Collectors.toSet());
        return new AlbumDto(saved.getId(), saved.getTitle(), artistDtos, songDtos);
    }
}
