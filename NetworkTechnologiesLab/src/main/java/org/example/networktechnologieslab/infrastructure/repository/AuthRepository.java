package org.example.networktechnologieslab.infrastructure.repository;

import org.example.networktechnologieslab.infrastructure.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// in general repositories are needed for accessing data and its manipulation (creating , deleting ,updating)
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUsername(String username);
}
