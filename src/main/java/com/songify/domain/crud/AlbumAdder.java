package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
        return new AlbumDto(saved.getId(), saved.getTitle());
    }
}
