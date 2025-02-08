package com.esun.onlineLibrary.Repository.DAO;

import com.esun.onlineLibrary.Model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class InventoryJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Inventory findByBookIsbn(String isbn){
        String sql = "CALL find_User_By_Phone_Number(:isbn)";
        HashMap<String, Object> map = new HashMap<>();
        map.put("isbn", isbn);
        return jdbc.query(sql,map,new BeanPropertyRowMapper<>(Inventory.class)).get(0);
    }

    public boolean existsByBookIsbn(String isbn){
        String sql = "CALL exists_Inventory_By_Isbn(:isbn, @exists)";

        HashMap<String, Object> map = new HashMap<>();
        map.put("isbn", isbn);
        jdbc.update(sql, map);

        return jdbcTemplate.queryForObject("SELECT @result", Boolean.class);
    }
}
