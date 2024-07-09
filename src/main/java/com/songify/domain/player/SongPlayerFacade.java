package com.songify.domain.player;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;

public class SongPlayerFacade {
    
    private final SongifyCrudFacade songifyCrudFacade;
    private final YouTubeHttpClient youTubeHttpClient;
    
    SongPlayerFacade(final SongifyCrudFacade songifyCrudFacade, final YouTubeHttpClient youTubeHttpClient) {
        this.songifyCrudFacade = songifyCrudFacade;
        this.youTubeHttpClient = youTubeHttpClient;
    }
    
    public String playSongWithId(Long id) {
        SongDto songById = songifyCrudFacade.findSongById(id);
        String name = songById.name();
        // jakiś kod odpytujący YouTube o piosenkę
        String result = youTubeHttpClient.playSongById(name);
        if (result.equals("success")) {
            return result;
        }
        throw new RuntimeException("some error");
    }
}
