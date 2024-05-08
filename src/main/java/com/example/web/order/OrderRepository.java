package com.example.web.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteByMemberId(Long memberId);
    @Query("select o from Order o join fetch o.member m join fetch o.product p where m.id = :id")
    List<Order> findOrdersById(@Param("id") Long id);
}
