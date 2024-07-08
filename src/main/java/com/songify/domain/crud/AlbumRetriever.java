package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.infrastructure.crud.album.error.AlbumNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumRetriever {
    
    private final AlbumRepository albumRepository;

//    AlbumWithArtistsAndSongsDto findAlbumByIdWithArtistsAndSongs(final Long id) {
//        Album album = albumRepository.findByIdWithArtistsAndSongs(id)
//                                     .orElseThrow(() -> new AlbumNotFoundException("Album not found"));
//        Set<Artist> artists = album.getArtists();
//        Set<Song> songs = album.getSongs();
//        AlbumDto albumDto = new AlbumDto(album.getId(), album.getTitle());
//        Set<ArtistDto> artistDtos = artists.stream()
//                                           .map(artist -> new ArtistDto(
//                                                   artist.getId(),
//                                                   artist.getName()))
//                                           .collect(Collectors.toSet());
//        Set<SongDto> songDtos = songs.stream()
//                                     .map(song -> new SongDto(
//                                             song.getId(),
//                                             song.getName(),
//                                             song.getReleaseDate(),
//                                             song.getDuration(),
//                                             song.getLanguage().toString()))
//                                     .collect(Collectors.toSet());
//        return new AlbumWithArtistsAndSongsDto(albumDto, artistDtos, songDtos);
//    }
    
    AlbumInfo findAlbumByIdWithArtistsAndSongs(final Long id) {
        return albumRepository.findById(id)
                              .orElseThrow(() -> new AlbumNotFoundException("Album not found"));
    }
    
    Set<Album> findAllAlbumsByArtistId(final Long id) {
        return albumRepository.findAllAlbumsByArtistId(id);
    }
    
    Album findById(final Long albumId) {
        return albumRepository.findAlbumById(albumId)
                              .orElseThrow(() -> new AlbumNotFoundException("Album not found"));
    }
    
    Set<AlbumDto> findAllAlbumsDtoByArtistId(final Long id) {
        Set<Album> albumsByArtistId = findAllAlbumsByArtistId(id);
        return albumsByArtistId.stream()
                               .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                               .collect(Collectors.toSet());
    }
}
