package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Driver;
import edu.spbstu.taxi.entity.Operator;
import edu.spbstu.taxi.entity.Order;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;

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
    //TODO:ModelAndView mav...???
    @RequestMapping(value = "rest/operator/{login}", method = RequestMethod.POST)
    public void newOperator(@PathVariable String login, @RequestBody Operator op) {
       service.addNewOperator(op);
    }
/*
    @RequestMapping(value = "rest/operator/{login}", method = RequestMethod.GET)
    public void newOperator(@PathVariable String login) {
        service.findOperatorByLogin(login).orElseThrow(() -> new...//???
    }
*/

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
            return null;
        } catch (HaveNotUserEx ex){
            return null; //TODO::new exception- have not user ex!!!
        }
    }

    @RequestMapping(value = "rest/operator/{login}/accept",method = RequestMethod.PUT)
    public void acceptOrder(@PathVariable String login, @RequestParam("OrID") int OrID,@RequestParam("DrID") int DrID) throws HaveNotOrderEx,HaveNotUserEx{
        service.appointOrdertoDriver(OrID, DrID, login);
    }

}
