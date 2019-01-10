package edu.spbstu.taxi.controller;

import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Driver;
import edu.spbstu.taxi.entity.Operator;
import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
       // service.addNewOperator(op);//TODO::add method to service by Operator object
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



}
