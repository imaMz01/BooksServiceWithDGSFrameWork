package com.Peaqock.BooksApplication.Controller;

import com.Peaqock.BooksApplication.Entity.Book;
import com.Peaqock.BooksApplication.Service.BookService;
import com.Peaqock.BooksApplication.codegen.types.BookRequestDto;
import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.Argument;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DgsComponent
@RequiredArgsConstructor
public class BookDGSController {

    public final BookService bookService;
    //private final Sinks.Many<Book> sinks = Sinks.many().multicast().onBackpressureBuffer();
    private final List<Sinks.Many<Book>> sinks = new ArrayList<>();
    @DgsQuery
    public List<Book> allBooks(){
        return bookService.list();
    }

    @DgsQuery
    public Book bookById(@Argument String id){
        return bookService.findById(id);
    }

    @DgsMutation
    public Book addBook(@Argument BookRequestDto bookRequestDto){
        Book book = Book.builder()
                .id(UUID.randomUUID().toString())
                .title(bookRequestDto.getTitle())
                .author((bookRequestDto.getAuthor()))
                .build();
        sinks.forEach(
                sink -> sink.tryEmitNext(book)
        );
       // sinks.tryEmitNext(book);
        return bookService.add(book);
    }

    @DgsMutation
    public Book updateBook(@Argument BookRequestDto bookRequestDto,@Argument String id){
        return bookService.update(bookRequestDto,id);
    }

    @DgsMutation
    public void deleteBook(@Argument String id){
         bookService.delete(id);
    }

    @DgsSubscription
    public Publisher<Book> publisherBook(){
        Sinks.Many<Book> sink = Sinks.many().multicast().onBackpressureBuffer();
        sinks.add(sink);
        return sink.asFlux();
    }
}
