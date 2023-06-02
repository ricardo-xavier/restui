package restui.ui;

import restui.Globals;
import restui.model.ProjectEnv;
import restui.model.Request;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static restui.Constants.*;

public class FrmCall extends JFrame {
    public FrmCall(DynamoDbEnhancedClient enhancedClient, Request request, List<ProjectEnv> envs) {
        JPanel pnlUrl = new JPanel(new BorderLayout());
        pnlUrl.setBorder(BorderFactory.createLineBorder(DARK_ORANGE, 3));

        JPanel pnlLeft = new JPanel(new FlowLayout());

        JPanel pnlCenter = new JPanel(new GridLayout());

        JComboBox<String> cbxEnv = new JComboBox<>();
        cbxEnv.addItem("LOC");
        cbxEnv.addItem("HML");
        cbxEnv.addItem("PRD");
        String env = Globals.getEnv();
        if (env == null) {
            env = "LOC";
        }
        pnlLeft.add(cbxEnv);

        JComboBox<String> cbxMethods = new JComboBox<>();
        cbxMethods.addItem("GET");
        cbxMethods.addItem("POST");
        cbxMethods.addItem("PUT");
        cbxMethods.addItem("PATCH");
        cbxMethods.addItem("DELETE");
        cbxMethods.setSelectedItem(request.getMethod());
        pnlLeft.add(cbxMethods);
        pnlUrl.add(pnlLeft, BorderLayout.WEST);

        JTextField edtUrl = new JTextField(request.getUrl());
        pnlCenter.add(edtUrl);
        pnlUrl.add(pnlCenter, BorderLayout.CENTER);

        JButton btnCall = new JButton("Call");
        pnlUrl.add(btnCall, BorderLayout.EAST);

        JTabbedPane tabbedPane = new JTabbedPane();
        PnlRequest pnlRequest = new PnlRequest(request);
        tabbedPane.add("Request", pnlRequest.getPanel());
        PnlResponse pnlResponse = new PnlResponse();
        tabbedPane.add("Response", pnlResponse.getPanel());

        btnCall.addActionListener(new CallListener(cbxEnv, cbxMethods, edtUrl, pnlRequest, pnlResponse, tabbedPane, envs));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(pnlUrl, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);

        setTitle(request.getRequestId());
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(FRM_WIDTH, FRM_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }
}
