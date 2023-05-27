package restui.ui;

import restui.model.Request;
import restui.model.UserProject;
import restui.repository.RequestRepository;
import restui.repository.UserProjectRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static restui.Constants.DARK_ORANGE;

public class PnlMain {
    private static final int HGAP = 10;
    private static final int VGAP = 5;
    private final List<UserProject> userProjects;
    private final DynamoDbEnhancedClient enhancedClient;

    public PnlMain(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        UserProjectRepository userProjectRepository = new UserProjectRepository(enhancedClient);
        userProjects = userProjectRepository.getByUser(System.getenv("USER"));
    }

    public JPanel build() {
        JComboBox<String> cbxProjects = new JComboBox<>();
        userProjects.stream().map(UserProject::getProjectId).forEach(cbxProjects::addItem);

        JButton btnOpen = new JButton("Open");
        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RequestRepository requestRepository = new RequestRepository(enhancedClient);
                String projectId = (String) cbxProjects.getSelectedItem();
                List<Request> requests = requestRepository.getByProject(projectId);
                System.out.println(requests.size());
                requests.forEach(System.out::println);
            }
        });

        JButton btnNew = new JButton("New");

        JPanel pnlProjects = new JPanel(new FlowLayout(FlowLayout.CENTER, HGAP, VGAP));
        pnlProjects.setBackground(DARK_ORANGE);
        pnlProjects.add(cbxProjects);
        pnlProjects.add(btnOpen);
        pnlProjects.add(btnNew);

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlProjects, BorderLayout.NORTH);

        return pnlMain;
    }
}
