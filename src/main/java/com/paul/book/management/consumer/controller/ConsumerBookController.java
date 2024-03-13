package com.paul.book.management.consumer.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.book.management.api.entity.Book;
import com.paul.book.management.api.service.IBookService;

@RestController
@RequestMapping("/book")
public class ConsumerBookController {
	
	//version必须匹配
    @DubboReference(version = "1.0.0", protocol = "dubbo", 
    		loadbalance = "random")
    private IBookService bookService;	

    @GetMapping(value="/{id}")
    public List<Book> getBooks(@PathVariable("id")String id){
    	System.out.println("book id for searching: " + id);
    	List<Book> result = null;
    	result = bookService.getBooks(id);
    	return result;
    }
    
    @GetMapping
    public List<Book> getAllBooks(){
    	List<Book> result = null;
    	result = bookService.getAllBooks();
    	return result;
    }
    
    @PostMapping(value="")
    public String createBook(@RequestBody Book book) {
    	String result = "Create book successfully.";
    	if (bookService.getBook(book.getId()) == null)
    		bookService.createBook(book);
    	else
    		result = "The book id can not be duplicate, please input a unique book id.";
    	return result;
    }
    
    @PutMapping(value="/{id}")
    public String updateBook(@PathVariable String id, @RequestBody Book book) {
        System.out.println("Updating book: " + book);
    	bookService.updateBook(book);
    	return "Update book successfully.";
    }
    
    @DeleteMapping("/{id}")  
    public String deleteBook(@PathVariable String id) {
    	System.out.println("bookId: " + id);
        bookService.deleteBook(id);
        return "Delete book " + id + " successfully";
    }       

}
