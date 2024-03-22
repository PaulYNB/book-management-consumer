package com.paul.book.management.consumer.controller;

import java.util.List;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.paul.book.management.api.entity.Book;
import com.paul.book.management.api.service.IBookService;

@RestController
@RequestMapping("/book")
public class ConsumerBookController {
	
	private static Logger logger = LogManager.getLogger(ConsumerBookController.class);
	
	//version必须匹配
    @DubboReference(version = "1.0.1", protocol = "dubbo", 
    		loadbalance = "random")
    private IBookService bookService;
    
    @GetMapping(value="/{id}")
    public String getBooks(@PathVariable("id")String id) {
    	List<Book> result = bookService.getBooks(id);
    	logger.info("GET: Got books via book id '" + id + "': " + result);
    	return JSON.toJSONString(result);
    }
    
    @GetMapping
    public String getAllBooks(){
    	List<Book> result = null;
    	result = bookService.getAllBooks();
    	logger.info("GET: Got all books: " + result);
    	return JSON.toJSONString(result);
    }
    
    @PostMapping(value="")
    public String createBook(@RequestBody Book book) {
    	String result = "Create book successfully.";
    	int cnt = 0;
    	try {
    		cnt = bookService.createBook(book);
    	} catch (DuplicateKeyException e) {
    		logger.warn("CREATE: Fail to add a book: " + e.getMessage());
    		result = "The book id can not be duplicate, please input a unique book id.";
    	}
    	if (cnt > 0)
    		logger.info("CREATE: Created book: " + book);
    	return result;
    }
    
    @PutMapping(value="/{id}")
    public String updateBook(@PathVariable String id, @RequestBody Book book) {
    	String result = "Update book successfully.";
    	int cnt = bookService.updateBook(book);
    	if (cnt > 0)
	    	logger.info("UPDATE: The book the id of which is '" + book.getId() +
					"' has been updated, the result is: " + book);
    	else {
    		String str = "UPDATE: No book is updated via book: " + book;
    		logger.warn(str);
    		result = str;
    	}
    	return result;
    }
    
    @DeleteMapping("/{id}")  
    public String deleteBook(@PathVariable String id) {
    	String result = "Delete book " + id + " successfully";
        int cnt = bookService.deleteBook(id);
        if (cnt > 0)
        	logger.info("DELETE: Deleted the book the id of which is " + id);
        else {
    		String str = "DELETE: No book is deleted by book id '" + id + "'.";
    		logger.warn(str);
    		result = str;        	
        }
        return result;
    }      

}
