package restui.ui;

import restui.model.Request;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.swing.*;
import java.awt.*;

import static restui.Constants.*;

public class FrmCall extends JFrame {
    private static final int GAP = 5;
    private static final double METHODS_WEIGHT = 0.1;
    private static final double URL_WEIGHT = 0.8;
    private static final double BTN_WEIGHT = 0.1;

    public FrmCall(DynamoDbEnhancedClient enhancedClient, Request request) {
        JPanel pnlUrl = new JPanel(new GridBagLayout());
        pnlUrl.setBackground(DARK_ORANGE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(GAP, GAP, GAP, GAP);

        JComboBox<String> cbxMethods = new JComboBox<>();
        cbxMethods.addItem("GET");
        cbxMethods.addItem("POST");
        cbxMethods.addItem("PUT");
        cbxMethods.addItem("PATCH");
        cbxMethods.addItem("DELETE");
        cbxMethods.setSelectedItem(request.getMethod());
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = METHODS_WEIGHT;
        pnlUrl.add(cbxMethods, c);

        JTextField edtUrl = new JTextField(request.getUrl());
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
        PnlRequest pnlRequest = new PnlRequest(request);
        tabbedPane.add("Request", pnlRequest.getPanel());
        PnlResponse pnlResponse = new PnlResponse();
        tabbedPane.add("Response", pnlResponse.getPanel());

        btnCall.addActionListener(new CallListener((String) cbxMethods.getSelectedItem(), edtUrl.getText(), pnlRequest.getBody(), pnlResponse, tabbedPane));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(pnlUrl, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);

        setTitle(request.getRequestId());
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(FRM_WIDTH, FRM_HEIGHT);
        setLocationRelativeTo(null);
    }
}
