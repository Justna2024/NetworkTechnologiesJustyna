package org.example.networktechnologieslab.infrastructure.repository;

import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.example.networktechnologieslab.infrastructure.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    User findUserByEmail(String email);
}
