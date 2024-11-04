package Space;

import javax.swing.*;
import java.awt.*;

public class SpaceControlGUI extends JFrame {
    private GeneralSatellite satellite;
    private JTextArea logArea;
    private JLabel satelliteStateLabel;
    private JPanel statusPanel;
    private JLabel altitudeLabel;
    private JLabel fuelLabel;
    private JLabel dataLabel;
    private final String LANDED = "🛬";
    private final String LAUNCHED = "🚀";
    private final String TRANSMITTING = "📡";
    private final String REFUELING = "⛽";
    
    public SpaceControlGUI() {
        setTitle("Space Control Center");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        satellite = new GeneralSatellite(10000);
        
        JPanel controlPanel = new JPanel();
        JButton launchBtn = new JButton("Launch");
        JButton landBtn = new JButton("Land");
        JButton refuelBtn = new JButton("Refuel");
        JButton transmitBtn = new JButton("Transmit Data");
        
        controlPanel.add(launchBtn);
        controlPanel.add(landBtn);
        controlPanel.add(refuelBtn);
        controlPanel.add(transmitBtn);
        
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        
        JPanel satellitePanel = new JPanel(new BorderLayout());
        satelliteStateLabel = new JLabel(LANDED, SwingConstants.CENTER);
        satelliteStateLabel.setFont(new Font("Dialog", Font.PLAIN, 120));
        satellitePanel.add(satelliteStateLabel, BorderLayout.CENTER);
        
        statusPanel = new JPanel(new GridLayout(3, 1));
        altitudeLabel = new JLabel("Altitude: 0 km");
        fuelLabel = new JLabel("Fuel: 0/0");
        dataLabel = new JLabel("Data Transmitted: 0 MB");
        
        statusPanel.add(altitudeLabel);
        statusPanel.add(fuelLabel);
        statusPanel.add(dataLabel);
        
        contentPanel.add(scrollPane);
        contentPanel.add(satellitePanel);
        
        add(controlPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        
        updateStatusPanel();
        
        launchBtn.addActionListener(e -> {
            try {
                satellite.launch();
                log("Satellite launched successfully");
                satelliteStateLabel.setText(LAUNCHED);
                updateStatusPanel();
            } catch (InsufficientFuelException ex) {
                log("ERROR: " + ex.getMessage());
            }
        });
        
        landBtn.addActionListener(e -> {
            try {
                satellite.land();
                log("Satellite landed successfully");
                satelliteStateLabel.setText(LANDED);
                updateStatusPanel();
            } catch (InsufficientFuelException ex) {
                log("ERROR: " + ex.getMessage());
            } catch (IllegalStateException ex) {
                log("ERROR: " + ex.getMessage());
            }
        });
        
        refuelBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter fuel amount:");
            try {
                int amount = Integer.parseInt(input);
                satelliteStateLabel.setText(REFUELING);
                satellite.refuel(amount);
                log("Refueled with " + amount + " units");
                updateStatusPanel();
                Timer timer = new Timer(2000, event -> {
                    satelliteStateLabel.setText(satellite.getAltitude() > 0 ? LAUNCHED : LANDED);
                });
                timer.setRepeats(false);
                timer.start();
            } catch (NumberFormatException ex) {
                log("ERROR: Invalid fuel amount");
            } catch (IllegalArgumentException ex) {
                log("ERROR: " + ex.getMessage());
            }
        });
        
        transmitBtn.addActionListener(e -> {
            try {
                satelliteStateLabel.setText(TRANSMITTING);
                satellite.transmitData();
                log("Data transmission completed");
                updateStatusPanel();
                Timer timer = new Timer(2000, event -> {
                    satelliteStateLabel.setText(satellite.getAltitude() > 0 ? LAUNCHED : LANDED);
                });
                timer.setRepeats(false);
                timer.start();
            } catch (IllegalStateException ex) {
                log("ERROR: " + ex.getMessage());
                satelliteStateLabel.setText(LANDED);
            }
        });
    }
    
    private void updateStatusPanel() {
    altitudeLabel.setText(String.format("Altitude: %.2f km", satellite.getAltitude()));
    fuelLabel.setText(String.format("Fuel: %.2f/%.2f", satellite.getCurrentFuel(), satellite.getMaxFuel()));
    dataLabel.setText(String.format("Data Transmitted: %.2f GB", satellite.getDataTransmitted() / 12.0));
}
    
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}