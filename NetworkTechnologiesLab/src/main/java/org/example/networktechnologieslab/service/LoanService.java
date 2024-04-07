package org.example.networktechnologieslab.service;
import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.example.networktechnologieslab.infrastructure.entity.Loan;
import org.example.networktechnologieslab.infrastructure.entity.User;
import org.example.networktechnologieslab.infrastructure.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public LoanService(LoanRepository loanRepository,UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.loanRepository = loanRepository;}
    
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    public Loan getOne(long id) {
        return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public Loan create(Loan loan) {
        return loanRepository.save(loan);
    }

    public void delete(long id) {
        loanRepository.deleteById(id);
    }

    public Loan updateLoan(Long loanId, Loan updatedLoan) {
        // Retrieve the existing loan from the database
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan existingLoan = optionalLoan.get();

            // Update the fields of the existing loan with the values from the updated loan
            existingLoan.setBook(updatedLoan.getBook());
            existingLoan.setUser(updatedLoan.getUser());
            existingLoan.setLoanDate(updatedLoan.getLoanDate());
            existingLoan.setDueDate(updatedLoan.getDueDate());
            existingLoan.setReturnDate(updatedLoan.getReturnDate());

            // Save and return the updated loan
            return loanRepository.save(existingLoan);
        } else {
            // Handle case where loan with given ID is not found
            throw new RuntimeException("Loan not found");
        }
    }

    public boolean loanBook(Long userId, Long bookId, Date borrowDate) {
        // switching to localdate to be able to calculate return period
        LocalDate loanLocalDate = borrowDate.toLocalDate();

        // checking if loan is possible:
        User user = userService.getOne(userId);
        if (user == null) {
            return false;
        }
        Book book = bookService.getOne(bookId);
        if (book == null) {
            return false;
        }

        if (book.getAvailableCopies() <= 0) {
            return false;
        }


        LocalDate localDueDate = loanLocalDate.plus(60, ChronoUnit.DAYS); //adding 60 days to loan date
        Date dueDate = Date.valueOf(localDueDate); // switching to date.sql from localDate

        // adding loan
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(borrowDate);
        loan.setDueDate(dueDate);
        loanRepository.save(loan);

        // Updating copies of the book
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.updateBook(book, bookId);

        return true; // return success status response
    }
    public boolean returnBook(Long loanId, Date returnDate) {
        // book is needed to get book id to be able to update the books table in the database
        Loan loan = getOne(loanId);
        long bookId = loan.getBook().getBookId();
        Book book = bookService.getOne(bookId);
        if (loan == null) {
            return false;
        }

        loan.setReturnDate(returnDate);
        updateLoan(loanId,loan);

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.updateBook(book, bookId);

        return true;
    }
}
