package restui.ui;

import restui.Caller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CallListener implements ActionListener {
    private final String method;
    private final String url;
    private final String body;
    private final PnlResponse pnlResponse;
    private final JTabbedPane tabbedPane;

    public CallListener(String method, String url, String body, PnlResponse pnlResponse, JTabbedPane tabbedPane) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.pnlResponse = pnlResponse;
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        tabbedPane.setSelectedIndex(1);
        Caller caller = new Caller();
        caller.call(method, url, body);
        pnlResponse.setResponse(caller.getBody());
        pnlResponse.setStatus(caller.getStatusCode(), caller.getTimeMs());
        pnlResponse.setHeaders(caller.getHeaders());
    }
}
