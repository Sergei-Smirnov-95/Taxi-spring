/*
package edu.spbstu.taxi.GUI;

import edu.spbstu.taxi.entity.CostCalculation;
import edu.spbstu.taxi.Exceptions.DBConnectionException;
import edu.spbstu.taxi.Exceptions.HaveNotOrderEx;
import edu.spbstu.taxi.service.Facade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayInfo extends JFrame{
    private JTextField inputDistanceTextField;
    private JTextField inputWaitingTimeTextField;
    private JButton completeOrderButton;
    private JPanel rootPanel;
    private Facade facade;

    public PayInfo(int orderID) {
        super("Pay info");
        setContentPane(rootPanel);
        Dimension size = new Dimension(400, 200);
        setSize(size);
        setMinimumSize(size);
        setLocationRelativeTo(null);
        setMaximumSize(size);
        setListeners(orderID);
        setVisible(true);

    }
    public void setListeners(int orderID){
        JFrame thisFrame = this;
        try {
            facade = Facade.getInstance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Cant connect with DB", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        completeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int dist = Integer.parseInt(inputDistanceTextField.getText());
                int time = Integer.parseInt(inputWaitingTimeTextField.getText());
                if(inputDistanceTextField.getText().isEmpty() || inputWaitingTimeTextField.getText().isEmpty() ){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please, enter data about order", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    facade.setPayInfo(dist, time, orderID);
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Order cost is "+ new CostCalculation(time,dist).getTotalCost(), "Thank you",
                            JOptionPane.INFORMATION_MESSAGE);
                    thisFrame.dispose();
                    Login login = new Login();
                    login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
                catch (DBConnectionException | HaveNotOrderEx ex){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please,try again. Cannot connect to DB.", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

            }
        });

    }
}
*/
