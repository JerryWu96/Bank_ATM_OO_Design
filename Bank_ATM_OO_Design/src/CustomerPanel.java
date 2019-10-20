import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel {
    private JFrame frame;

    public CustomerPanel() {
        this.frame = new JFrame();
        frame.setTitle("Customer Panel");
        frame.setBounds(100, 500, 500, 500);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        frame.setLayout(new FlowLayout(FlowLayout.RIGHT));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
