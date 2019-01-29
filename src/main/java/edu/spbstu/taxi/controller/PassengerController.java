package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Passenger;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class PassengerController {
    @Autowired
    ServiceImpl service;

    @RequestMapping("rest/passenger/{login}/authenticate")
    public String authenticate(@PathVariable String login, @RequestParam("passwd") String passwd) {
        if (service.authenticate(login, passwd) != 1)
            return "false";
        return "true";
    }

    @RequestMapping("rest/passenger/{login}/new_order")
    public boolean newOrder(@PathVariable String login, @RequestParam("srcAddr") String srcAddr,//TODO:: addr in query???? move to body
                            @RequestParam("dstAddr") String dstAddr) {
        try {
            service.addNewOrder(srcAddr, dstAddr, login, LocalDate.now());
        }
        catch (HaveNotUserEx ex){
            return false;
        }
        return true;
    }

    @RequestMapping(value="rest/passenger/{login}", method = RequestMethod.POST)
    public void newPassenger(@PathVariable String login, @RequestBody Passenger passenger) {
        service.addNewPassenger(passenger);
    }

}
