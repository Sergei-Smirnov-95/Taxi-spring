package edu.spbstu.taxi.GUI;

import edu.spbstu.taxi.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JTextField login;
    private JPasswordField password;
    private JButton exitButton;
    private JButton signInButton;
    private JPanel loginPanel;
    private JButton NewUser;
    private JPanel LogIn;
    @Autowired
    private ServiceImpl facade;
    public Login() {
        super("Registration form");
        setContentPane(loginPanel);
        Dimension size = new Dimension(400,300);
        setSize(size);
        setMinimumSize(size);
        setLocationRelativeTo(null);
        setMaximumSize(size);
        setListeners();
        setVisible(true);


    }

    private void setListeners(){
        Login thisFrame = this;
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                String userLogin = login.getText();
                String userPass  = new String (password.getPassword());
                int userType;
                if(userLogin.isEmpty() || userPass.isEmpty()){
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please, enter your login and password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    userType = facade.authenticate(userLogin, userPass);
                    switch (userType){
                        case 0:
                            thisFrame.dispose();
                            OperatorForm operatorForm = new OperatorForm(userLogin);
                            operatorForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            break;
                        case 1:
                            thisFrame.dispose();
                            PassengerForm passengerForm = new PassengerForm(userLogin);
                            passengerForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            break;
                        case 2:
                            thisFrame.dispose();
                            DriverForm driverForm = new DriverForm(userLogin);
                            driverForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            break;
                        default:

                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Try again. Havent user with this login/password", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                }  catch (Exception e) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            e.toString(),  "Cannot connect to DB",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        NewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisFrame.dispose();
                NewUserForm usForm = new NewUserForm();
                usForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Container frame = exitButton.getParent();
                do
                    frame = frame.getParent();
                while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });
    }


}
