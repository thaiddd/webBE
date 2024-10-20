package com.store.backend.Repository;

import com.store.backend.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getByUser_IdAndState(Long id, String state);

    List<Order> findByState(String state);

    Order getOrderById(Long id);

    @Query(value = "CALL Proc_ReportOrderQuantity(:year)", nativeQuery = true)
    List<Map<String, ?>> getReportOrderCount(@Param("year") String year);
    @Query(value = "select o from Order o where o.isDelete = 0")
    List<Order>findAll();
}
