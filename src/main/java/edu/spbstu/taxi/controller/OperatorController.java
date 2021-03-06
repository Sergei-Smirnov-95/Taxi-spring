package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Driver;
import edu.spbstu.taxi.entity.Operator;
import edu.spbstu.taxi.entity.Order;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OperatorController {
    @Autowired
    ServiceImpl service;

    @RequestMapping("rest/operator/{login}/authenticate")
    public String authenticate(@PathVariable String login, @RequestParam("passwd") String passwd) {
        if (service.authenticate(login, passwd) != 0)
            return "false";
        return "true";
    }

    @RequestMapping(value = "rest/operator/{login}", method = RequestMethod.POST)
    public void newOperator(@PathVariable String login, @RequestBody Operator op) {
       service.addNewOperator(op);
    }

    /*@RequestMapping(value = "rest/operator/{login}", method = RequestMethod.GET)
    public Operator getOperator(@PathVariable String login) {
        service.findOperatorByLogin(login).orElseThrow(() -> new.. .//???
    }*/


    @GetMapping("rest/operator/{login}/drivers")
    public @ResponseBody
    List<Driver> getAvailableDrivers(@PathVariable String login){
        try {
            return service.getAvailableDrivers(login);
        } catch (HaveNotUserEx ex){
            return null;
        }
    }

    @GetMapping("rest/operator/{login}/orders")
    public @ResponseBody
    List<Order> getNewOrders(@PathVariable String login){
        try {
            return service.getNewOrders(login);
        } catch (HaveNotOrderEx ex){
            return new ArrayList<Order>();
        } catch (HaveNotUserEx ex){
            return new ArrayList<Order>();
        }
    }

    @GetMapping(value = "rest/operator/{login}/appoint")
    public String appointOrder(@PathVariable String login, @RequestParam("OrID") int OrID,@RequestParam("DrID") int DrID) {
        try {
            service.appointOrdertoDriver(OrID, DrID, login);
            return "true";
        }catch (HaveNotOrderEx ex){
            return "false";
        }catch( HaveNotUserEx ex){
            return "false";
        }
    }

}
