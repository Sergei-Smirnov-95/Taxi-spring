package edu.spbstu.taxi.repository;

import edu.spbstu.taxi.entity.Passenger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public interface PassengerRepository extends UserRepository<Passenger> {
    @Query("select U from Passenger U where U.login = :login and TypeUser = 1")
    Optional<Passenger> getPassengerByLogin(@Param("login") String login);

}
