//package edu.spbstu.taxi.GUI;
//
//import edu.spbstu.taxi.Exceptions.HaveNotUserEx;
//import edu.spbstu.taxi.entity.Order;
//import edu.spbstu.taxi.Exceptions.DBConnectionException;
//import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
//import edu.spbstu.taxi.service.Facade;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class DriverForm extends JFrame {
//
//    private JTable Orders;
//    private JButton AcceptButton;
//    private JButton declineButton;
//    private JTable NewOrders;
//    private JButton exitButton;
//    private JPanel rootPanel;
//    private Facade facade;
//    private String login;
//    private int selectedRequest;
//
//    public DriverForm(String userLogin) {
//        super("Driver workspace");
//        login = userLogin;
//
//
//        this.setContentPane(rootPanel);
//        this.setLocationRelativeTo(null);
//        Dimension size = new Dimension(400, 200);
//        this.setSize(size);
//        this.setMaximumSize(size);
//        this.setMinimumSize(size);
//        NewOrders.setModel(new DefaultTableModel());
//        setVisible(true);
//        initHandlers();
//        populateTables();
//    }
//
//    void initHandlers() {
//        DriverForm thisFrame = this;
//        try {
//            facade = Facade.getInstance();
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(new JFrame(),
//                    ex.toString(), "Allert",
//                    JOptionPane.WARNING_MESSAGE);
//        }
//        AcceptButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                    facade.acceptRequest(selectedRequest, login);
//                    facade.declineOther(login);
//                    populateTables();
//                    thisFrame.dispose();
//                    PayInfo pi = new PayInfo(selectedRequest);
//                    pi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                } catch (DBConnectionException e) {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            e.toString(), "Cannot connect to DB",
//                            JOptionPane.WARNING_MESSAGE);
//                } catch (HaveNotUserEx e) {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            e.toString(), "Cannot accept",
//                            JOptionPane.WARNING_MESSAGE);
//                } catch (HaveNotOrderEx e) {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            e.toString(), "Cannot find user",
//                            JOptionPane.WARNING_MESSAGE);
//                }
//
//
//            }
//        });
//        declineButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                    facade.declineRequest(selectedRequest, login);
//                    populateTables();
//                } catch (HaveNotUserEx | HaveNotOrderEx e) {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            e.toString(), "Cannot decline",
//                            JOptionPane.WARNING_MESSAGE);
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            e.toString(), "Cannot connect to DB",
//                            JOptionPane.WARNING_MESSAGE);
//                }
//
//            }
//
//
//        });
//        exitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                thisFrame.dispose();
//                Login login = new Login();
//                login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//            }
//        });
//        NewOrders.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent mouseEvent) {
//                super.mouseClicked(mouseEvent);
//                int row = NewOrders.rowAtPoint(mouseEvent.getPoint());
//                int tempSelectedRequest = (int) NewOrders.getModel().getValueAt(row, 0);
//                if (tempSelectedRequest > 0) {
//                    selectedRequest = tempSelectedRequest;
//                    AcceptButton.setEnabled(true);
//                    declineButton.setEnabled(true);
//                } else {
//                    AcceptButton.setEnabled(false);
//                    declineButton.setEnabled(false);
//                }
//
//
//            }
//        });
//
//
//    }
//
//    private void populateTables() {
//        //table new orders
//        DefaultTableModel model = (DefaultTableModel) NewOrders.getModel();
//        model.setRowCount(0);
//        model.setColumnCount(3);
//        Object[] cols = new Object[]{"id", "SourceAddr", "DestAddr"};
//        model.setColumnIdentifiers(cols);
//        try {
//
//            java.util.List<Order> orlist = facade.getAppointedOrdersByDriver(login);
//            for (Order or : orlist) {
//                model.addRow(new Object[]{or.getId(), or.getSourceAddress(), or.getDestinationAddress()});
//            }
//
//        } catch (HaveNotOrderEx | HaveNotUserEx ex) {
//            model.addRow(new Object[]{"-", "-", "-"});
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(new JFrame(),
//                    "Havent user with this login or DB connection", "Error",
//                    JOptionPane.ERROR_MESSAGE);
//            Container frame = exitButton.getParent();
//            do
//                frame = frame.getParent();
//            while (!(frame instanceof JFrame));
//            ((JFrame) frame).dispose();
//        }
//        NewOrders.setModel(model);
//        NewOrders.repaint();
//        //table old orders
//        DefaultTableModel modeltab = (DefaultTableModel) Orders.getModel();
//        modeltab.setRowCount(0);
//        modeltab.setColumnCount(3);
//        modeltab.setColumnIdentifiers(cols);
//        try {
//
//            java.util.List<Order> orlist = facade.getOrdersByDriver(login);
//            for (Order or : orlist) {
//                modeltab.addRow(new Object[]{or.getId(), or.getSourceAddress(), or.getDestinationAddress()});
//            }
//
//        } catch (HaveNotOrderEx | HaveNotUserEx ex) {
//            modeltab.addRow(new Object[]{"-", "-", "-"});
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(new JFrame(),
//                    "Havent user with this login or DB connection", "Error",
//                    JOptionPane.ERROR_MESSAGE);
//            Container frame = exitButton.getParent();
//            do
//                frame = frame.getParent();
//            while (!(frame instanceof JFrame));
//            ((JFrame) frame).dispose();
//        }
//        Orders.setModel(modeltab);
//        Orders.repaint();
//    }
//
//
//}
