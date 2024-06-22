package com.songify.domain.crud.song;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Song extends BaseEntity {
    
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String artist;
    Instant releaseDate;
    Long duration;
    @Enumerated(EnumType.STRING)
    SongLanguage language;
    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    private Long id;
    
    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
}
