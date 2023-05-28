package restui.ui;

import restui.model.Request;

import javax.swing.*;
import java.util.Map;

public class PnlRequest {
    private final JTextArea edtBody;
    private final JTabbedPane panel;
    private final PnlRequestParams pnlRequestParams;

    public PnlRequest(Request request)  {
        edtBody = new JTextArea(request.getBody());
        JScrollPane scrollBody = new JScrollPane(edtBody);

        pnlRequestParams = new PnlRequestParams(request.getParams());
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

    public String buildUrl(String url) {
        Map<String, String> params = pnlRequestParams.getParams();
        while (true) {
            int open = url.indexOf("${");
            if (open <= 0) {
                break;
            }
            int close = url.indexOf("}");
            String key = url.substring(open+2, close);
            String value = params.get(key);
            url = url.replace(url.substring(open, close+1), value);
        }
        return url;
    }
}
