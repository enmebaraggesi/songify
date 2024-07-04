package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("artists")
class ArtistController {
    
    private final SongifyCrudFacade songifyCrudFacade;
    
    @PostMapping
    ResponseEntity<ArtistDto> createArtist(@RequestBody ArtistRequestDto dto) {
        ArtistDto artistDto = songifyCrudFacade.addArtist(dto);
        return ResponseEntity.ok(artistDto);
    }
    
    @GetMapping
    ResponseEntity<AllArtistsDto> getAllArtists(@PageableDefault(size = 20, sort = {"id"}) Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(pageable);
        AllArtistsDto allArtistsDto = new AllArtistsDto(allArtists);
        return ResponseEntity.ok(allArtistsDto);
    }
}
