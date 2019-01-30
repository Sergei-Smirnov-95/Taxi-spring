package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Driver;
import edu.spbstu.taxi.entity.Order;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {
    @Autowired
    ServiceImpl service;

    @RequestMapping("rest/driver/{login}/authenticate")
    public String authenticate(@PathVariable String login, @RequestParam("passwd") String passwd) {
        if (service.authenticate(login, passwd) != 2)
            return "false";
        return "true";
    }

    //TODO::new driver
    @RequestMapping(value="rest/driver/{login}", method = RequestMethod.POST)
    public void newDriver(@PathVariable String login, @RequestBody Driver driver) {
        service.addNewDriver(driver);
    }

    @RequestMapping("rest/driver/{login}/accept")
    public void accept(@PathVariable String login, @RequestParam("orderID") int orderID) throws HaveNotUserEx, HaveNotOrderEx {
        service.acceptRequest(orderID, login);
    }

    @RequestMapping("rest/driver/{login}/decline")
    public void decline(@PathVariable String login, @RequestParam("orderID") int orderID) throws HaveNotUserEx, HaveNotOrderEx {
        service.declineRequest(orderID, login);
    }

    @RequestMapping(value="rest/driver/{login}/newOrders", method = RequestMethod.GET)
    public List<Order> getNewOrders(@PathVariable String login)throws HaveNotOrderEx,HaveNotUserEx{
        return service.getAppointedOrdersByDriver(login);
    }

    @RequestMapping(value="rest/driver/{login}/oldOrders", method = RequestMethod.GET)
    public List<Order> getOldOrders(@PathVariable String login)throws HaveNotOrderEx,HaveNotUserEx{
        return service.getOrdersByDriver(login);
    }
}
