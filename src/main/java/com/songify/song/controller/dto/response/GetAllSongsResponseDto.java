package com.songify.song.controller.dto.response;

import com.songify.song.model.Song;

import java.util.List;

public record GetAllSongsResponseDto(List<Song> songs) {

}
