package restui.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Map;

import static restui.Constants.ROW_HEIGHT;

public class PnlRequestParams {
    private final JPanel panel;
    private final Map<String, String> params;

    public PnlRequestParams(Map<String, String> params)  {
        this.params = params;
        panel = new JPanel();
        JTable tabPrms = new JTable();
        String[] columns = { "Name", "Value" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tabPrms.setModel(tableModel);
        tabPrms.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabPrms.getColumnModel().getColumn(1).setPreferredWidth(500);
        tabPrms.setRowHeight(ROW_HEIGHT);
        panel.add(tabPrms);

        tableModel.setRowCount(0);
        if (params != null) {
            params.forEach((k, v) -> tableModel.addRow(new String[]{k, v}));
        }
        tableModel.fireTableDataChanged();
    }

    public JPanel getPanel() {
        return panel;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
