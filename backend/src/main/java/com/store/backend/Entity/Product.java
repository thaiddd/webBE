package com.store.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Transient
//    @JsonIgnore
//    private MultipartFile image;

    private String imageUrl;

    private String productName;

    private long price;

    private String sex;

    private String color;

    private int quantity;

    private LocalDate createdDate;

    @Column(length = 6000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;


    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<CartIndex> cartIndexs;

}
