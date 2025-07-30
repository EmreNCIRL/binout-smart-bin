package binout.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinOutGUI implements ActionListener {

    private JFrame frame;

    // Profile fields
    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> providerBox;
    private JComboBox<String> cityBox;
    private JComboBox<String> zoneBox;

    // Bin checkboxes
    private JCheckBox greenBin, blueBin, brownBin, redBin;

    // Buttons
    private JButton createUpdateBtn;
    private JButton greenBtn, blueBtn, brownBtn, redBtn;

    // Status label
    private JLabel statusLabel;

    // User data state
    private String userId = "u123";
    private String userName = "";
    private String userEmail = "";
    private String userProvider = "CountryClean";
    private String userCity = "Dublin";
    private String userZone = "Dublin - Zone 1";
    private boolean hasGreen, hasBlue, hasBrown, hasRed;

    // Backend client
    private BinOutClient binOutClient;

    public static void main(String[] args) {
        new BinOutGUI().build();
    }

    private void build() {
        frame = new JFrame("BinOut Simple");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        binOutClient = new BinOutClient("localhost", 50051);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Welcome message at top
        statusLabel = new JLabel("<html>Welcome! Please enter your details to start tracking your bin collection dates.</html>");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Profile inputs
        mainPanel.add(createLabeledFieldPanel("Full Name:", nameField = new JTextField(15)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(createLabeledFieldPanel("Email:", emailField = new JTextField(15)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        String[] providers = {"CountryClean", "GreenStar", "Panda"};
        providerBox = new JComboBox<>(providers);
        mainPanel.add(createLabeledComboPanel("Bin Provider:", providerBox));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        String[] cities = {"Dublin", "Cork", "Galway"};
        cityBox = new JComboBox<>(cities);
        cityBox.addActionListener(e -> updateZones((String) cityBox.getSelectedItem()));
        mainPanel.add(createLabeledComboPanel("City:", cityBox));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        zoneBox = new JComboBox<>();
        updateZones("Dublin");
        mainPanel.add(createLabeledComboPanel("Zone:", zoneBox));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Ask which bins they have
        JLabel binQuestion = new JLabel("Which bins do you have? Please mark them below:");
        binQuestion.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(binQuestion);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Bin checkboxes
        JPanel binsPanel = new JPanel();
        binsPanel.setLayout(new BoxLayout(binsPanel, BoxLayout.X_AXIS));
        binsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        greenBin = new JCheckBox("Green");
        blueBin = new JCheckBox("Blue");
        brownBin = new JCheckBox("Brown");
        redBin = new JCheckBox("Red");

        binsPanel.add(greenBin);
        binsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binsPanel.add(blueBin);
        binsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binsPanel.add(brownBin);
        binsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binsPanel.add(redBin);

        mainPanel.add(binsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create/Update profile button
        createUpdateBtn = new JButton("Create/Update Profile");
        createUpdateBtn.addActionListener(this);
        createUpdateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(createUpdateBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Info above bin pickup buttons
        JLabel pickupInfo = new JLabel("Click a bin button to see its next collection date.");
        pickupInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(pickupInfo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Bin pickup buttons
        JPanel pickupPanel = new JPanel();
        pickupPanel.setLayout(new BoxLayout(pickupPanel, BoxLayout.X_AXIS));
        pickupPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        greenBtn = new JButton("Green Pickup");
        blueBtn = new JButton("Blue Pickup");
        brownBtn = new JButton("Brown Pickup");
        redBtn = new JButton("Red Pickup");

        greenBtn.addActionListener(this);
        blueBtn.addActionListener(this);
        brownBtn.addActionListener(this);
        redBtn.addActionListener(this);

        pickupPanel.add(greenBtn);
        pickupPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pickupPanel.add(blueBtn);
        pickupPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pickupPanel.add(brownBtn);
        pickupPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pickupPanel.add(redBtn);

        mainPanel.add(pickupPanel);

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createLabeledFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 25));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(field);

        return panel;
    }

    private JPanel createLabeledComboPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 25));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(comboBox);

        return panel;
    }

    private void updateZones(String city) {
        zoneBox.removeAllItems();
        switch (city) {
            case "Dublin" -> {
                zoneBox.addItem("Dublin - Zone 1");
                zoneBox.addItem("Dublin - Zone 2");
                zoneBox.addItem("Dublin - Zone 3");
            }
            case "Cork" -> {
                zoneBox.addItem("Cork - Zone 1");
                zoneBox.addItem("Cork - Zone 2");
                zoneBox.addItem("Cork - Zone 3");
            }
            case "Galway" -> {
                zoneBox.addItem("Galway - Zone 1");
                zoneBox.addItem("Galway - Zone 2");
                zoneBox.addItem("Galway - Zone 3");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == createUpdateBtn) {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (newName.isEmpty() || newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Name and Email cannot be empty.",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            userName = newName;
            userEmail = newEmail;
            userProvider = (String) providerBox.getSelectedItem();
            userCity = (String) cityBox.getSelectedItem();
            userZone = (String) zoneBox.getSelectedItem();
            hasGreen = greenBin.isSelected();
            hasBlue = blueBin.isSelected();
            hasBrown = brownBin.isSelected();
            hasRed = redBin.isSelected();

            try {
                binOutClient.createUserProfile(userId, userName, userEmail);
                binOutClient.setBinSchedule(
                        userId,
                        hasGreen ? "2025-08-01" : "N/A",
                        hasBlue ? "2025-08-02" : "N/A",
                        hasBrown ? "2025-08-03" : "N/A",
                        hasRed ? "2025-08-04" : "N/A"
                );

                statusLabel.setText("Profile saved. Welcome, " + userName + "!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Error saving profile/schedule: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (src == greenBtn || src == blueBtn || src == brownBtn || src == redBtn) {
            String binName = "";
            if (src == greenBtn) binName = "green";
            else if (src == blueBtn) binName = "blue";
            else if (src == brownBtn) binName = "brown";
            else if (src == redBtn) binName = "red";

            if (!(binName.equals("green") && hasGreen) &&
                !(binName.equals("blue") && hasBlue) &&
                !(binName.equals("brown") && hasBrown) &&
                !(binName.equals("red") && hasRed)) {
                JOptionPane.showMessageDialog(frame,
                        "You don't have this bin selected.",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                var schedule = binOutClient.getBinSchedule(userId);

                String date = switch (binName) {
                    case "green" -> schedule.getGreenDate();
                    case "blue" -> schedule.getBlueDate();
                    case "brown" -> schedule.getBrownDate();
                    case "red" -> schedule.getRedDate();
                    default -> "No date found";
                };

                JOptionPane.showMessageDialog(frame,
                        "Next pickup date for " + binName + " bin is: " + date,
                        "Pickup Date",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Error fetching schedule: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
