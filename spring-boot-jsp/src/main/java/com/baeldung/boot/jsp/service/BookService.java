package com.baeldung.boot.jsp.service;

import com.baeldung.boot.jsp.dto.Book;

import java.util.Collection;

public interface BookService {
    Collection<Book> getBooks();

    Book addBook(Book book);
}