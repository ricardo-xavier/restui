package restui.ui;

import javax.swing.*;
import java.awt.*;

public class PnlMain {
    public JPanel build() {
        JPanel pnlUrl = new JPanel(new GridBagLayout());
        pnlUrl.setBackground(Color.ORANGE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,5,5);

        JComboBox<String> cbxMethods = new JComboBox<>();
        cbxMethods.addItem("GET");
        cbxMethods.addItem("POST");
        cbxMethods.addItem("PUT");
        cbxMethods.addItem("PATCH");
        cbxMethods.addItem("DELETE");
        cbxMethods.setSelectedIndex(0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.1;
        pnlUrl.add(cbxMethods, c);

        JTextField edtUrl = new JTextField();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.8;
        pnlUrl.add(edtUrl, c);

        JButton btnCall = new JButton("Call");
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.1;
        pnlUrl.add(btnCall, c);

        JTabbedPane tabbedPane = new JTabbedPane();
        PnlRequest pnlRequest = new PnlRequest();
        tabbedPane.add("Request", pnlRequest.getPanel());
        PnlResponse pnlResponse = new PnlResponse();
        tabbedPane.add("Response", pnlResponse.getPanel());

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlUrl, BorderLayout.NORTH);
        pnlMain.add(tabbedPane, BorderLayout.CENTER);

        btnCall.addActionListener(new CallListener(edtUrl, pnlResponse.getEdtResponse(), tabbedPane));
        return pnlMain;
    }
}
