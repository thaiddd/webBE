package com.store.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.backend.Util.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String username;

    private String email;

    private String phoneNumber;

    private String address;

    private String imageUrl;

    private LocalDate birthDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(length = 20)
    private Sex sex;

    private LocalDate createdDate;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    private Cart cart;

    @JsonIgnore
    private String password;

    @ManyToMany
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public User(String username, String email, String password, String address, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.fullName = fullName;
    }
}
