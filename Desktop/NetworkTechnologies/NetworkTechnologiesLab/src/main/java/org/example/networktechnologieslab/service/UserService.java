package org.example.networktechnologieslab.service;

import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.example.networktechnologieslab.infrastructure.entity.User;
import org.example.networktechnologieslab.infrastructure.repository.BookRepository;
import org.example.networktechnologieslab.infrastructure.repository.LoanRepository;
import org.example.networktechnologieslab.infrastructure.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    public UserService(UserRepository userRepository, LoanRepository loanRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;

        this.bookRepository = bookRepository;
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

    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email);

    }
    public int getUserAge (long id){
        User useranalysis=userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));;
        Date fulldob = useranalysis.getDateOfBirth();

        LocalDate dob = fulldob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dob, currentDate).getYears();

        return age;
    }

    public Long getUserIdByEmail(String email) {
        User useranalysis=userRepository.findUserByEmail(email);
        Long id = useranalysis.getUserId();

        return id;
    }
}

