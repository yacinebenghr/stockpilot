package com.stockpilot.demo.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="products")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "", length = 100,nullable = false)
    private String sku;
    @Column(name = "", length = 100,nullable = false)
    private String name;
    @Column(name = "", length = 100,nullable = false)
    private int minThreshold;
    @Column(name = "", length = 100,nullable = false)
    private double UnitPrice;
}
