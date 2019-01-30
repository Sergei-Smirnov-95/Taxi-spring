package edu.spbstu.taxi.repository;

import edu.spbstu.taxi.entity.Order;
import edu.spbstu.taxi.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public interface OrderRepository extends JpaRepository<Order, Integer> {
    /*@Query("select U from Orders U where U.passId = :passId")
    List<Order> findByPassengerID(@Param("passId") int passId);*/

}
