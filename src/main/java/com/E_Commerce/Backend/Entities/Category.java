package com.E_Commerce.Backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;

    @Column(
            name = "description",
            nullable = false
    )
    private String description;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

}
