package com.store.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long Id;

//    private  String roleName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> user;
}
