package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Order;
import edu.spbstu.taxi.entity.Passenger;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public boolean newOrder(@PathVariable String login, @RequestParam("srcAddr") String srcAddr,
                            @RequestParam("dstAddr") String dstAddr) {
        try {
            service.addNewOrder(srcAddr, dstAddr, login, LocalDateTime.now());
        }
        catch (HaveNotUserEx ex){
            return false;
        }
        return true;
    }

    @GetMapping("rest/passenger/{login}/orders")
    public @ResponseBody
    List<Order> getOrdersByPass(@PathVariable String login){
        try {
            return service.getOrdersByPassenger(login);
        } catch (HaveNotOrderEx ex){
            return new ArrayList<Order>();
        } catch (HaveNotUserEx ex){
            return new ArrayList<Order>();
        }
    }

    @RequestMapping(value="rest/passenger/{login}", method = RequestMethod.POST)
    public void newPassenger(@PathVariable String login, @RequestBody Passenger passenger) {
        service.addNewPassenger(passenger);
    }

}
