package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
        Set<ArtistDto> artistsCollection = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertTrue(artistsCollection.isEmpty());
        //when
        ArtistDto result = songifyCrudFacade.addArtist(artist);
        //then
        Set<ArtistDto> artistsCollectionAfterSave = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertEquals(1, artistsCollectionAfterSave.size());
        assertThat(result.id()).isEqualTo(0L);
        assertThat(result.name()).isEqualTo("test");
    }
}