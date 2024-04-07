package org.example.networktechnologieslab.infrastructure.entity;

import jakarta.persistence.*;

import java.util.Set;

/*
Variables were created according to the database structure in the project proposal page*/

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy  = GenerationType.AUTO)
    private Long bookId;

    @Column(name="isbn")
    @Basic
    private String isbn;

    @Column(name="title")
    @Basic
    private String title;

    @Column(name="author")
    @Basic
    private String author;

    @Column(name="publisher")
    @Basic
    private String publisher;
    @Column(name="year")
    @Basic
    private Long year;
    @Column(name="available_copies")
    @Basic
    private Long availableCopies;

    // relationship as defined in project proposal


    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Long availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Long getBookId(){
        return bookId;
    }



}