package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.AllGenresResponseDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import com.songify.infrastructure.crud.song.controller.dto.response.UpdateSongResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.songify.domain.crud.SongCrudMapper.mapSongDtoForJsonToSongDto;
import static com.songify.domain.crud.SongCrudMapper.mapSongToSongDto;

@AllArgsConstructor
@Service
public class SongifyCrudFacade {
    
    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final ArtistRetriever artistRetriever;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final AlbumRetriever albumRetriever;
    private final GenreRetriever genreRetriever;
    
    public SongDto addSong(final SongDtoForJson dto) {
        Song addedSong = songAdder.addSong(dto);
        return mapSongToSongDto(addedSong);
    }
    
    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }
    
    public AlbumDto addArtistToAlbum(Long artistId, Long albumId) {
        Album album = artistAssigner.addArtistToAlbum(artistId, albumId);
        return new AlbumDto(album.getId(),
                            album.getTitle(),
                            album.getArtists()
                                 .stream()
                                 .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                                 .collect(Collectors.toSet()),
                            album.getSongs()
                                 .stream()
                                 .map(song -> new SongDto(song.getId(),
                                                          song.getName(),
                                                          song.getReleaseDate(),
                                                          song.getDuration(),
                                                          song.getLanguage().toString(),
                                                          new GenreDto(song.getGenre().getId(),
                                                                       song.getGenre().getName())))
                                 .collect(Collectors.toSet()));
    }
    
    public ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto) {
        return artistAdder.addArtistWithDefaultAlbumAndSong(dto);
    }
    
    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }
    
    public AlbumDto addAlbumWithSongs(AlbumRequestDto dto) {
        return albumAdder.addAlbum(dto.title(), dto.releaseDate(), dto.songId());
    }
    
    public List<SongDto> findAllSongs(final Pageable pageable) {
        return songRetriever.findAllSongs(pageable);
    }
    
    public SongDto findSongById(Long id) {
        return songRetriever.findDtoById(id);
    }
    
    public Set<ArtistDto> findAllArtists(final Pageable pageable) {
        return artistRetriever.findAllArtists(pageable);
    }
    
    public AlbumInfo findAlbumByIdWithArtistsAndSongs(final Long id) {
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }
    
    public void deleteSongById(Long id) {
        songDeleter.deleteById(id);
    }
    
    public void deleteArtistByIdWithAlbumsAndSongs(Long id) {
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(id);
    }
    
    public void updateSongById(Long id, SongDtoForJson dto) {
        // some domain validator
        Song songValidatedAndReadyToUpdate = new Song(dto.title());
        // some domain validator ended checking
        songUpdater.updateById(id, songValidatedAndReadyToUpdate);
    }
    
    public SongDto updateSongPartiallyById(Long id, SongDtoForJson updatedSong) {
        SongDto songDto = mapSongDtoForJsonToSongDto(updatedSong);
        Song song = songUpdater.updatePartiallyById(id, songDto);
        return mapSongToSongDto(song);
    }
    
    public ArtistDto updateArtistNameById(Long id, String name) {
        return artistUpdater.updateArtistNameById(id, name);
    }
    
    public Set<AlbumDto> findAlbumsByArtistId(final Long id) {
        return albumRetriever.findAllAlbumsDtoByArtistId(id);
    }
    
    public Set<AlbumDto> findAllAlbums(final Pageable pageable) {
        return albumRetriever.findAllAlbums(pageable);
    }
    
    int countArtistsForAlbumId(final Long albumId) {
        return albumRetriever.countArtistsForAlbumId(albumId);
    }
    
    public AllGenresResponseDto findAllGenres() {
        List<GenreDto> genreDtos = genreRetriever.findAll();
        return new AllGenresResponseDto(genreDtos);
    }
    
    @Transactional
    public UpdateSongResponseDto assignGenreToSong(final Long songId, final Long genreId) {
        Song song = songRetriever.findById(songId);
        Genre genre = genreRetriever.findById(genreId);
        song.setGenre(genre);
        return new UpdateSongResponseDto("updated song with genre: " + genre.getName());
    }
    
    @Transactional
    public AlbumDto addSongToAlbum(final Long albumId, final Long songId) {
        Song song = songRetriever.findById(songId);
        Album album = albumRetriever.findById(albumId);
        album.addSong(song);
        song.setAlbum(album);
        return new AlbumDto(album.getId(),
                            album.getTitle(),
                            album.getArtists()
                                 .stream()
                                 .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                                 .collect(Collectors.toSet()),
                            album.getSongs()
                                 .stream()
                                 .map(song1 -> new SongDto(song1.getId(),
                                                           song1.getName(),
                                                           song1.getReleaseDate(),
                                                           song1.getDuration(),
                                                           song1.getLanguage().toString(),
                                                           new GenreDto(song1.getGenre().getId(),
                                                                        song1.getGenre().getName())))
                                 .collect(Collectors.toSet()));
    }
}
