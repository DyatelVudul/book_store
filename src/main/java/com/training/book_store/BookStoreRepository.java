package com.training.book_store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/book-store/library")
public class BookStoreRepository {

    private final BookStoreController bookStoreController;

    public BookStoreRepository(BookStoreController bookStoreController) {
        this.bookStoreController = bookStoreController;
    }

    @GetMapping("/search/id/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Optional<Book> book = bookStoreController.findById(bookId);

        if(book.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book.get());
    }

    @GetMapping("/search/isbn/{bookIsbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String bookIsbn) {
        Book book = bookStoreController.findByIsbn(bookIsbn);

        if(book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Book>> getAllBooks(Pageable pageable) {
        Page<Book> allBooks = bookStoreController.findAll(pageable);
        return ResponseEntity.ok(allBooks.getContent());
    }

    @GetMapping("/search/title/{bookTitle}")
    public ResponseEntity<Iterable<Book>> getBooksByTitle(@PathVariable String bookTitle,
                                                          Pageable pageable) {
        Page<Book> books = bookStoreController.findByTitle(bookTitle,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))
                )
        );

        return ResponseEntity.ok(books.getContent());
    }

    @GetMapping("/search/author/{bookAuthor}")
    public ResponseEntity<Iterable<Book>> getBooksByAuthor(@PathVariable String bookAuthor, Pageable pageable) {
        Page<Book> books = bookStoreController.findByAuthor(bookAuthor,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))
                )
        );

        return ResponseEntity.ok(books.getContent());
    }

    @GetMapping("/search/genre/{bookGenre}")
    public ResponseEntity<Iterable<Book>> getBooksByGenre(@PathVariable String bookGenre, Pageable pageable) {
        Page<Book> books = bookStoreController.findByGenre(bookGenre,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC,"title"))
                )
        );

        return ResponseEntity.ok(books.getContent());
    }

    @GetMapping("/search/publisher/{bookPublisher}")
    public ResponseEntity<Iterable<Book>> getBooksByPublisher(@PathVariable String bookPublisher, Pageable pageable) {
        Page<Book> books = bookStoreController.findByPublisher(bookPublisher,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))
                )
        );
        return ResponseEntity.ok(books.getContent());
    }

    @PostMapping("maintain/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book, Principal principal) {
        if(principal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookStoreController.save(book));
    }

    @DeleteMapping("maintain/remove")
    public ResponseEntity<Book> deleteBook(@RequestBody Book book, Principal principal) {
        return ResponseEntity.ofNullable(null);
    }

    @PutMapping("maintain/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, Principal principal) {
        return ResponseEntity.ok(bookStoreController.save(book));
    }


}