package com.training.book_store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookStoreController extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

    Book findByIsbn(String isbn);

    Page<Book> findByTitle(String title, PageRequest amount);

    Page<Book> findByAuthor(String author, PageRequest amount);

    Page<Book> findByGenre(String genre, PageRequest amount);

    Page<Book> findByPublisher(String publisher, PageRequest amount);
}
