package restui;

import restui.ui.PnlMain;

import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("restUI");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel pnlMain = new PnlMain().build();
        getContentPane().add(pnlMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
