package restui.ui;

import restui.model.Request;

import javax.swing.*;

public class PnlRequest {
    private final JTextArea edtBody;
    private final JTabbedPane panel;

    public PnlRequest(Request request)  {
        edtBody = new JTextArea(request.getBody());
        JScrollPane scrollBody = new JScrollPane(edtBody);

        PnlRequestParams pnlRequestParams = new PnlRequestParams();
        PnlRequestHeaders pnlRequestHeaders = new PnlRequestHeaders();

        panel = new JTabbedPane();
        panel.add("Params", pnlRequestParams.getPanel());
        panel.add("Headers", pnlRequestHeaders.getPanel());
        panel.add("Body", scrollBody);
    }

    public JTabbedPane getPanel() {
        return panel;
    }

    public String getBody() {
        return edtBody.getText();
    }
}
