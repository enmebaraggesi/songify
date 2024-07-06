package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
class ArtistDeleter {
    
    private final ArtistRepository artistRepository;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final AlbumDeleter albumDeleter;
    private final SongDeleter songDeleter;
    
    @Transactional
    void deleteArtistByIdWithAlbumsAndSongs(final Long id) {
        Artist artist = artistRetriever.findById(id);
        Set<Album> albumSet = albumRetriever.findAllAlbumsByArtistId(artist.getId());
        // remove artist while they have no albums
        if (!albumSet.isEmpty()) {
            log.info("Artist with ID {} has 0 albums", id);
            artistRepository.deleteById(id);
        }
        // remove artist from albums with more than this artist
        filterOutAlbumsWithMoreThanSelectedArtist(albumSet)
                .forEach(album -> {
                    album.removeArtist(artist);
                    artist.removeAlbum(album);
                });
        // delete songs from albums of only this artist
        Set<Album> selectedArtistAlbums = filterOutAlbumsWithOnlySelectedArtist(albumSet, artist);
        Set<Long> allSongsIds = selectedArtistAlbums
                .stream()
                .flatMap(album -> album.getSongs().stream())
                .map(Song::getId)
                .collect(Collectors.toSet());
        songDeleter.deleteAllSongsByIds(allSongsIds);
        // delete albums of only this artist
        Set<Long> albumsIds = selectedArtistAlbums
                .stream()
                .map(Album::getId)
                .collect(Collectors.toSet());
        albumDeleter.deleteAllAlbumsByIds(albumsIds);
        // delete artist
        artistRepository.deleteById(id);
    }
    
    private static Set<Album> filterOutAlbumsWithOnlySelectedArtist(final Set<Album> albumSet, final Artist artist) {
        return albumSet.stream()
                       .filter(album -> album.getArtists().contains(artist))
                       .collect(Collectors.toSet());
    }
    
    private static Set<Album> filterOutAlbumsWithMoreThanSelectedArtist(final Set<Album> albumSet) {
        return albumSet.stream()
                       .filter(album -> album.getArtists().size() > 1)
                       .collect(Collectors.toSet());
    }
}
