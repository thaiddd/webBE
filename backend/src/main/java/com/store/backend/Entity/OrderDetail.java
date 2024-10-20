package com.store.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "Id", nullable = false)
    private Long id;

    private long totalPrice;

    private int quantity;

    @OneToOne
    @JoinColumn(name = "id_product")
    private Product product;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order orders;
}
