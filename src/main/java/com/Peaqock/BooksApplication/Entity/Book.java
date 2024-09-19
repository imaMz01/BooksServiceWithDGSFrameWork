package com.Peaqock.BooksApplication.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
}
