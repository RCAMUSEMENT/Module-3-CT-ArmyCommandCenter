/** Ryley's U.S. ARMY: STRATEGIC OPERATIONS COMMAND
 * Student Name: Ryley Carlson
 * CSC372 Module 3 Critical Thinking Assignment
 * Date: 2026-04-05
 * Program: ArmyCommandCenter.java
 * Description: This Java Swing application simulates what a strategic command center may look like for the U.S. Army.
 * It features a customized camouflage-themed interface, utilizing a tactical border design, a dynamic status log that allows
 * its users to be able to log their mission times, export logs to a file, and even change the camouflage hue all while at the same time still
 * maintaining a consistent session color. This application emphasizes both functionality
 * and thematic design to create a completely immersive user experience.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class ArmyCommandCenter extends JFrame {
    private final JTextArea statusLog;
    private final JPanel mainPanel;
    private final Color sessionCamoGreen;

    public ArmyCommandCenter() {
        // --- BASE CONFIGURATION ---
        setTitle("Ryley's U.S. ARMY: STRATEGIC OPERATIONS COMMAND");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // --- PERSISTENT CAMOUFLAGE HUE FOR THE SESSION ---
        Random rand = new Random();
        float greenHue = 0.25f + rand.nextFloat() * (0.45f - 0.25f);
        sessionCamoGreen = Color.getHSBColor(greenHue, 0.7f, 0.6f);

        // --- CUSTOM TACTICAL BORDER ---
        Border outerFrame = BorderFactory.createLineBorder(new Color(40, 50, 30), 10);
        Border innerPadding = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border tacticalBorder = BorderFactory.createCompoundBorder(outerFrame, innerPadding);

        // --- UI LAYOUT ---
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(tacticalBorder);
        mainPanel.setBackground(new Color(85, 107, 47)); // Olive Drab base color

        statusLog = new JTextArea(" System Ready... Listening for Commands\n");
        statusLog.setEditable(true);
        statusLog.setBackground(new Color(20, 20, 20));
        statusLog.setForeground(Color.GREEN);
        statusLog.setFont(new Font("Monospaced", Font.BOLD, 13));
        
        mainPanel.add(new JScrollPane(statusLog), BorderLayout.CENTER);

        setJMenuBar(createMilitaryMenu());
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuBar createMilitaryMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu missionMenu = new JMenu("Operations Menu");
        missionMenu.setMnemonic(KeyEvent.VK_O);

        // ITEM 1: Log Current Time
        JMenuItem timeItem = new JMenuItem("1. Log Current Mission Time");
        timeItem.setMnemonic(KeyEvent.VK_1);
        timeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
        timeItem.addActionListener(e -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            statusLog.append("TIME LOGGED: " + dtf.format(LocalDateTime.now()) + "\n");
        });

        // ITEM 2: Export to log.txt
        JMenuItem saveItem = new JMenuItem("2. Export to log.txt");
        saveItem.setMnemonic(KeyEvent.VK_2);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("log.txt", true)) {
                writer.write("--- LOG ENTRY ---\n" + statusLog.getText() + "\n");
                statusLog.append("SYSTEM: DATA HAS BEEN EXPORTED TO log.txt SUCCESSFULLY. GOING DARK...\n");
            } catch (IOException ioEx) {
                statusLog.append("ERROR: FILE SYSTEM LOCKED.\n");
            }
        });

        // ITEM 3: Change Camouflage Hue
        JMenuItem camoItem = new JMenuItem("3. Change Camouflage Hue");
        camoItem.setMnemonic(KeyEvent.VK_3);
        camoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_DOWN_MASK));
        camoItem.addActionListener(e -> {
            // CAMOUFLAGE RE-HUE APPLIED USING THE SAME SESSION HUE FOR CONSISTENCY
            mainPanel.setBackground(sessionCamoGreen);
            statusLog.setBackground(sessionCamoGreen.darker().darker());
            mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(sessionCamoGreen.darker(), 10),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            statusLog.append("SYSTEM: CAMOUFLAGE RE-HUE APPLIED (SESSION HUE).\n");
        });

        // ITEM 4: Terminate the Mission
        JMenuItem exitItem = new JMenuItem("4. Terminate Mission");
        exitItem.setMnemonic(KeyEvent.VK_4);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener(e -> System.exit(0));

        missionMenu.add(timeItem);
        missionMenu.add(saveItem);
        missionMenu.add(camoItem);
        missionMenu.addSeparator();
        missionMenu.add(exitItem);
        menuBar.add(missionMenu);

        return menuBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArmyCommandCenter());
    }
}
