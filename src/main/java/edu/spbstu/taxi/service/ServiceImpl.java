package edu.spbstu.taxi.service;

import edu.spbstu.taxi.Exceptions.DBConnectionException;
import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.*;
import edu.spbstu.taxi.repository.DriverRepository;
import edu.spbstu.taxi.repository.OperatorRepository;
import edu.spbstu.taxi.repository.OrderRepository;
import edu.spbstu.taxi.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceImpl {
    private OrderRepository orderRepository;
    private DriverRepository driverRepository;
    private PassengerRepository passengerRepository;
    private OperatorRepository operatorRepository;

    @Autowired
    public ServiceImpl(OrderRepository orderRepository, PassengerRepository passengerRepository, DriverRepository driverRepository,
                       OperatorRepository operatorRepository) {
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.operatorRepository = operatorRepository;
        this.orderRepository = orderRepository;
    }

    public int authenticate(String login, String pwd) {
        if (loginPassenger(login, pwd)) {
            return 1;
        } else if (loginOperator(login, pwd)) {
            return 0;
        } else if (loginDriver(login, pwd)) {
            return 2;
        }
        return -1;

    }

    public boolean loginPassenger(String login, String pwd) {
        Passenger pass = passengerRepository.getPassengerByLogin(login).orElse(null);
        if (pass != null)
            return pass.loginUser(pwd);
        return false;
    }

    public boolean loginDriver(String login, String pwd) {
        Driver driver = driverRepository.getDriverByLogin(login).orElse(null);
        if (driver != null)
            return driver.loginUser(pwd);
        return false;
    }

    public boolean loginOperator(String login, String pwd) {
        Operator op = operatorRepository.getOperatorByLogin(login).orElse(null);
        if (op != null)
            return op.loginUser(pwd);
        return false;
    }

    public void addNewPassenger(Passenger passenger){
        //TODO::Passenger pass = passengerRepository.getPassengerByLogin().
        passengerRepository.save(passenger);
    }

    public void addNewDriver(Driver driver){
        // TODO::Driver dr = driver Repository.ge
        driverRepository.save(driver);
    }

    public void addNewOperator(Operator operator){
        //TODO::Operator op = operatorRepository.getOperatorByLogin(login_).orElse();
        operatorRepository.save(operator);
    }

    public void addNewOrder(String sourceAddr, String destAddr, String userLogin,
                            LocalDateTime creationDate_) throws HaveNotUserEx {
        Passenger pas = passengerRepository.getPassengerByLogin(userLogin).orElseThrow(() -> new HaveNotUserEx());

        Order or = new Order(sourceAddr, destAddr, pas, creationDate_);
        orderRepository.save(or);
    }

    public List<Order> getOrdersByDriver(String login) throws HaveNotOrderEx, HaveNotUserEx {
        List<Order> orlist = orderRepository.findAll();
        Driver driver = driverRepository.getDriverByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        if (orlist != null) {
            return driver.getOrderList(orlist);
        } else {
            throw new HaveNotOrderEx();
        }
    }

    public List<Order> getAppointedOrdersByDriver(String login) throws HaveNotOrderEx, HaveNotUserEx {
        List<Order> orlist = orderRepository.findAll();
        Driver driver = driverRepository.getDriverByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        if (orlist != null) {
            return driver.getAppointedList(orlist);
        } else {
            throw new HaveNotOrderEx();
        }

    }

    public List<Order> getNewOrders(String login) throws HaveNotUserEx, HaveNotOrderEx {
        List<Order> orlist = orderRepository.findAll();
        Operator operator = operatorRepository.getOperatorByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        orlist = operator.getNewOrders(orlist);
        for (Order or:orlist
             ) {
            operator.handleOrder(or);
            orderRepository.saveAndFlush(or);
        }
        if (orlist.isEmpty()) {
            throw new HaveNotOrderEx();
        }
        return orlist;
    }
    public List<Order> getOrdersByPassenger(String login) throws HaveNotUserEx, HaveNotOrderEx {
        Passenger pass = passengerRepository.getPassengerByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        List<Order> orlist = orderRepository.findAll();
        if (orlist.isEmpty()) {
            throw new HaveNotOrderEx();
        }
        orlist = pass.getOrders(orlist);
        if (orlist.isEmpty()) {
            throw new HaveNotOrderEx();
        }
        return orlist;
    }

    public void acceptRequest(int OrderId, String driverLogin) throws HaveNotUserEx, HaveNotOrderEx {
        Driver driver = driverRepository.getDriverByLogin(driverLogin).orElseThrow(() -> new HaveNotUserEx());
        Order or = orderRepository.findById(OrderId).orElseThrow(() -> new HaveNotOrderEx());
        if (or.setOrderStatus(OrderStatus.ACCEPTED)) {
            driver.setBusy(true);
            or.setDriver(driver);
        } else {
            throw new HaveNotUserEx();
        }
        driverRepository.saveAndFlush(driver);
        orderRepository.saveAndFlush(or);
        declineOther(driverLogin);
    }

    public void declineOther(String login) throws HaveNotUserEx, HaveNotOrderEx {
        Driver driver = driverRepository.getDriverByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        List<Order> or = driver.getAppointedList(orderRepository.findAll());
        //driver.declineOther(or);
        for (Order order : or) {
            if (!order.setOrderStatus(OrderStatus.DECLINED)) {
                throw new HaveNotOrderEx();
            }
        }
        orderRepository.saveAll(or);
    }

    public void declineRequest(int order, String driverLogin) throws HaveNotUserEx, HaveNotOrderEx {
        Driver driver = driverRepository.getDriverByLogin(driverLogin).orElseThrow(() -> new HaveNotUserEx());
        Order or = orderRepository.findById(order).orElseThrow(() -> new HaveNotOrderEx());
        if (!or.setOrderStatus(OrderStatus.DECLINED)) {
            throw new HaveNotOrderEx();
        }
        or.setDriver(null);
        List<Order> checkOrder = driver.getAppointedList(orderRepository.findAll());
        if(checkOrder.size()==0){
            driver.setBusy(false);
        }
        orderRepository.saveAndFlush(or);
        driverRepository.saveAndFlush(driver);
    }

    public List<Driver> getAvailableDrivers(String login) throws HaveNotUserEx {
        Operator operator = operatorRepository.getOperatorByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        List<Driver> userlist = driverRepository.findAll();
        List<Driver> drlist = userlist.stream().filter(user -> user.getTypeUser() == 2).map(user -> (Driver) user).collect(Collectors.toList());
        return operator.getAvailableDrivers(drlist);
    }

    public void appointOrdertoDriver(int selectedOrder, int selectedDriver, String login) throws HaveNotUserEx, HaveNotOrderEx {
        Operator operator = operatorRepository.getOperatorByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        Order or = orderRepository.findById(selectedOrder).orElseThrow(() -> new HaveNotOrderEx());
        Driver dr = driverRepository.findById(selectedDriver).orElseThrow(() -> new HaveNotUserEx());
        dr.setBusy(true);
        or.setDriver(dr);
        or.setOrderStatus(OrderStatus.APPOINTED);
        or.setOperator(operator);
        orderRepository.saveAndFlush(or);
        driverRepository.saveAndFlush(dr);
    }

    public void setPayInfo(int dist, int time, int orderID, String login) throws HaveNotUserEx,HaveNotOrderEx {
        Order or = orderRepository.findById(orderID).orElseThrow(() -> new HaveNotOrderEx());
        or.setCostCalculation(time,dist);
        or.setPayed(true);
        or.setExecutionDate(LocalDateTime.now());
        or.setOrderStatus(OrderStatus.EXECUTED);
        Driver dr = driverRepository.getDriverByLogin(login).orElseThrow(() -> new HaveNotUserEx());
        dr.setBusy(false);
        orderRepository.save(or);
        driverRepository.saveAndFlush(dr);
    }
}
