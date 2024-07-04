package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("genres")
@AllArgsConstructor
class GenreController {
    
    private final SongifyCrudFacade songifyCrudFacade;
    
    @PostMapping
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreRequestDto dto) {
        GenreDto genreDto = songifyCrudFacade.addGenre(dto);
        return ResponseEntity.ok(genreDto);
    }
}
