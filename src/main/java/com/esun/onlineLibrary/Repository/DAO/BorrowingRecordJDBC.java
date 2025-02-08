package com.esun.onlineLibrary.Repository.DAO;

import com.esun.onlineLibrary.Model.BorrowingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BorrowingRecordJDBC {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<BorrowingRecord> findByUserId(Long userId){
        String sql = "CALL find_Record_By_User(:userId);";
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return jdbc.query(sql,map,new BeanPropertyRowMapper<>(BorrowingRecord.class));
    }

    public BorrowingRecord findByInventoryIdAndReturnTimeIsNULL(Long id){
        String sql = "CALL find_Record_By_Inventory(:id);";
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return jdbc.query(sql,map,new BeanPropertyRowMapper<>(BorrowingRecord.class)).get(0);
    }
}
