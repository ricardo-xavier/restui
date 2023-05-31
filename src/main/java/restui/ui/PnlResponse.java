package restui.ui;

import javax.swing.*;
import java.awt.*;
import java.net.http.HttpHeaders;

public class PnlResponse {
    private final JPanel panel;
    private final JTextArea edtResponse;
    private final JTextArea edtHeaders;
    private final JPanel pnlStatus;
    private final JLabel lblStatus;

    public PnlResponse() {
        edtResponse = new JTextArea();
        JScrollPane spResponse = new JScrollPane(edtResponse);

        edtHeaders = new JTextArea();
        JScrollPane spHeaders = new JScrollPane(edtHeaders);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Body", spResponse);
        tabbedPane.add("Headers", spHeaders);

        lblStatus = new JLabel();
        pnlStatus = new JPanel();
        pnlStatus.add(lblStatus);

        panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(pnlStatus, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setResponse(String response) {
        edtResponse.setText(response);
    }

    public void setStatus(int statusCode, long timeMs) {
        String s = String.valueOf(statusCode);
        lblStatus.setText(s + " " + timeMs + " ms");
        pnlStatus.setBackground(s.startsWith("2") ? Color.GREEN : Color.RED);
        lblStatus.setForeground(s.startsWith("2") ? Color.BLACK : Color.WHITE);
    }

    public void setHeaders(HttpHeaders headers) {
        StringBuilder text = new StringBuilder();
        headers.map().forEach((k, v) -> text.append(k).append("=").append(v).append("\n"));
        edtHeaders.setText(text.toString());
    }
}
