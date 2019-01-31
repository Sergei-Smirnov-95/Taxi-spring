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
    @RequestMapping(value = "rest/driver/{login}", method = RequestMethod.POST)
    public void newDriver(@PathVariable String login, @RequestBody Driver driver) {
        service.addNewDriver(driver);
    }

    @RequestMapping(value = "rest/driver/{login}/accept", method = RequestMethod.GET)
    public String accept(@PathVariable String login, @RequestParam("orderID") int orderID){
        try {
            service.acceptRequest(orderID, login);
            return "true";
        } catch (HaveNotOrderEx ex) {
            return "false";
        } catch (HaveNotUserEx ex) {
            return "false";
        }
    }

    @RequestMapping(value = "rest/driver/{login}/decline", method = RequestMethod.GET)
    public String decline(@PathVariable String login, @RequestParam("orderID") int orderID) {
        try {
            service.declineRequest(orderID, login);
            return "true";
        } catch (HaveNotOrderEx ex) {
            return "false";
        } catch (HaveNotUserEx ex) {
            return "false";
        }
    }

    @RequestMapping(value = "rest/driver/{login}/newOrders", method = RequestMethod.GET)
    public List<Order> getNewOrders(@PathVariable String login) throws HaveNotOrderEx, HaveNotUserEx {
        return service.getAppointedOrdersByDriver(login);
    }

    @RequestMapping(value = "rest/driver/{login}/oldOrders", method = RequestMethod.GET)
    public List<Order> getOldOrders(@PathVariable String login) throws HaveNotOrderEx, HaveNotUserEx {
        return service.getOrdersByDriver(login);
    }
}
