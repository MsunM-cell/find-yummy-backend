package com.example.demo.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findAppUserByUsername(String username);
    Optional<AppUser> findAppUserById(Long id);

    List<AppUser> findAppUsersByCity(String city);
}
