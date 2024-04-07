package org.example.networktechnologieslab.infrastructure.repository;
// task from client -> Controller Class (where we define the endpoint and what it returns ->
// controller asks Service -> service takes function from Repository -> to entity
import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findAll();
    Iterable<Loan> findByUserUserId(Long userId);
}
