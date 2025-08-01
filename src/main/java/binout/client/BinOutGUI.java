/*
Emre Ketme
Distributed Systems CA
*/

package binout.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import binout.userprofile.UserProfile;

public class BinOutGUI implements ActionListener {
    private JTextField userIdField, cityField, binTypeField;
    private JTextArea scheduleReply, profileReply, recyclingReply;
    private BinOutClient client;

    public static void main(String[] args) {
        // Entry point, runs GUI in event dispatch thread
        SwingUtilities.invokeLater(() -> new BinOutGUI().build());
    }

    private void build() {
        JFrame frame = new JFrame("BinOut - Smart Waste Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel welcomeLabel = new JLabel("<html><h2>Welcome to BinOut!</h2>"
                + "<p>Track your bin pickup schedule and get recycling tips to support sustainability.</p></html>");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Create 3 service panels for user profile, bin schedule and recycling tips
        mainPanel.add(createServicePanel(
                "Please enter your user ID to get user details and bins you have.",
                "User ID (e.g. user1, user2, user3):",
                "Get Profile",
                "profile"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createServicePanel(
                "Please enter your city to get the bin schedules for your bins.",
                "City (Dublin, Cork, Galway):",
                "Get Bin Schedule",
                "schedule"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createServicePanel(
                "Please enter bin color to get tips for recycling.",
                "Bin Type (e.g. green):",
                "Get Recycling Tips",
                "recycling"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Create grpc client connection to localhost server at port 50051
        client = new BinOutClient("localhost", 50051);

        // Default input values for easy testing
        userIdField.setText("user1");
        cityField.setText("dublin");
        binTypeField.setText("green");
    }

    // Creates a service panel with instructions, input field, button and response area
    private JPanel createServicePanel(String instruction, String label, String buttonText, String type) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(buttonText));

        JLabel instructionLabel = new JLabel(instruction);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(instructionLabel);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jLabel = new JLabel(label);
        inputPanel.add(jLabel);

        JTextField entry = new JTextField(12);
        inputPanel.add(entry);

        JButton button = new JButton(buttonText);
        button.addActionListener(this);
        inputPanel.add(button);

        panel.add(inputPanel);

        JTextArea replyArea = new JTextArea(4, 30);
        replyArea.setEditable(false);
        replyArea.setLineWrap(true);
        replyArea.setWrapStyleWord(true);
        replyArea.setBorder(BorderFactory.createEtchedBorder());
        replyArea.setToolTipText("Service response appears here.");
        panel.add(replyArea);

        // Save references to fields and reply areas for later use in actionPerformed
        if (type.equals("profile")) {
            userIdField = entry;
            profileReply = replyArea;
        } else if (type.equals("schedule")) {
            cityField = entry;
            scheduleReply = replyArea;
        } else if (type.equals("recycling")) {
            binTypeField = entry;
            recyclingReply = replyArea;
        }
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = ((JButton) e.getSource()).getText();
        try {
            if (cmd.equals("Get Profile")) {
                profileReply.setText("Loading...");
                UserProfile profile = client.getUserProfile(userIdField.getText().trim());
                if (profile != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("User ID: ").append(profile.getUserId()).append("\n");
                    sb.append("City: ").append(profile.getCity()).append("\n");
                    sb.append("Service Provider: ").append(profile.getServiceProvider()).append("\n");
                    sb.append("Bins: Green, Blue, Brown");
                    profileReply.setText(sb.toString());
                } else {
                    profileReply.setText("User not found.");
                }
            } else if (cmd.equals("Get Bin Schedule")) {
                String city = cityField.getText().trim().toLowerCase();
                String schedule = client.getBinScheduleByCity(city);
                scheduleReply.setText(schedule);
            } else if (cmd.equals("Get Recycling Tips")) {
                recyclingReply.setText(client.getRecyclingTips(binTypeField.getText().trim()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
}
