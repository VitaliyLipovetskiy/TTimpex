package com.baeldung.boot.jsp.repository;

import com.baeldung.boot.jsp.repository.model.BookData;

import java.util.Collection;
import java.util.Optional;

public interface BookRepository {
    Collection<BookData> findAll();

    Optional<BookData> findById(String isbn);

    BookData add(BookData book);
}
