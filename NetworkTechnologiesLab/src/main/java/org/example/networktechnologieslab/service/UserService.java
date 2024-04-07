package org.example.networktechnologieslab.service;

import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.example.networktechnologieslab.infrastructure.entity.User;
import org.example.networktechnologieslab.infrastructure.repository.LoanRepository;
import org.example.networktechnologieslab.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public UserService(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getOne(long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public void delete(long id){
        userRepository.deleteById(id);
    }

    public Iterable<Loan> getLoanHistory(long userId){return loanRepository.findByUserUserId(userId);}
}

