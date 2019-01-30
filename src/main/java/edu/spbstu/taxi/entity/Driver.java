package edu.spbstu.taxi.entity;

import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("2")
@Data
public class Driver extends User {
    @Column(name = "isBusy")
    private boolean busy = false;
    private float rating;

    public Driver() {
    }

    public Driver(int id_, String login_, String pwd_, String name_,
                  String email_, String phone_, float rating_) {
        super(id_, login_, pwd_, name_, email_, phone_, false, 2);
        busy = false;
        rating = rating_;
    }

    public List<Order> getOrderList(List<Order> orderList) throws HaveNotOrderEx {
        List<Order> OrderList = new ArrayList();
        for (Order order : orderList) {
            if (order.getDriver().getId() == this.getId() && order.getOrderStatus() != OrderStatus.APPOINTED)
                OrderList.add(order);
        }
        if (OrderList == null)
            throw new HaveNotOrderEx();
        return OrderList;
    }

    public List<Order> getAppointedList(List<Order> orderList) throws HaveNotOrderEx {
        List<Order> appointedOrderList = new ArrayList();
        for (Order order : orderList) {
            if (order.getDriver().getId() == this.getId() && order.getOrderStatus() == OrderStatus.APPOINTED)
                appointedOrderList.add(order);
        }
        if (appointedOrderList == null)
            throw new HaveNotOrderEx();
        return appointedOrderList;
    }

    public boolean acceptRequest(Order order) {
        if (order.setOrderStatus(OrderStatus.ACCEPTED)) {
            this.setBusy(true);
            order.setDriver(this);
            return true;
        }
        return false;
    }

    public boolean declineRequest(Order order) {
        if (order.setOrderStatus(OrderStatus.DECLINED)) {
            //this.setBusy(false);
            return true;
        }
        return false;
    }

    public void declineOther(List<Order> or) throws HaveNotOrderEx {
        or = this.getAppointedList(or);
        for (Order order : or) {
            if (!declineRequest(order)) {
                throw new HaveNotOrderEx();
            }
        }
    }
/*
    public boolean acceptOrder(Order order){
        if(order.setOrderStatus(OrderStatus.ACCEPTED)) {
            setBusy(true);
            return true;
        }
        return false;

    }

    public boolean declineOrder(Order order){
        if(order.setOrderStatus(OrderStatus.DECLINED)) {
            setBusy(false);
            return true;
        }
        return false;
    }

    public boolean setRouteLength(Order order, float routeLength){
        return order.getCostCalculation().setRouteLength(routeLength);
    }

    public void setWaitingTime(){

    }

    public void refuteComplain(){

    }

    public void calculateCoast(){

    }*/

}
