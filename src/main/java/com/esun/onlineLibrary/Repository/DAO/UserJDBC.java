package com.esun.onlineLibrary.Repository.DAO;

import com.esun.onlineLibrary.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserJDBC {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByPhoneNumber(String phoneNumber) {
        String sql = "CALL find_User_By_Phone_Number(:phone)";
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phoneNumber);
        return jdbc.query(sql,map,new BeanPropertyRowMapper<>(User.class)).get(0);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        String sql = "CALL exists_User_By_Phone_Number(:phone, @exists)";

        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phoneNumber);
        jdbc.update(sql, map);
        try{
            return jdbcTemplate.queryForObject("SELECT @result", Boolean.class);
        }
        catch (Exception e){
            return false;
        }
    }
}
