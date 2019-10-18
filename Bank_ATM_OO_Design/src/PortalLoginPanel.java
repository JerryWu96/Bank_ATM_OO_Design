import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortalLoginPanel {
    public PortalLoginPanel() {
//        super("Bank Portal Panel");
        JFrame frame = new JFrame();
        frame.setTitle("Bank Portal Login Panel");
        frame.setBounds(100, 500, 600, 100);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        frame.setLayout( new FlowLayout(FlowLayout.RIGHT) );
//        setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JRadioButton radioButton_1 = new JRadioButton("Manager");
        JRadioButton radioButton_2 = new JRadioButton("Customer");
        radioButton_1.setBounds(100,100,100,30);
        radioButton_2.setBounds(300,100,100,30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton_1);
        bg.add(radioButton_2);
        frame.add(radioButton_1);
        frame.add(radioButton_2);

        JTextField userIDField = new JTextField(8);
        JLabel userIDLabel = new JLabel( "ID:" );
//        userIDLabel.setBounds(50, 90, 100, 20);
        JPasswordField userPassField = new JPasswordField(12);
//        JTextField userPassField = new JTextField(12);
//        userIDLabel.setBounds(50, 500, 110, 20);

        JLabel userPassLabel = new JLabel( "Password:" );

        userIDField.setHorizontalAlignment(JTextField.LEFT);
        userPassField.setHorizontalAlignment(JPasswordField.LEFT);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = userIDField.getText();
                String password = new String(userPassField.getPassword());
                if (radioButton_1.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Welcome Manager " + userID);
                } else if (radioButton_2.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Welcome Customer " + userID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select your identity. Manager/Customer?");
                }
            }
        });

        contentPane.add(userIDLabel);
        contentPane.add(userIDField);
        contentPane.add(userPassLabel);
        contentPane.add(userPassField);
        contentPane.add(loginButton);
        frame.setVisible(true);
    }

}
