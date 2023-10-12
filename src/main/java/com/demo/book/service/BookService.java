package com.demo.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.book.domain.Book;
import com.demo.book.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	/**
	 * All getBook
	 * @return
	 */
	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		
		return books;
	}
	
	/**
	 * Find bookId
	 * @param bookId
	 * @return
	 */
	public Book findByBookId(Long bookId) {
		Optional<Book> optionalBook = bookRepository.findById(bookId);
		
		return optionalBook.map(book -> {
			return book;
		}).orElseThrow(() ->  new RuntimeException("bookId not found"));
//		if (!optionalBook.isPresent()) {
//			throw new RuntimeException("bookId not found");
//		}
//		return optionalBook.get();
	}
	
	/**
	 * Create Book
	 * @param book
	 * @return
	 */
	public Book createBook(Book book) {
		Book bk = bookRepository.save(book);
		
		return bk;
	}
	
	/**
	 * Update Book
	 * @param book
	 * @return
	 */
	public Book updateBook(Book book) {
		Book bk = findByBookId(book.getBookId());
		log.info("book:{}", book);
		bk = bookRepository.save(book);
		
		return bk;
	}	
	
	/**
	 * Delete Book
	 * @param bookId
	 * @return
	 */
	public Book deleteBook(Long bookId) {
		Book bk = findByBookId(bookId);
		
		if (bk != null)
			bookRepository.delete(bk);
		
		return bk;
	}
}
