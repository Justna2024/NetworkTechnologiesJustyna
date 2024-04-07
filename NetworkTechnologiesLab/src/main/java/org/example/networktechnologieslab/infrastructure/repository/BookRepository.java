package org.example.networktechnologieslab.infrastructure.repository;

import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findAll(); // list all of books
    Iterable<Book> findByTitle(String title); // must be fing by Title not by nam
    // only 1 book with given type
}
