package restui.ui;

import restui.Caller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CallListener implements ActionListener {
    private JTextField edtUrl;
    private JTextArea edtResponse;
    private JTabbedPane tabbedPane;

    public CallListener(JTextField edtUrl, JTextArea edtResponse, JTabbedPane tabbedPane) {
        this.edtUrl = edtUrl;
        this.edtResponse = edtResponse;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        tabbedPane.setSelectedIndex(1);
        String response = new Caller().call(edtUrl.getText());
        edtResponse.setText(response);
    }
}
