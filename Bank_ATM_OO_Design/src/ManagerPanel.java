import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Swing GUI class designed for managers to interact with the system. It is mainly used to generate report.
 */
public class ManagerPanel {
    private JFrame frame;

    public ManagerPanel(String userID) {
        this.frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);
        contentPane.setLayout(null);

        JPanel bankPanel = new JPanel();
        JLabel nameLabel = new JLabel();
        nameLabel.setText(BankPortal.getInstance().getBank().getUserName(userID, SharedConstants.MANAGER));

        tabbedPane.addTab("Your Bank", null, bankPanel, "Use this tab to check your bank metrics");

        JButton reportBtn = new JButton("Get your bank report");
        JLabel infoLabel = new JLabel("Press next day to simulate time.");
        JLabel dayLabel = new JLabel(updateDay());
        JButton dayBtn = new JButton("next day");
        JButton signoutButton = new JButton("Logout");

        reportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Report report = BankLogger.getInstance().generateReport(BankPortal.getInstance().getDay());
//                JOptionPane.showMessageDialog(frame, report.display());
            }
        });


        dayBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankPortal.getInstance().nextDay();
                dayLabel.setText(updateDay());
            }
        });


        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have successfully signed out!");
                setInvisible();
            }
        });

        bankPanel.add(reportBtn);
        bankPanel.add(infoLabel);
        bankPanel.add(dayLabel);
        bankPanel.add(dayBtn);
        bankPanel.add(signoutButton);
        tabbedPane.addTab("Your Bank", null, bankPanel, "Use this tab to check your bank metrics");

        frame.add(signoutButton);
        frame.setTitle("Manager Panel");
        frame.setBounds(100, 500, 1400, 600);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible();
    }

    public String updateDay() {
        return "Current day:" + BankPortal.getInstance().getDay();
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }

    public void setInvisible() {
        this.frame.setVisible(false);
    }
}
