package restui.ui;

import javax.swing.*;

public class PnlResponse {
    private final JScrollPane panel;
    private final JTextArea edtResponse;

    public PnlResponse() {
        edtResponse = new JTextArea();
        panel = new JScrollPane(edtResponse);
    }

    public JScrollPane getPanel() {
        return panel;
    }

    public JTextArea getEdtResponse() {
        return edtResponse;
    }
}
