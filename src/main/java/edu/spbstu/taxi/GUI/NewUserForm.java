//package edu.spbstu.taxi.GUI;
//
//import edu.spbstu.taxi.Exceptions.DBConnectionException;
//import edu.spbstu.taxi.service.Facade;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class NewUserForm extends JFrame {
//    private JTextField login;
//    private JPasswordField Password;
//    private JTextField Email;
//    private JRadioButton PassengerJ;
//    private JRadioButton OperatorJ;
//    private JTextField Phone;
//    private JRadioButton DriverJ;
//    private JButton Exit;
//    private JButton Create;
//    private JTextField Name;
//    private JPanel NewUser;
//    private Facade facade;
//
//    public NewUserForm() {
//        super("New user form");
//        setContentPane(NewUser);
//        Dimension size = new Dimension(400,300);
//        setSize(size);
//        setMinimumSize(size);
//        setLocationRelativeTo(null);
//        setMaximumSize(size);
//        setListeners();
//        setVisible(true);
//
//    }
//
//    public void setListeners()  {
//        NewUserForm thisFrame = this;
//        Create.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                try {
//                    facade = Facade.getInstance();
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            "Cant connect with DB", "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                String userLogin = login.getText();
//                String email = Email.getText();
//                String phone = Phone.getText();
//                String name = Name.getText();
//                String userPass  = new String (Password.getPassword());
//                if(userLogin.isEmpty() || userLogin.equals("Login") || userPass.isEmpty() ||
//                        userPass.equals("Password") ||email.isEmpty() || email.equals("Email")||
//                        phone.isEmpty() || phone.equals("Phone") || name.isEmpty() || name.equals("Name")){
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            "Please, enter data about yourself", "Warning",
//                            JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//                try {
//                    if(facade.isUser(userLogin))
//                    {
//                            JOptionPane.showMessageDialog(new JFrame(),
//                                    "Try again.We have user with this login", "Error",
//                                    JOptionPane.ERROR_MESSAGE);
//                    }
//                    else
//                    {
//                        try {
//                            if (PassengerJ.isSelected()) {
//                                facade.addNewPassenger(10, userLogin, userPass, name, email, phone);
//                                thisFrame.dispose();
//                                PassengerForm passengerForm = new PassengerForm(userLogin);
//                                passengerForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                            } else if (OperatorJ.isSelected()) {
//                                facade.addNewOperator(10, userLogin, userPass, name, email, phone);
//                                thisFrame.dispose();
//                                OperatorForm operatorForm = new OperatorForm(userLogin);
//                                operatorForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                            } else if (DriverJ.isSelected()) {
//                                facade.addNewDriver(10, userLogin, userPass, name, email, phone, 0);
//                                thisFrame.dispose();
//                                DriverForm driverForm = new DriverForm(userLogin);
//                                driverForm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                            } else
//                            {
//                                JOptionPane.showMessageDialog(new JFrame(),
//                                        "Choose type of user", "Error",
//                                        JOptionPane.ERROR_MESSAGE);
//                            }
//                        }
//                        catch (DBConnectionException ex){
//                            JOptionPane.showMessageDialog(new JFrame(),
//                                    "Cant connect to database", "Error",
//                                    JOptionPane.ERROR_MESSAGE);
//                            return;
//                        }
//                    }
//                } catch (Exception ex){
//                    JOptionPane.showMessageDialog(new JFrame(),
//                            ex.toString(),  "Allert",
//                            JOptionPane.WARNING_MESSAGE);
//                }
//
//
//            }
//        });
//        Exit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                //Main.LogOut(this);
//                thisFrame.dispose();
//                Login login = new Login();
//                login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//            }
//        });
//    }
//
//
//}
