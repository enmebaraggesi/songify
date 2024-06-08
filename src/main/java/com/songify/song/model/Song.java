package com.songify.song.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "song")
public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String artist;
    
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
}
