package restui.ui;

import javax.swing.*;
import java.awt.*;

public class PnlCall {
    private static final int GAP = 5;
    private static final double METHODS_WEIGHT = 0.1;
    private static final double URL_WEIGHT = 0.8;
    private static final double BTN_WEIGHT = 0.1;

    public JPanel build() {
        JPanel pnlUrl = new JPanel(new GridBagLayout());
        pnlUrl.setBackground(Color.ORANGE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(GAP, GAP, GAP, GAP);

        JComboBox<String> cbxMethods = new JComboBox<>();
        cbxMethods.addItem("GET");
        cbxMethods.addItem("POST");
        cbxMethods.addItem("PUT");
        cbxMethods.addItem("PATCH");
        cbxMethods.addItem("DELETE");
        cbxMethods.setSelectedIndex(0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = METHODS_WEIGHT;
        pnlUrl.add(cbxMethods, c);

        JTextField edtUrl = new JTextField();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = URL_WEIGHT;
        pnlUrl.add(edtUrl, c);

        JButton btnCall = new JButton("Call");
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = BTN_WEIGHT;
        pnlUrl.add(btnCall, c);

        JTabbedPane tabbedPane = new JTabbedPane();
        PnlRequest pnlRequest = new PnlRequest();
        tabbedPane.add("Request", pnlRequest.getPanel());
        PnlResponse pnlResponse = new PnlResponse();
        tabbedPane.add("Response", pnlResponse.getPanel());

        JPanel pnlCall = new JPanel(new BorderLayout());
        pnlCall.add(pnlUrl, BorderLayout.NORTH);
        pnlCall.add(tabbedPane, BorderLayout.CENTER);

        btnCall.addActionListener(new CallListener(edtUrl, pnlResponse.getEdtResponse(), tabbedPane));
        return pnlCall;
    }
}
