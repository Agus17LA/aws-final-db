package com.awsfinal.awsfinaldb.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="professions")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profession_id")
    private Integer id;

    @Column(name = "profession_name",nullable = false)
    private String professionName;

    public Profession(String professionName) {
        setProfessionName(professionName);
    }
}