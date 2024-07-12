package com.songify.domain.usercrud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
class User extends BaseEntity {
    
    @Id
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1
    )
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private String password;
    
    private boolean enabled = true;
    
    private Collection<String> authorities = new HashSet<>();
    
    public User(final Collection<String> authorities, final String email, final boolean enabled, final String password) {
        this.authorities = authorities;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
    }
}
