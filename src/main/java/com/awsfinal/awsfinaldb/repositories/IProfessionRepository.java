package com.awsfinal.awsfinaldb.repositories;

import com.awsfinal.awsfinaldb.domain.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProfessionRepository extends JpaRepository<Profession,Integer> {
    Optional<Profession> findByProfessionName(String professionName);
}
