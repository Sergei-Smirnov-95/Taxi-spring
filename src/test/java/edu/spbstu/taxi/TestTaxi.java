package edu.spbstu.taxi;

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
    public void TestAuthorization(){
        try {
            assert(facade.authenticate("op1", "111")==0);
            assert(facade.authenticate("us1", "111")==1);
            assert(facade.authenticate("dr1", "111")==2);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
