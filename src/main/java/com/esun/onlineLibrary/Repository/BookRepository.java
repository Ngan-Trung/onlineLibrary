package com.esun.onlineLibrary.Repository;

import com.esun.onlineLibrary.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByNameContaining(String name);

    List<Book> findByAuthor(String author);

}

