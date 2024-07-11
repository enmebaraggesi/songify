package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.GetAllAlbumsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("albums")
class AlbumController {
    
    private final SongifyCrudFacade songifyCrudFacade;
    
    @PostMapping
    ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumRequestDto dto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(dto);
        return ResponseEntity.ok(albumDto);
    }
    
    @GetMapping("{albumId}")
    ResponseEntity<AlbumInfo> getAlbumWithArtistsAndSongs(@PathVariable Long albumId) {
        AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumByIdWithArtistsAndSongs);
    }
    
    @GetMapping
    ResponseEntity<GetAllAlbumsResponseDto> getAlbums(Pageable pageable) {
        Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbums(pageable);
        return ResponseEntity.ok(new GetAllAlbumsResponseDto(allAlbums));
    }
}
