package com.demo.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.book.domain.Book;
import com.demo.book.dto.BookDto;
import com.demo.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public List<BookDto> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		
		ArrayList<BookDto> booksDto = new ArrayList();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		int length = books.size();
		for (int i = 0; i<length; i++) {
			BookDto bookDto = BookDto.builder()
								.bookId(books.get(i).getBookId())
								.name(books.get(i).getSummary())
								.summary(books.get(i).getSummary())
								.rating(books.get(i).getRating())
								.build();
			booksDto.add(bookDto);
		}
		
		return booksDto;
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<BookDto> findById(@PathVariable Long bookId) {
		Book book = bookService.findByBookId(bookId);
		BookDto bookDto = BookDto.builder().build();
		
		BeanUtils.copyProperties(book, bookDto);
		return ResponseEntity.ok(bookDto);
	}
	
	@PostMapping("/")
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
		Book book = Book.builder()
					.name(bookDto.getName())
					.summary(bookDto.getSummary())
					.rating(bookDto.getRating())
					.build();
		book = bookService.createBook(book);
		
		BookDto bkDto = BookDto.builder()
							.bookId(book.getBookId())
							.name(book.getName())
							.summary(book.getSummary())
							.rating(book.getRating())
							.build();
		
		return ResponseEntity.ok(bkDto);
	}
	
	@PutMapping("/{book-id}")
	public ResponseEntity<BookDto> createBook(@PathVariable(value="book-id") Long bookId, @RequestBody BookDto bookDto) {
		Book book = Book.builder()
					.bookId(bookId)
					.name(bookDto.getName())
					.summary(bookDto.getSummary())
					.rating(bookDto.getRating())
					.build();
		book = bookService.updateBook(book);
		
		BookDto bkDto = BookDto.builder()
				.bookId(book.getBookId())
				.name(book.getName())
				.summary(book.getSummary())
				.rating(book.getRating())
				.build();

		return ResponseEntity.ok(bkDto);
	}
	
	@DeleteMapping("/{book-id}")
	public ResponseEntity<String> deleteBook(@PathVariable(value="book-id") Long bookId) {

		Book book = bookService.deleteBook(bookId);
		
		if (book != null)
			return ResponseEntity.ok("Deleted");
		return ResponseEntity.ok("Not found ID:"+bookId);
	}	
}
