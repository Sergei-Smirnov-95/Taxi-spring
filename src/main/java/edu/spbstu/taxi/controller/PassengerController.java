package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class PassengerController {
    @Autowired
    ServiceImpl service;

    @RequestMapping("rest/passenger/{login}/authenticate")
    public boolean authenticate(@PathVariable String login, @RequestParam("passwd") String passwd) {
        if (service.authenticate(login, passwd) != 1)
            return false;
        return true;
    }

    @RequestMapping("rest/passenger/{login}/new_order")
    public boolean newOrder(@PathVariable String login, @RequestParam("srsAddr") String srcAddr,
                            @RequestParam("dstAddr") String dstAddr) {
        try {
            service.addNewOrder(srcAddr, dstAddr, login, LocalDate.now());
        }
        catch (HaveNotUserEx ex){
            return false;
        }
        return true;
    }

    @RequestMapping("rest/passenger/{login}/new")
    public void newPassenger(@PathVariable String login,
                            @RequestParam("passwd") String passwd,
                            @RequestParam("name") String name,
                            @RequestParam("email") String email,
                            @RequestParam("phone") String phone) {
        service.addNewPassenger(1, login, passwd, name, email, phone);
    }

}
