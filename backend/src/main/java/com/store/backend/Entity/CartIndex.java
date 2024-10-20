package com.store.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart cart;

    private Long totalPrice;

    private Boolean state;
}
