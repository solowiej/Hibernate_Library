package com.javagda25.library.model;


import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book implements IBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate yearWritten;

    @Formula(value = "(year(now()) - year(yearWritten))")
    private int howOld;

    private int numberOfPages;
    private int numberOfAvaibleCopies;

    @Formula("(select count(*) from BookLent bl where bl.book_id=id and bl.dateReturned is null)")
    private int numberOfBorrowedCopies;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book")
    private Set<BookLent> currentLents;


}
