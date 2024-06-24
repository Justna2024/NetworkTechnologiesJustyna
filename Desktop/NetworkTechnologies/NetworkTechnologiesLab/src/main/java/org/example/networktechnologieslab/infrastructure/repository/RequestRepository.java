package org.example.networktechnologieslab.infrastructure.repository;

import org.example.networktechnologieslab.infrastructure.entity.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
}
