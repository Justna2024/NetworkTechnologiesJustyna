package org.example.networktechnologieslab.controller;

import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.example.networktechnologieslab.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/*
Mapping is naming endpoints (links ) and defining their properties

post - sends data to backend

request - all the ones inside the main bracket will
            have a prefix eg in this case /loan before
            get all eg

delete - delete from db

get - we request smt from backened including the db
 */
@RestController
@RequestMapping("/loan")
public class LoanController {

    private LoanService loanService;
    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    @GetMapping("/getAll")
    public @ResponseBody Iterable<Loan> getAll() {
        return loanService.getAll();
    }

    @GetMapping("/{id}")
    public Loan getOne(@PathVariable long id) {
        return loanService.getOne(id);
    }

    @PostMapping
    public ResponseEntity<Loan> create(@RequestBody Loan loan) {
        var newLoan = loanService.create(loan);
        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //borrowing
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(
            @RequestParam("userId") Long userId,
            @RequestParam("bookId") Long bookId,
            @RequestParam("borrowDate") Date borrowDate
    ) {
        // Call the borrow service method to borrow the book
        boolean success = loanService.loanBook(userId, bookId, borrowDate);

        if (success) {
            return ResponseEntity.ok("Book borrowed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to borrow book");
        }
    }
    @PostMapping("/return")
    public ResponseEntity<?> returnBook(
            @RequestParam("loanId") Long loanId,
            @RequestParam("returnDate") Date returnDate
    ) {
        // Call the borrow service method to borrow the book
        boolean success = loanService.returnBook(loanId, returnDate);

        if (success) {
            return ResponseEntity.ok("Book returned successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to return book");
        }
    }

}
