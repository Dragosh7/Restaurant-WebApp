package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "orders_foods",
            joinColumns = {
                    @JoinColumn(name = "orders_id",
                            referencedColumnName = "ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "food_id",
                            referencedColumnName = "ID")})
    private List<Food> command = new ArrayList<>();
    private Double totalPrice;
    private String status;
    private LocalDate date;

}
