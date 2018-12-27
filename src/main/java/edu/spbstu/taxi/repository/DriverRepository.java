package edu.spbstu.taxi.repository;

import edu.spbstu.taxi.entity.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public interface DriverRepository extends UserRepository<Driver> {

    @Query("select U from Driver U where U.login = :login and TypeUser = 2")
    Optional<Driver> getDriverByLogin(@Param("login") String login);

}
