package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.album.error.AlbumNotFoundException;
import com.songify.infrastructure.crud.artist.error.ArtistNotFoundException;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
import com.songify.infrastructure.crud.song.controller.error.SongNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongifyCrudFacadeTest {
    
    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );
    
    @Test
    @DisplayName("should add song")
    public void should_add_song() {
        //given
        SongDtoForJson song = SongDtoForJson.builder()
                                            .title("testSong")
                                            .language("english")
                                            .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        //when
        SongDto addedSong = songifyCrudFacade.addSong(song);
        //then
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()))
                .extracting(SongDto::id)
                .containsExactly(1L);
        assertThat(addedSong.name()).isEqualTo("testSong");
        assertThat(addedSong.language()).isEqualTo("ENGLISH");
    }
    
    @Test
    @DisplayName("should return song with default genre")
    public void should_return_song_with_default_genre() {
        //given
        SongDtoForJson song = SongDtoForJson.builder()
                                            .title("testSong")
                                            .language("english")
                                            .build();
        Long songId = songifyCrudFacade.addSong(song).id();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).hasSize(1);
        //when
        SongDto result = songifyCrudFacade.findSongById(songId);
        //then
        assertThat(result.name()).isEqualTo("testSong");
        assertThat(result.genre().id()).isEqualTo(1L);
        assertThat(result.genre().name()).isEqualTo("default");
    }
    
    @Test
    @DisplayName("should throw exception when song not found by id")
    public void should_throw_exception_when_song_not_found_by_id() {
        //given
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongById(1L));
        //then
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("cannot find song with id 1");
    }
    
    @Test
    @DisplayName("should add artist")
    public void should_add_artist() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                                                  .name("testArtist")
                                                  .build();
        Set<ArtistDto> artists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertTrue(artists.isEmpty());
        //when
        ArtistDto result = songifyCrudFacade.addArtist(artist);
        //then
        Set<ArtistDto> artistsCollectionAfterSave = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertEquals(1, artistsCollectionAfterSave.size());
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("testArtist");
    }
    
    @Test
    @DisplayName("should throw exception when artist not found by id")
    public void should_throw_exception_when_artist_not_found_by_id() {
        //given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(1L));
        //then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with ID 1 not found");
    }
    
    @Test
    @DisplayName("should add artist to album")
    public void should_add_artist_to_album() {
        //given
        Long artistId = songifyCrudFacade.addArtist(ArtistRequestDto.builder()
                                                                    .name("testArtist")
                                                                    .build()).id();
        Long songId = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                              .language("english")
                                                              .build()).id();
        AlbumRequestDto album = AlbumRequestDto.builder()
                                               .title("testAlbum")
                                               .releaseDate(Instant.now())
                                               .songId(songId)
                                               .build();
        Long albumId = songifyCrudFacade.addAlbumWithSongs(album).id();
        //when
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        //then
        Set<AlbumDto> albumsByArtistId = songifyCrudFacade.findAlbumsByArtistId(artistId);
        assertThat(albumsByArtistId).extracting(AlbumDto::id)
                                    .containsExactly(albumId);
    }
    
    @Test
    @DisplayName("should delete artist by id when he has no albums")
    public void should_delete_artist_by_id_when_he_has_no_albums() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                                                  .name("testArtist")
                                                  .build();
        Long artistId = songifyCrudFacade.addArtist(artist).id();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        //then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }
    
    @Test
    @DisplayName("should delete artist with albums and songs when there was only one artist owning albums")
    public void should_delete_artist_with_albums_and_songs_when_there_was_only_one_artist_owning_albums() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                                                  .name("testArtist")
                                                  .build();
        Long artistId = songifyCrudFacade.addArtist(artist).id();
        SongDto song = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                               .title("testSong")
                                                               .language("english")
                                                               .build());
        Long albumId = songifyCrudFacade.addAlbumWithSongs(AlbumRequestDto.builder()
                                                                          .title("testAlbum")
                                                                          .releaseDate(Instant.now())
                                                                          .songId(song.id())
                                                                          .build()).id();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.countArtistsForAlbumId(albumId)).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(1);
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        //then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
    }
    
    @Test
    @DisplayName("should delete artist from album by id when there were more than one artist in album")
    public void should_delete_artist_only_from_album_by_id_when_there_were_more_than_one_artist_in_album() {
        //given
        Long songId = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                              .title("testSong")
                                                              .language("english")
                                                              .build()).id();
        Long albumId = songifyCrudFacade.addAlbumWithSongs(AlbumRequestDto.builder()
                                                                          .title("testAlbum")
                                                                          .releaseDate(Instant.now())
                                                                          .songId(songId)
                                                                          .build()).id();
        ArtistRequestDto artist1 = ArtistRequestDto.builder()
                                                   .name("testArtist1")
                                                   .build();
        ArtistRequestDto artist2 = ArtistRequestDto.builder()
                                                   .name("testArtist2")
                                                   .build();
        Long artistId1 = songifyCrudFacade.addArtist(artist1).id();
        Long artistId2 = songifyCrudFacade.addArtist(artist2).id();
        songifyCrudFacade.addArtistToAlbum(artistId1, albumId);
        songifyCrudFacade.addArtistToAlbum(artistId2, albumId);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId)
                                    .getArtists()
                                    .size()).isEqualTo(2);
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId1);
        //then
        Set<AlbumInfo.ArtistInfo> albumArtists = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId)
                                                             .getArtists();
        assertThat(albumArtists.size()).isEqualTo(1);
        assertThat(albumArtists).extracting(AlbumInfo.ArtistInfo::getName)
                                .containsExactly("testArtist2");
        Set<AlbumInfo.SongInfo> songs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId)
                                                         .getSongs();
        assertThat(songs.size()).isEqualTo(1);
        assertThat(songs).extracting(AlbumInfo.SongInfo::getName)
                         .containsExactly("testSong");
    }
    
    @Test
    @DisplayName("should return album by id")
    public void should_return_album_by_id() {
        //given
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        Long songId = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                              .title("testSong")
                                                              .language("english")
                                                              .build()).id();
        Long albumId = songifyCrudFacade.addAlbumWithSongs(AlbumRequestDto.builder()
                                                                          .title("testAlbum")
                                                                          .releaseDate(Instant.now())
                                                                          .songId(songId)
                                                                          .build()).id();
        //when
        AlbumInfo albumById = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        //then
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(albumById.getId()).isEqualTo(albumId);
        assertThat(albumById.getTitle()).isEqualTo("testAlbum");
    }
    
    @Test
    @DisplayName("should add album with song")
    public void should_add_album_with_song() {
        //given
        SongDto song = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                               .title("testSong")
                                                               .language("english")
                                                               .build());
        AlbumRequestDto album = AlbumRequestDto.builder()
                                               .title("testAlbum")
                                               .releaseDate(Instant.now())
                                               .songId(song.id())
                                               .build();
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        //when
        AlbumDto addedAlbum = songifyCrudFacade.addAlbumWithSongs(album);
        //then
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged()).size()).isEqualTo(1);
        AlbumInfo albumWithSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(addedAlbum.id());
        assertThat(albumWithSongs.getId()).isEqualTo(1L);
        assertThat(albumWithSongs.getTitle()).isEqualTo("testAlbum");
        Set<AlbumInfo.SongInfo> songs = albumWithSongs.getSongs();
        assertTrue(songs.stream().anyMatch(songInfo -> songInfo.getId().equals(song.id())));
    }
    
    @Test
    @DisplayName("should throw exception when album not found by id")
    public void should_throw_exception_when_album_not_found_by_id() {
        //given
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(1L));
        //then
        assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Album not found");
    }
}