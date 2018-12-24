package edu.spbstu.taxi;

import edu.spbstu.taxi.Exceptions.DBConnectionException;
import edu.spbstu.taxi.service.ServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceImpl.class)
public class TestTaxi {
    @Autowired
    ServiceImpl facade;

    @Test
    public void Test(){
        try {
            System.out.println(facade.authenticate("op1", "1111"));
        } catch (DBConnectionException e) {
            System.out.println("bggggg");
        }
    }
}
