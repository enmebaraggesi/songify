package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final AlbumRetriever albumRetriever;
    
    public SongDto addSong(final SongDtoForJson dto) {
        Song addedSong = songAdder.addSong(dto);
        return mapSongToSongDto(addedSong);
    }
    
    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }
    
    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }
    
    public AlbumDto addAlbumWithSongs(AlbumRequestDto dto) {
        return albumAdder.addAlbum(dto.title(), dto.releaseDate(), dto.songId());
    }
    
    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }
    
    public List<SongDto> findAllSongs(final Pageable pageable) {
        return songRetriever.findAllDtos(pageable);
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
}
