package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {@Index(
        name = "idx_song_name",
        columnList = "name"
)})
class Song extends BaseEntity {
    
    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private Instant releaseDate;
    
    private Long duration;
    
    @OneToOne(fetch = FetchType.LAZY)
    private Genre genre;
    
    @ManyToOne
    private Album album;
    
    @Enumerated(EnumType.STRING)
    private SongLanguage language;
    
    Song(String name) {
        this.name = name;
    }
    
    Song(final Long duration, final SongLanguage language, final String name, final Instant releaseDate) {
        this.duration = duration;
        this.language = language;
        this.name = name;
        this.releaseDate = releaseDate;
    }
}
