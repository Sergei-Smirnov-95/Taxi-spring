package edu.spbstu.taxi.repository;

import edu.spbstu.taxi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
