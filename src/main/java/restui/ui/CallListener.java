package restui.ui;

import restui.Caller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CallListener implements ActionListener {
    private final JTextField edtUrl;
    private final PnlResponse pnlResponse;
    private final JTabbedPane tabbedPane;

    public CallListener(JTextField edtUrl, PnlResponse pnlResponse, JTabbedPane tabbedPane) {
        this.edtUrl = edtUrl;
        this.pnlResponse = pnlResponse;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        tabbedPane.setSelectedIndex(1);
        Caller caller = new Caller();
        caller.call(edtUrl.getText());
        pnlResponse.setResponse(caller.getBody());
        pnlResponse.setStatus(caller.getStatusCode(), caller.getTimeMs());
        pnlResponse.setHeaders(caller.getHeaders());
    }
}
