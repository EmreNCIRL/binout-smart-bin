package binout.client;

import javax.swing.*;
import java.awt.*;

public class BinOutGUI {

    private BinOutClient client;

    public BinOutGUI() {
        client = new BinOutClient("localhost", 50051);

        JFrame frame = new JFrame("BinOut App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField userIdField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField binTypeField = new JTextField(20);
        JTextField collectionDateField = new JTextField(20);
        JTextArea outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JButton createUserBtn = new JButton("Create User");
        createUserBtn.addActionListener(e -> {
            String userId = userIdField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            client.createUserProfile(userId, name, email, phone);
            outputArea.setText("User created: " + name);
        });

        JButton getUserBtn = new JButton("Get User");
        getUserBtn.addActionListener(e -> {
            String userId = userIdField.getText();
            client.getUserProfile(userId);
            outputArea.setText("Fetched user profile for: " + userId);
        });

        JButton setScheduleBtn = new JButton("Set Bin Schedule");
        setScheduleBtn.addActionListener(e -> {
            String userId = userIdField.getText();
            String binType = binTypeField.getText();
            String date = collectionDateField.getText();
            client.setBinSchedule(userId, binType, date);
            outputArea.setText("Bin schedule set for: " + date);
        });

        JButton getScheduleBtn = new JButton("Get Bin Schedule");
        getScheduleBtn.addActionListener(e -> {
            String userId = userIdField.getText();
            client.getBinSchedule(userId);
            outputArea.setText("Fetched schedule for: " + userId);
        });

        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Bin Type:"));
        panel.add(binTypeField);
        panel.add(new JLabel("Collection Date (YYYY-MM-DD):"));
        panel.add(collectionDateField);

        panel.add(createUserBtn);
        panel.add(getUserBtn);
        panel.add(setScheduleBtn);
        panel.add(getScheduleBtn);
        panel.add(new JScrollPane(outputArea));

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BinOutGUI::new);
    }
}
