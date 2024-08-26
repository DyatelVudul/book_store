package com.training.book_store;

public record Book(Long id,
                   String title,
                   String author,
                   String isbn,
                   String genre,
                   String publisher,
                   Long amount,
                   Double price) {}