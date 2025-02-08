package com.esun.onlineLibrary.Repository;

import com.esun.onlineLibrary.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByBookIsbn(String isbn);

    boolean existsByBookIsbn(String isbn);
}

