package org.example.networktechnologieslab.controller;

import org.example.networktechnologieslab.infrastructure.entity.Request;
import org.example.networktechnologieslab.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Request> getAll() {
        return requestService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getOne(@PathVariable long id) {
        return requestService.getOne(id)
                .map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Request> create(@RequestBody Request request) {
        var newRequest = requestService.create(request);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> delete(@PathVariable long id) {
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
