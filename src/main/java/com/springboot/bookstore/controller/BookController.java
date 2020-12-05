package com.springboot.bookstore.controller;


	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

import com.springboot.bookstore.entities.Book;
import com.springboot.bookstore.repository.BookRepository;

	

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public List<Book> getBookList() {
        return bookRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public Object getBook(@PathVariable long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            new ResponseEntity<>(HttpStatus.ACCEPTED);
          return book.get();
        } else {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allprices")
    public double calculatePrice() {
    ArrayList<Book> books;
    books= (ArrayList<Book>) bookRepository.findAll();
    double price = 0;
        for (Book var : books)
        {
            price+=var.price;
        }
    return price;
    }

    @PostMapping("/add")
    public void store(Book b) {
        try {
            bookRepository.save(b);
             new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update(@PathVariable Long id,@RequestBody Book bookDetails) {
            Optional<Book> b = bookRepository.findById(id);
            if (b.isPresent()){
                Book book=b.get();
                book.setTitle(bookDetails.title);
                book.setAuthor(bookDetails.author);
                book.setPrice(bookDetails.price);
                book.setReleasedate(bookDetails.releasedate);
                bookRepository.save(book);
                new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else{
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @PostMapping("/delete/{id}")
    public void deleteBook(@PathVariable long id) {
        try {
            bookRepository.deleteById(id);
            new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Exception e){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
