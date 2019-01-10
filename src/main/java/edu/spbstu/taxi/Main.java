package edu.spbstu.taxi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
//@Controller
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    //@RequestMapping("/")
    //public String homepage() {
    //    return "homepage";
    //}

}