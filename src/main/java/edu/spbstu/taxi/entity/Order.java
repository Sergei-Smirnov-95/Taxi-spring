package edu.spbstu.taxi.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    //@GeneratedValue//(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "sourceAddr")
    private String sourceAddress;
    @Column(name = "destAddr")
    private String destinationAddress;
    @ManyToOne
    @JoinColumn(name = "passId")
    private Passenger passenger;
    @ManyToOne
    @JoinColumn(name = "driverId")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "operatorId")
    private Operator operator;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime creationDate;
    private LocalDateTime executionDate;
    private boolean isPayed;

    private float waitingTime;
    private float routeLength;
    private float totalCost;
    @Transient
    private CostCalculation costCalculation;

    public Order(){}

    public Order(String sourceAddress, String destinationAddress,
                 Passenger passenger, LocalDateTime creationDate) {
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.passenger = passenger;
        this.orderStatus = OrderStatus.NEW;
        this.creationDate = creationDate;
        this.costCalculation = new CostCalculation(0, 0);
        this.isPayed = false;
    }
/*
    public Order restoreOrder(int driverId, int operatorId,
                              OrderStatus orderStatus, LocalDate executionDate, int id, float routelength,
                              float waitingTime, float totalCost, String Complaint, boolean isPaied) {
        this.setDriverId(driverId);
        this.setOperator(operatorId);
        this.setStatus(orderStatus);
        this.setOrderId(id);
        this.setExecutionDate(executionDate);
        this.setCostCalculation(waitingTime, routelength);
        this.isPayed = isPaied;
        return this;
    }*/


    public boolean setOrderStatus(OrderStatus orderStatus) {
        if (OrderStatus.isAvailable(this.orderStatus, orderStatus)) {
            this.orderStatus = orderStatus;
            return true;
        }
        return false;
    }

    public void pay() {
        isPayed = true;
    }

    public boolean setExecutionDate(LocalDateTime executionDate) {
        //if(executionDate.isAfter(creationDate)) {
        this.executionDate = executionDate;
        return true;
        //}
        //return false;
    }

    public void setCostCalculation(float waitingTime, float routelength) {
        CostCalculation cc = new CostCalculation(waitingTime, routelength);
        this.costCalculation = cc;
        this.waitingTime = waitingTime;
        this.routeLength = routelength;
        this.totalCost = cc.getTotalCost();
        this.pay();
    }

}
