package restui.ui;

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
import java.util.List;

import static restui.Constants.*;

public class PnlMain {
    private final JPanel panel;
    private final PnlSelectRequest pnlSelectRequest;

    public PnlMain(DynamoDbEnhancedClient enhancedClient) {
        panel = new JPanel(new BorderLayout());
        UserProjectRepository userProjectRepository = new UserProjectRepository(enhancedClient);
        List<UserProject> userProjects = userProjectRepository.getByUser(System.getenv("USER"));

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

        JPanel pnlProjects = new JPanel(new FlowLayout(FlowLayout.CENTER, HGAP, VGAP));
        pnlProjects.setBackground(DARK_ORANGE);
        pnlProjects.add(cbxProjects);
        pnlProjects.add(btnOpen);
        pnlProjects.add(btnNew);

        panel.add(pnlProjects, BorderLayout.NORTH);
        pnlSelectRequest = new PnlSelectRequest(enhancedClient);
        panel.add(pnlSelectRequest.getPanel(), BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }
}
