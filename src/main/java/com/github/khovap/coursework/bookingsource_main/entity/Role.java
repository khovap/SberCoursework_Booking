package com.github.khovap.coursework.bookingsource_main.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    @Override
    public String getAuthority() {
        return getName();
    }

    public Role(Long id) {
        this.id = id;
    }
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
