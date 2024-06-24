package org.example.networktechnologieslab.service;

import org.example.networktechnologieslab.infrastructure.entity.Request;
import org.example.networktechnologieslab.infrastructure.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Iterable<Request> getAll() {
        return requestRepository.findAll();
    }

    public Optional<Request> getOne(long id) {
        return requestRepository.findById(id);
    }

    public Request create(Request request) {
        return requestRepository.save(request);
    }

    public void delete(long id) {
        requestRepository.deleteById(id);
    }
}
