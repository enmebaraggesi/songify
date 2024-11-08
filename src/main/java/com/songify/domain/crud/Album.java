package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
class Album extends BaseEntity {
    
    @Id
    @GeneratedValue(generator = "album_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "album_id_seq",
            sequenceName = "album_id_seq",
            allocationSize = 1
    )
    private Long id;
    
    private String title;
    
    private Instant releaseDate;
    
    @OneToMany(mappedBy = "album",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //to save automatically added new song
    private Set<Song> songs = new HashSet<>();
    
    @ManyToMany(mappedBy = "albums")
    private Set<Artist> artists = new HashSet<>();
    
    void addSong(final Song song) {
        songs.add(song);
    }
    
    void removeArtist(final Artist artist) {
        artists.remove(artist);
    }
    
    void addArtist(final Artist artist) {
        artists.add(artist);
    }
}
