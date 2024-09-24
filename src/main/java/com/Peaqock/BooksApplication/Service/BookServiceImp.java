package com.Peaqock.BooksApplication.Service;

import com.Peaqock.BooksApplication.Entity.Book;
import com.Peaqock.BooksApplication.Repository.BookRepository;
import com.Peaqock.BooksApplication.codegen.types.BookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    public final BookRepository bookRepository;


    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> list() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Book with id %s not found",id))
        );
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book update(BookRequestDto bookRequestDto, String id) {
        Book bookToSave = findById(id);
        bookToSave.setAuthor(bookRequestDto.getAuthor());
        bookToSave.setTitle(bookRequestDto.getTitle());
        return bookRepository.save(bookToSave);
    }
}
