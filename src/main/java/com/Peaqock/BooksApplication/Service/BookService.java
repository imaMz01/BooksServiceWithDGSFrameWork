package com.Peaqock.BooksApplication.Service;

import com.Peaqock.BooksApplication.Dto.BookRequestDto;
import com.Peaqock.BooksApplication.Entity.Book;

import java.util.List;

public interface BookService {

    public Book add(Book book);
    public List<Book> list();
    public Book findById(String id);
    public void delete(String id);
    public Book update(BookRequestDto bookRequestDto,String id);
}
