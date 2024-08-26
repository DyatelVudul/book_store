package com.training.book_store;

import org.springframework.data.annotation.Id;

public record Book(@Id Long id,
                   String title,
                   String author,
                   String isbn,
                   String genre,
                   String publisher,
                   Long amount,
                   Double price) {}