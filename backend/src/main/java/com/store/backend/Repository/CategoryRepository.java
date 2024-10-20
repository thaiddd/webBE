package com.store.backend.Repository;

import com.store.backend.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "select * from category where id =?",nativeQuery = true)
    public Category searchCategoriById(Long Id);
}
