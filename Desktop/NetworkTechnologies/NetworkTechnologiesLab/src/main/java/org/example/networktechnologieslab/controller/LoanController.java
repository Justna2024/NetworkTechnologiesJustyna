package org.example.networktechnologieslab.controller;

import org.example.networktechnologieslab.controller.datatransferobjects.BorrowDto;
import org.example.networktechnologieslab.controller.datatransferobjects.LoanDto;
import org.example.networktechnologieslab.controller.datatransferobjects.ReturnDto;
import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.example.networktechnologieslab.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

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

    @GetMapping("/getByUserId")
    public List<Loan> getByUserId(@RequestParam("userId") Long userId) {
        return loanService.getByUserId(userId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Loan> create(@RequestBody LoanDto loanDto) {



        var newLoan = loanService.create(loanDto.getBookId(), loanDto.getUserId(),
                loanDto.getLoanDate(), loanDto.getDueDate(), loanDto.getReturnDate());


        return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //borrowing
    @PostMapping("/borrow")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowDto requestBody)
    {
        // Call the borrow service method to borrow the book
        boolean success = loanService.loanBook(requestBody);

        if (success) {
            return ResponseEntity.ok("Book borrowed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to borrow book");
        }
    }
    @PostMapping("/return")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> returnBook(@RequestBody ReturnDto requestBody) {
        // Call the borrow service method to borrow the book
        boolean success = loanService.returnBook(requestBody);

        if (success) {
            return ResponseEntity.ok("Book returned successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to return book");
        }
    }

}
