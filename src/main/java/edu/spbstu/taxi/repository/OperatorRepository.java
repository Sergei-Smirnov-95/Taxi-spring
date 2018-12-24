package edu.spbstu.taxi.repository;

import edu.spbstu.taxi.entity.Operator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OperatorRepository extends UserRepository<Operator> {

    @Query("select U from Operator U where U.login = :login and TypeUser = 0")
    Optional<Operator> getOperatorByLogin(@Param("login") String login);

}
