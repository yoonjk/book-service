package com.demo.book.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.book.TestConfig;
import com.demo.book.dto.BookDto;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	public void findByBook() {
		// given
		long bookId = 1L;
		
		// when
		ResponseEntity<BookDto> response = restTemplate.getForEntity("/book/{id}", BookDto.class, bookId);
		
		log.info("response:{}", response);
		
		// then
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void createBook() {
		// given
		BookDto bookDto = BookDto.builder() 
							.name("mastring python")
							.summary("Mastering python development")
							.rating(5)
							.build();
		
		// when
		ResponseEntity<BookDto> response = restTemplate.postForEntity("/book/", bookDto, BookDto.class);
		
		// then
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void updateBook() {
		// given
		BookDto bookDto = BookDto.builder() 
				.bookId(3L)
				.name("mastring python")
				.summary("Mastering python development")
				.rating(2)
				.build();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "3");
		
        HttpEntity<BookDto> request = new HttpEntity<BookDto>(bookDto);
        String url = "/book/{id}";

        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(params)
                .toUri();
        
        // For queryParam
        uri = UriComponentsBuilder
        .fromUri(uri)
        .queryParam("name", "myName")
        .encode()
        .build()
        .toUri();
        
//        UriComponents complexUrl = UriComponentsBuilder
//                .fromUriString("http://localhost:8080/users/{nickname}")
//                .uriVariables(Map.of("nickname", "dailycode"))
//                .queryParam("userName", "한글이름")
//                .queryParam("userId", "nobody")
//                .queryParam("age", 30) 
//                // String 뿐만 아니라 int, boolan, double 등의 값도 사용할 수 있다!
//                .encode().build(); 
//                // Tip: encode() 해주면 toUriString 사용시 한글에 대한 URL 엔코딩도 해준다.        
        
		// when
        ResponseEntity<BookDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, BookDto.class, params);

		// then
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void deleteByBookId() {
		// when
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "3");
		
        String url = "/book/{id}";		
		
		// when
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY,String.class, params);

		// then
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
