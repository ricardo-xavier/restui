package restui.ui;

import restui.model.ProjectEnv;

import javax.swing.*;
import java.awt.*;

import static restui.Constants.*;

public class FrmEnv extends JFrame {
    public FrmEnv(ProjectEnv env) {
        JPanel panel = new JPanel(new BorderLayout());

        JButton btnNew = new JButton("New");
        JButton btnEdt = new JButton("Edit");
        JButton btnDel = new JButton("Delete");

        JPanel pnlActions = new JPanel(new GridLayout(6, 1));
        pnlActions.setBackground(DARK_ORANGE);
        pnlActions.add(btnNew);
        pnlActions.add(btnEdt);
        pnlActions.add(btnDel);

        panel.add(pnlActions, BorderLayout.EAST);

        setTitle(env.getEnvId());
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(FRM_WIDTH, FRM_HEIGHT);
        setLocationRelativeTo(null);
    }
}
