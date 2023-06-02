package restui.ui;

import restui.Globals;
import restui.model.ProjectEnv;
import restui.model.Request;
import restui.model.UserProject;
import restui.repository.ProjectEnvRepository;
import restui.repository.RequestRepository;
import restui.repository.UserProjectRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import static restui.Constants.*;

public class PnlMain {
    private final JPanel panel;
    private final PnlSelectRequest pnlSelectRequest;

    public PnlMain(DynamoDbEnhancedClient enhancedClient) {
        panel = new JPanel(new BorderLayout());
        UserProjectRepository userProjectRepository = new UserProjectRepository(enhancedClient);
        List<UserProject> userProjects = userProjectRepository.getByUser(System.getenv("USER"));

        JLabel lblProject = new JLabel("Project");

        JComboBox<String> cbxProjects = new JComboBox<>();
        userProjects.stream().map(UserProject::getProjectId).forEach(cbxProjects::addItem);

        JButton btnOpen = new JButton("Open");
        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String projectId = (String) cbxProjects.getSelectedItem();

                RequestRepository requestRepository = new RequestRepository(enhancedClient);
                List<Request> requests = requestRepository.getByProject(projectId);

                ProjectEnvRepository projectEnvRepository = new ProjectEnvRepository(enhancedClient);
                List<ProjectEnv> envs = projectEnvRepository.getByProject(projectId);

                panel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                pnlSelectRequest.update(requests, envs);
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        JButton btnNew = new JButton("New");

        JPanel pnlProjects = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlProjects.setBackground(DARK_ORANGE);
        pnlProjects.add(lblProject);
        pnlProjects.add(cbxProjects);
        pnlProjects.add(btnOpen);
        pnlProjects.add(btnNew);

        JLabel lblEnvs = new JLabel("Environment");

        JComboBox<String> cbxEnv = new JComboBox<>();
        cbxEnv.addItem("LOC");
        cbxEnv.addItem("HML");
        cbxEnv.addItem("PRD");
        String env = Globals.getEnv();
        if (env == null) {
            env = "LOC";
        }

        JButton btnEditEnv = new JButton("Edit");
        btnEditEnv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String projectId = (String) cbxProjects.getSelectedItem();
                String envId = (String) cbxEnv.getSelectedItem();
                ProjectEnvRepository projectEnvRepository = new ProjectEnvRepository(enhancedClient);
                ProjectEnv projectEnv = projectEnvRepository.getByProjectAndId(projectId, envId);
                if (projectEnv == null) {
                    projectEnv = new ProjectEnv();
                    projectEnv.setProjectId(projectId);
                    projectEnv.setEnvId(envId);
                    projectEnv.setVariables(new HashMap<>());
                }
                new FrmEnv(projectEnv).setVisible(true);
            }
        });

        JPanel pnlActions = new JPanel();
        pnlActions.setBackground(DARK_ORANGE);
        pnlActions.add(lblEnvs);
        pnlActions.add(cbxEnv);
        pnlActions.add(btnEditEnv);

        panel.add(pnlProjects, BorderLayout.NORTH);
        pnlSelectRequest = new PnlSelectRequest(enhancedClient);
        panel.add(pnlSelectRequest.getPanel(), BorderLayout.CENTER);
        panel.add(pnlActions, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }
}
