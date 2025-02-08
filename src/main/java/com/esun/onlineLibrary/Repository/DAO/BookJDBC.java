package com.esun.onlineLibrary.Repository.DAO;

import com.esun.onlineLibrary.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BookJDBC {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<Book> findByNameContaining(String name){
        String sql = "CALL find_Book_By_Name(:name);";
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        return jdbc.query(sql,map,new BeanPropertyRowMapper<>(Book.class));
    }

    public Book findByIsbn(String isbn){
        String sql = "CALL find_Book_By_Isbn(:isbn);";
        HashMap<String, Object> map = new HashMap<>();
        map.put("isbn", isbn);
        return jdbc.query(sql,map,new BeanPropertyRowMapper<>(Book.class)).get(0);
    }
}
