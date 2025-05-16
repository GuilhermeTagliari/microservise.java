package br.edu.atitus.product_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(length = 255, nullable = false)
    private String brand;

    @Column(length = 255, nullable = false)
    private String model;

    @Column(length = 3, nullable = false)
    private String currency;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Integer stock;

    @Transient
    private String environment;

    @Transient
    private double convertedPrice;

    // Getters and setters (omitidos para brevidade)
}
