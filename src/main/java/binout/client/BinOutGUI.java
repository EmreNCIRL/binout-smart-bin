package binout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BinOutGUI implements ActionListener {

    private JFrame frame;

    private JTextField nameField;
    private JTextField emailField;
    private JComboBox<String> providerBox;
    private JComboBox<String> cityBox;
    private JComboBox<String> zoneBox;
    private JCheckBox greenBin, blueBin, brownBin, redBin;

    private JButton cancelBtn, createBtn;

    private JButton homeBtn, scheduleBtn, profileBtn;

    private String userName = "";
    private boolean hasGreen, hasBlue, hasBrown, hasRed;

    private JPanel homePanel, schedulePanel, profilePanel, navPanel;

    public static void main(String[] args) {
        new BinOutGUI().build();
    }

    public void build() {
        frame = new JFrame("BinOut");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        profilePanel = getProfileCreationPanel();
        homePanel = getHomePanel();
        schedulePanel = getSchedulePanel();
        navPanel = getNavPanel();

        frame.add(profilePanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel getNavPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));

        homeBtn = new JButton("Home");
        scheduleBtn = new JButton("Schedule");
        profileBtn = new JButton("Profile");

        homeBtn.addActionListener(this);
        scheduleBtn.addActionListener(this);
        profileBtn.addActionListener(this);

        panel.add(homeBtn);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(scheduleBtn);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(profileBtn);

        return panel;
    }

    private JPanel getProfileCreationPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome to BinOut!<br> Ireland's first smart bin collection reminder.<br><br> Please create your user profile to get started.</div></html>", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(getInputPanel("Full Name", nameField = new JTextField(20)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(getInputPanel("Email Address", emailField = new JTextField(20)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] providers = {"CountryClean", "GreenStar", "Panda"};
        providerBox = new JComboBox<>(providers);
        mainPanel.add(getComboBoxPanel("Choose Bin Provider", providerBox));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] cities = {"Dublin", "Cork", "Galway"};
        cityBox = new JComboBox<>(cities);
        cityBox.addActionListener(e -> updateZones((String) cityBox.getSelectedItem()));
        mainPanel.add(getComboBoxPanel("Choose City", cityBox));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        zoneBox = new JComboBox<>();
        updateZones("Dublin");
        mainPanel.add(getComboBoxPanel("Choose Zone", zoneBox));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel binLabel = new JLabel("Which bins do you have?");
        binLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(binLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        greenBin = new JCheckBox("Green");
        blueBin = new JCheckBox("Blue");
        brownBin = new JCheckBox("Brown");
        redBin = new JCheckBox("Red");
        JPanel binPanel = new JPanel();
        binPanel.setLayout(new BoxLayout(binPanel, BoxLayout.X_AXIS));
        binPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        binPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binPanel.add(greenBin);
        binPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binPanel.add(blueBin);
        binPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binPanel.add(brownBin);
        binPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        binPanel.add(redBin);
        binPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        mainPanel.add(binPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        cancelBtn = new JButton("Cancel");
        createBtn = new JButton("Create Profile");
        cancelBtn.addActionListener(this);
        createBtn.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(cancelBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(createBtn);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(buttonPanel);

        return mainPanel;
    }

    private JPanel getInputPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 25));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(field);

        return panel;
    }

    private JPanel getComboBoxPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(120, 25));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
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

    private JPanel getHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel heading = new JLabel("You're on track!");
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(heading);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel binOutLabel = new JLabel("Bin out?");
        binOutLabel.setFont(new Font("Arial", Font.BOLD, 16));
        binOutLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(binOutLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel binsPanel = new JPanel();
        binsPanel.setLayout(new BoxLayout(binsPanel, BoxLayout.Y_AXIS));
        binsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ArrayList<JCheckBox> binChecks = new ArrayList<>();

        if (hasGreen) binChecks.add(new JCheckBox("Green", true));
        if (hasBlue) binChecks.add(new JCheckBox("Blue", false));
        if (hasBrown) binChecks.add(new JCheckBox("Brown", false));
        if (hasRed) binChecks.add(new JCheckBox("Red", false));

        if (!binChecks.isEmpty()) {
            binChecks.get(0).setSelected(true);
        }

        for (JCheckBox cb : binChecks) {
            cb.setAlignmentX(Component.LEFT_ALIGNMENT);
            binsPanel.add(cb);
            binsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        panel.add(binsPanel);

        return panel;
    }

    private JPanel getSchedulePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Schedule (coming soon)", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Cancel")) {
            nameField.setText("");
            emailField.setText("");
            providerBox.setSelectedIndex(0);
            cityBox.setSelectedIndex(0);
            updateZones("Dublin");
            greenBin.setSelected(false);
            blueBin.setSelected(false);
            brownBin.setSelected(false);
            redBin.setSelected(false);

        } else if (cmd.equals("Create Profile")) {
            userName = nameField.getText();
            hasGreen = greenBin.isSelected();
            hasBlue = blueBin.isSelected();
            hasBrown = brownBin.isSelected();
            hasRed = redBin.isSelected();

            homePanel = getHomePanel();

            frame.getContentPane().removeAll();
            frame.add(homePanel, BorderLayout.CENTER);
            frame.add(navPanel, BorderLayout.SOUTH);
            frame.revalidate();
            frame.repaint();

        } else if (cmd.equals("Home")) {
            homePanel = getHomePanel();
            switchToPanel(homePanel);

        } else if (cmd.equals("Schedule")) {
            switchToPanel(schedulePanel);

        } else if (cmd.equals("Profile")) {
            switchToPanel(profilePanel);
        }
    }

    private void switchToPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.add(navPanel, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }
}
