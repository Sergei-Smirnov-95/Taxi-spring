package edu.spbstu.taxi.GUI;

import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
import edu.spbstu.taxi.entity.Driver;
import edu.spbstu.taxi.entity.Order;
import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.service.ServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OperatorForm extends JFrame {

    private JTable Orders;
    private JButton AppointButton;
    private JTable Drivers;
    private JButton exitButton;
    private JPanel rootPanel;
    private ServiceImpl facade;
    private String login;
    private int selectedOrders;
    private int selectedDrivers;
    public OperatorForm( String userLogin){
        super("Operator workspace");
        login = userLogin;

        this.setContentPane(rootPanel);
        this.setLocationRelativeTo(null);
        Dimension size = new Dimension(400, 300);
        this.setSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        Drivers.setModel(new DefaultTableModel());
        Orders.setModel(new DefaultTableModel());
        setVisible(true);
        initHandlers();
        populateTables();
    }

    void initHandlers(){
        OperatorForm thisFrame =  this;
        AppointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    facade.appointOrdertoDriver(selectedOrders,selectedDrivers, login);
                    populateTables();

                }catch (HaveNotUserEx | HaveNotOrderEx ex){
                    JOptionPane.showMessageDialog(new JFrame(),
                            ex.toString(),  "Cant find user with this id",
                            JOptionPane.WARNING_MESSAGE);
                }catch ( Exception  e) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            e.toString(),  "Cannot connect to DB",
                            JOptionPane.WARNING_MESSAGE);
                }


            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisFrame.dispose();
                Login login = new Login();
                login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        Drivers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                int row = Drivers.rowAtPoint(mouseEvent.getPoint());
                int tempSelectedDriver = (int) Drivers.getModel().getValueAt(row, 0);
                if(tempSelectedDriver>0){
                    selectedDrivers = tempSelectedDriver;
                    if(selectedOrders>0) {
                        AppointButton.setEnabled(true);
                    }
                } else {
                    AppointButton.setEnabled(false);
                }


            }
        });
        Orders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                int row = Orders.rowAtPoint(mouseEvent.getPoint());
                int tempSelectedOrders = (int) Orders.getModel().getValueAt(row, 0);
                if(tempSelectedOrders>0){
                    selectedOrders = tempSelectedOrders;
                    if(selectedDrivers>0) {
                        AppointButton.setEnabled(true);
                    }
                } else {
                    AppointButton.setEnabled(false);
                }
            }
        });


    }

    private void populateTables(){
        //table new orders
        DefaultTableModel model = (DefaultTableModel) Orders.getModel();
        model.setRowCount(0);
        model.setColumnCount(3);
        Object[] cols = new Object[]{"id", "SourceAddr", "DestAddr"};
        model.setColumnIdentifiers(cols);
        try {
            //model.addRow(new Object[]{"id", "source ", "dest "});
            java.util.List<Order> orlist= facade.getNewOrders(login);
            for(Order or : orlist) {
                model.addRow(new Object[]{or.getId(),or.getSourceAddress(),or.getDestinationAddress()});
            }

        } catch (HaveNotOrderEx ex)
        {
            model.addRow(new Object[]{"-","-","-"});
        }
        catch (HaveNotUserEx ex)
        {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Havent user with this login or DB connection", "Error",
                    JOptionPane.ERROR_MESSAGE);
            Container frame = exitButton.getParent();
            do
                frame = frame.getParent();
            while (!(frame instanceof JFrame));
            ((JFrame) frame).dispose();
        }
        Orders.setModel(model);
        Orders.repaint();
        //table drivers
        DefaultTableModel modeltab = (DefaultTableModel) Drivers.getModel();
        modeltab.setRowCount(0);
        modeltab.setColumnCount(2);
        cols = new Object[]{"id", "raiting"};
        modeltab.setColumnIdentifiers(cols);
        try {
            //modeltab.addRow(new Object[]{"id", "raiting"});
            java.util.List<Driver> drlist= facade.getAvailableDrivers(login);
            for(Driver dr : drlist) {
                modeltab.addRow(new Object[]{dr.getId(),dr.getRating()});
            }
        }
        catch (HaveNotUserEx  ex)
        {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Havent user with this login or DB connection", "Error",
                    JOptionPane.ERROR_MESSAGE);
            Container frame = exitButton.getParent();
            do
                frame = frame.getParent();
            while (!(frame instanceof JFrame));
            ((JFrame) frame).dispose();
        }

        Drivers.setModel(modeltab);
        Drivers.repaint();
    }


}
