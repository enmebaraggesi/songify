package com.songify.song.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false)
    private Long id;
    
    @Column(nullable = false)
    String name;
    
    @Column(nullable = false)
    String artist;
    
    Instant releaseDate;
    
    Long duration;
    
    @Enumerated(EnumType.STRING)
    SongLanguage language;
    
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
}
