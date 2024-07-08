package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.artist.error.ArtistNotFoundException;
import com.songify.infrastructure.crud.song.controller.dto.response.SongDtoForJson;
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
    @DisplayName("should add 'test' Artist with id:0 when 'test' Artist was sent")
    public void should_add_test_artist_with_id_zero_when_test_artist_was_sent() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                                                  .name("test")
                                                  .build();
        Set<ArtistDto> artists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertTrue(artists.isEmpty());
        //when
        ArtistDto result = songifyCrudFacade.addArtist(artist);
        //then
        Set<ArtistDto> artistsCollectionAfterSave = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertEquals(1, artistsCollectionAfterSave.size());
        assertThat(result.id()).isEqualTo(0L);
        assertThat(result.name()).isEqualTo("test");
    }
    
    @Test
    @DisplayName("should throw exception Artist Not Found when ID:0")
    public void should_throw_exception_artist_not_found_when_id_was_zero() {
        //given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
        //then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with ID 0 not found");
    }
    
    @Test
    @DisplayName("should delete artist by id when he has no albums")
    public void should_delete_artist_by_id_when_he_has_no_albums() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                                                  .name("test")
                                                  .build();
        Long artistId = songifyCrudFacade.addArtist(artist).id();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        //when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        //then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }
    
    @Test
    @DisplayName("should add song")
    public void should_add_song() {
        //given
        SongDtoForJson song = SongDtoForJson.builder()
                                            .title("test")
                                            .language("english")
                                            .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        //when
        SongDto addedSong = songifyCrudFacade.addSong(song);
        //then
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(addedSong.id()).isEqualTo(0L);
        assertThat(addedSong.name()).isEqualTo("test");
        assertThat(addedSong.language()).isEqualTo("ENGLISH");
    }
    
    @Test
    @DisplayName("should add album with song")
    public void should_add_album_with_song() {
        //given
        SongDto song = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                               .title("test")
                                                               .language("english")
                                                               .build());
        AlbumRequestDto album = AlbumRequestDto.builder()
                                               .title("test")
                                               .releaseDate(Instant.now())
                                               .songId(song.id())
                                               .build();
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        //when
        AlbumDto addedAlbum = songifyCrudFacade.addAlbumWithSongs(album);
        //then
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged()).size()).isEqualTo(1);
        AlbumInfo albumWithSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(addedAlbum.id());
        assertThat(albumWithSongs.getId()).isEqualTo(0L);
        assertThat(albumWithSongs.getTitle()).isEqualTo("test");
        Set<AlbumInfo.SongInfo> songs = albumWithSongs.getSongs();
        assertTrue(songs.stream().anyMatch(songInfo -> songInfo.getId().equals(song.id())));
    }
    
    @Test
    @DisplayName("should delete artist by id with albums and songs when he has one album and was albums only artist")
    public void should_delete_artist_by_id_with_albums_and_songs_when_he_has_one_album_and_was_albums_only_artist() {
        //given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                                                  .name("test")
                                                  .build();
        Long artistId = songifyCrudFacade.addArtist(artist).id();
        SongDto song = songifyCrudFacade.addSong(SongDtoForJson.builder()
                                                               .title("test")
                                                               .language("english")
                                                               .build());
        Long albumId = songifyCrudFacade.addAlbumWithSongs(AlbumRequestDto.builder()
                                                                          .title("test")
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
    @DisplayName("should delete artist by id with albums songs when he has one album and was albums only one artist")
    public void should_delete_artist_from_album_when_he_is_not_only_one_artist_for_album() {
        //given
//        assertThat(album.getArtists().size()).isEqualTo(1);
        //when
        //then
    }
}