package edu.spbstu.taxi.entity;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("1")
@Data
public class Passenger extends User {
    public Passenger() {
    }

    public Passenger(int id_, String login_, String pwd_, String name_,
                     String email_, String phone_) {
        super(id_, login_, pwd_, name_, email_, phone_, false, 1);
    }

    public Order createOrder(String sourceAddress, String destinationAddress) {
        Order order = new Order(sourceAddress, destinationAddress, this,
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return order;
    }

    public List<Order> getOrders(List<Order> orlist) {
        List<Order> orl = new ArrayList();
        for (Order or : orlist) {
            if (or.getPassenger().getId() == this.getId()) {
                orl.add(or);
            }
        }
        return orl;
    }

    boolean declineOrder(Order order) {
        //System.out.println("Order declined by passenger");
        return order.setOrderStatus(OrderStatus.DEAD);
    }

    void puyBill(Order or) {
        or.pay();
    }

}
