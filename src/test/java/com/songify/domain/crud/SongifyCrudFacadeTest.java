package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.infrastructure.crud.artist.error.ArtistNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

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
}