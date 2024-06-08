package org.example.networktechnologieslab.controller;

import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.example.networktechnologieslab.infrastructure.entity.User;
import org.example.networktechnologieslab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable long id) {
        return userService.getOne(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> create(@RequestBody User user) {
        var newUser = userService.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/loanHistory")
    public ResponseEntity<Iterable<Loan>> getLoanHistory(@PathVariable long id) {
        Iterable<Loan> userLoans = userService.getLoanHistory(id);
        if (userLoans.iterator().hasNext()) {
            return new ResponseEntity<>(userLoans, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getByEmail")
    public ResponseEntity<?> getByEmail(@RequestParam String email){
        User user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getUserAge")
    public ResponseEntity<?>getUserAge(@RequestParam long id){
        int dob = userService.getUserAge(id);
        return ResponseEntity.ok(dob);
    }

    @GetMapping("/getUserIdByEmail")
    public ResponseEntity<?>getUserIdByEmail(@RequestParam String email){
        Long id = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(id);
    }


}

