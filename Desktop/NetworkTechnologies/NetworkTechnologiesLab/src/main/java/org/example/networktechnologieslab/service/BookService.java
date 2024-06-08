package org.example.networktechnologieslab.service;

import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.example.networktechnologieslab.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    public Book getOne(long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book create(Book book){
        return bookRepository.save(book);
    }

    public void delete(long id){
        bookRepository.deleteById(id);
    }

    public Iterable<Book> getBookByName(String title) {
        return bookRepository.findByTitle(title);

    }
    public Book updateBook(Book updatedBook, long id) {
        // verifying book exists
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublisher(updatedBook.getPublisher());
        existingBook.setYear(updatedBook.getYear());
        existingBook.setIsbn(updatedBook.getIsbn());

        return bookRepository.save(existingBook);
    }

}
