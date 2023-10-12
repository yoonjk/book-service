package com.demo.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.book.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
