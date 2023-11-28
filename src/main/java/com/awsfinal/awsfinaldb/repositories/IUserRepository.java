package com.awsfinal.awsfinaldb.repositories;

import com.awsfinal.awsfinaldb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByDni(String dni);

}
