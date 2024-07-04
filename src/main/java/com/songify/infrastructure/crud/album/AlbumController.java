package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("album")
class AlbumController {
    
    private final SongifyCrudFacade songifyCrudFacade;
    
    @PostMapping
    ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumRequestDto dto) {
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(dto);
        return ResponseEntity.ok(albumDto);
    }
}
