package com.paul.book.management.consumer.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.paul.book.management.api.entity.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class ConsumerBookControllerTest {
	
	@Autowired
	ConsumerBookController bookController;
	
	private static final String BOOK_ID = "book1";

	@Test
	public void test01CreateBook() {
		String result = null;
		Book book = new Book();
		book.setId(BOOK_ID);
		book.setBookName("西游记");
		book.setAuthor("吴承恩");
		try {
			result = bookController.createBook(book);
		} catch (Exception e) {
			assertThrows("Fail to add a book by IBookService.createBook('book1')", 
					DuplicateKeyException.class, 
					() -> {  
						bookController.createBook(book);  
			        });
		}
		assertTrue("No message returned by ConsumerBookController.createBook(Book), " +
				"please check.", Optional.ofNullable(result).isPresent());		
	}
	
	@Test
	public void test02GetBooks() {
		String bookId = BOOK_ID;
		String sBooks = bookController.getBooks(bookId);
		List<Book> bookList = JSON.parseArray(sBooks, Book.class);
		assertTrue("No records returned by IBookService.getBooks(bookId), " +
				" please add some books first.", 
				bookList.size()>0);
	}
	
	@Test
	public void test04GetAllBooks() {
		String sBooks = bookController.getAllBooks();
		List<Book> bookList = JSON.parseArray(sBooks, Book.class);
		assertTrue("No records returned by IBookService.getAllBooks(), " +
				" please add some books first.", 
				bookList.size()>0);
	}
	
	@Test
	public void test05UpdateBook() {
		String bookId = BOOK_ID;
		Book book = new Book();
		book.setId(bookId);
		book.setBookName("红楼梦1");
		book.setAuthor("曹雪芹");
		String result = bookController.updateBook(book.getId(), book);
		assertTrue("No message returned by ConsumerBookController.updateBook(bookId, Book), " +
				"please check.", Optional.of(result).isPresent());
	}	
	
	@Test
	public void test06DeleteBook() {
		String bookId = BOOK_ID;
		String result = bookController.deleteBook(bookId);
		assertTrue("No message returned by ConsumerBookController.deleteBook(bookId), " +
				"please check.", Optional.of(result).isPresent());
	}
}
