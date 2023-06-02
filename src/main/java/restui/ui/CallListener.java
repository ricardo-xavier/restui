package restui.ui;

import restui.Caller;
import restui.model.ProjectEnv;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class CallListener implements ActionListener {
    private final JComboBox<String> cbxEnvs;
    private final JComboBox<String> cbxMethods;
    private final JTextField edtUrl;
    private final PnlRequest pnlRequest;
    private final PnlResponse pnlResponse;
    private final JTabbedPane tabbedPane;
    private final List<ProjectEnv> envs;

    public CallListener(JComboBox<String> cbxEnvs, JComboBox<String> cbxMethods, JTextField edtUrl, PnlRequest pnlRequest, PnlResponse pnlResponse, JTabbedPane tabbedPane, List<ProjectEnv> envs) {
        this.cbxEnvs = cbxEnvs;
        this.cbxMethods = cbxMethods;
        this.edtUrl = edtUrl;
        this.pnlRequest = pnlRequest;
        this.pnlResponse = pnlResponse;
        this.tabbedPane = tabbedPane;
        this.envs = envs;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String envId = (String) cbxEnvs.getSelectedItem();
        Map<String, String> variables = null;
        for (ProjectEnv projectEnv : envs) {
            if (envId.equals(projectEnv.getEnvId())) {
                variables = projectEnv.getVariables();
                break;
            }
        }
        tabbedPane.setSelectedIndex(1);
        Caller caller = new Caller();
        try {
            String url = replaceVar(edtUrl.getText(), variables);
            caller.call((String) cbxMethods.getSelectedItem(), url, pnlRequest.getBody());
            pnlResponse.setResponse(caller.getBody());
            pnlResponse.setStatus(caller.getStatusCode(), caller.getTimeMs());
            pnlResponse.setHeaders(caller.getHeaders());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private String replaceVar(String text, Map<String, String> variables) throws Exception {
        if (text == null) {
            return text;
        }
        while (true) {
            int p1 = text.indexOf("#{");
            if (p1 < 0) {
                break;
            }
            int p2 = text.indexOf("}", p1);
            String name = text.substring(p1 + 2, p2);
            String value = variables != null ? variables.get(name) : null;
            if (value == null) {
                throw new Exception("Variable not found: " + name);
            }
            text = text.replace(text.substring(p1, p2+1), value);
        }
        return text;
    }
}
