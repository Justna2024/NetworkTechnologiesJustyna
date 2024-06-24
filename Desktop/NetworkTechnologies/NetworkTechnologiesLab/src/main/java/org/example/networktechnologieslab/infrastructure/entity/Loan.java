package org.example.networktechnologieslab.infrastructure.entity;

import jakarta.persistence.*;

import java.sql.Date;


/*
Variables were created according to the database structure in the project proposal page*/
@Entity
@Table(name="loan", schema = "library")
public class Loan {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loanId;

    @ManyToOne // use relationships to declare foreign keys
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "loan_date")
    @Basic
    private Date loanDate;

    @Column(name = "due_date")
    @Basic
    private Date dueDate;

    @Column(name = "return_date")
    @Basic
    private Date returnDate;


    // getters and setters
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
