package org.example.networktechnologieslab.controller;

import org.example.networktechnologieslab.infrastructure.entity.Book;
import org.example.networktechnologieslab.infrastructure.repository.BookRepository;
import org.example.networktechnologieslab.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

//    private final BookRepository bookRepository;
//    @Autowired
//    public BookController(BookRepository bookRepository){
//        this.bookRepository = bookRepository;
//    }


//    @PostMapping("/addBook")
//    @ResponseStatus(code = HttpStatus.CREATED) //code 201
//    public @ResponseBody Book addBook(@RequestBody Book book){
//        return bookRepository.save(book);
//    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<Book> getAll(){
        return bookService.getAll();
    }

    @GetMapping("/{id}") // if i wanna access then i need /book/id (their personal id)
        public Book getOne(@PathVariable long id){
            return bookService.getOne(id);
        }

    @PostMapping // the default - the book
    @PreAuthorize("hasRole('ROLE_ADMIN')")
        public ResponseEntity<Book> create(@RequestBody Book book){
            var newBook = bookService.create(book);
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Void> delete(@PathVariable long id){
        bookService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    // searching for book by name
    @GetMapping("/getByName")
    public ResponseEntity<?> getBookByName(@RequestParam("title") String title) {
        Iterable<Book> books = bookService.getBookByName(title);
        if (books.iterator().hasNext()) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@RequestBody Book updatedBook, @PathVariable long id) {
        Book updated = bookService.updateBook(updatedBook, id);
        return ResponseEntity.ok(updated);
    }


}
