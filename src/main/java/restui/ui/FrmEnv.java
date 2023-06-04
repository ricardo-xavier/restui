package restui.ui;

import restui.model.ProjectEnv;
import restui.repository.ProjectEnvRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import static restui.Constants.*;

public class FrmEnv extends JFrame {
    private final JButton btnSave;

    public FrmEnv(DynamoDbEnhancedClient enhancedClient, ProjectEnv env) {
        JPanel panel = new JPanel(new BorderLayout());

        JTable tabVariables = new JTable();
        String[] columns = {"Name", "Value"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tabVariables.setModel(tableModel);
        tabVariables.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabVariables.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabVariables.setRowHeight(ROW_HEIGHT);

        env.getVariables().forEach((k, v) -> tableModel.addRow(new String[]{k, v}));

        tabVariables.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                if ("tableCellEditor".equals(propertyChangeEvent.getPropertyName())) {
                    if (!tabVariables.isEditing()) {
                        btnSave.setEnabled(true);
                    }
                }
            }
        });

        JButton btnNew = new JButton("New");
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = tabVariables.getSelectedRow();
                tableModel.addRow(new String[]{"", ""});
                tabVariables.editCellAt(tabVariables.getRowCount() - 1, 0);
                btnSave.setEnabled(true);
            }
        });
        JButton btnDel = new JButton("Delete");
        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = tabVariables.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String name = (String) tabVariables.getValueAt(row, 0);
                int resp = JOptionPane.showConfirmDialog(null,
                        "Confirm deletion of variable " + name + "?",
                        "Delete", JOptionPane.YES_NO_OPTION);
                if (resp != JOptionPane.YES_OPTION) {
                    return;
                }
                tableModel.removeRow(tabVariables.getSelectedRow());
                btnSave.setEnabled(true);
            }
        });

        btnSave = new JButton("Save");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                save(enhancedClient, env, tabVariables);
                btnSave.setEnabled(false);
            }
        });

        JPanel pnlActions = new JPanel(new FlowLayout());
        pnlActions.setBackground(DARK_ORANGE);
        pnlActions.add(btnNew);
        pnlActions.add(btnDel);
        pnlActions.add(btnSave);

        panel.add(tabVariables, BorderLayout.CENTER);
        panel.add(pnlActions, BorderLayout.SOUTH);

        setTitle(env.getEnvId());
        getContentPane().add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(FRM_WIDTH, FRM_HEIGHT);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (btnSave.isEnabled()) {
                    int resp = JOptionPane.showConfirmDialog(null,
                            "Save changes?",
                            "Save changes?", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        save(enhancedClient, env, tabVariables);
                    }
                }
            }
        });
    }

    private void save(DynamoDbEnhancedClient enhancedClient, ProjectEnv env, JTable tabVariables) {
        delete(env, tabVariables);
        insertUpdate(env, tabVariables);
        new ProjectEnvRepository(enhancedClient).update(env);
    }

    private void delete(ProjectEnv env, JTable tabVariables) {
        Map<String, String> variables = new HashMap<>();
        for (String id : env.getVariables().keySet()) {
            for (int i = 0; i < tabVariables.getRowCount(); i++) {
                String tableId = (String) tabVariables.getValueAt(i, 0);
                if (id.equals(tableId)) {
                    variables.put(id, (String) tabVariables.getValueAt(i, 1));
                    break;
                }
            }
        }
        env.setVariables(variables);
    }

    private void insertUpdate(ProjectEnv env, JTable tabVariables) {
        for (int i = 0; i < tabVariables.getRowCount(); i++) {
            String tableId = (String) tabVariables.getValueAt(i, 0);
            String tableValue = (String) tabVariables.getValueAt(i, 1);
            env.getVariables().put(tableId, tableValue);
        }
    }
}
