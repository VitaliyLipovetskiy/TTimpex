package com.baeldung.boot.jsp.exception;

import com.baeldung.boot.jsp.dto.Book;

public class DuplicateBookException extends RuntimeException {
    private final Book book;

    public DuplicateBookException(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}