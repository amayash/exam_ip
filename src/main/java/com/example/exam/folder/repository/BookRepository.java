package com.example.exam.folder.repository;

import com.example.exam.folder.model.Book;
import com.example.exam.folder.model.BookExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book, Long> {
    @Query("Select sum(os.count) from CartBook os where os.book.id = :bookId")
    Optional<Integer> getCapacity(@Param("bookId") Long bookId);
    @Query("Select new com.example.exam.folder.model.BookExtension(s, sum(os.count)) from Book s " +
            "left join CartBook os on s.id = os.book.id " +
            "group by s")
    List<BookExtension> getBooksWithCapacity();
    @Query("Select new com.example.exam.folder.model.BookExtension(s, sum(os.count)) from Book s " +
            "left join CartBook os on s.id = os.book.id " +
            "where s.id = :bookId " +
            "group by s")
    Optional<BookExtension> getBookWithCapacity(@Param("bookId") Long bookId);

    @Query("select p from Book p where p.name like CONCAT('%',:search,'%')")
    List<Book> findAll(@Param("search") String search);

    @Query("select p from Book p where p.category.id=:search")
    List<Book> findAll(@Param("search") Integer search);

    List<Book> findAllByAuthorsId(Long id);
}
