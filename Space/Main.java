package Space;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpaceControlGUI gui = new SpaceControlGUI();
            gui.setVisible(true);
        });
    }
}