package restui.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Map;

import static restui.Constants.ROW_HEIGHT;

public class PnlRequestParams {
    private final JScrollPane panel;
    private final Map<String, String> params;

    public PnlRequestParams(Map<String, String> params)  {
        this.params = params;
        JTable tabPrms = new JTable();
        String[] columns = { "Key", "Value" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        tabPrms.setModel(tableModel);
        tabPrms.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabPrms.getColumnModel().getColumn(0).setMaxWidth(300);
        tabPrms.getColumnModel().getColumn(1).setPreferredWidth(500);
        tabPrms.setRowHeight(ROW_HEIGHT);
        panel = new JScrollPane(tabPrms);

        tableModel.setRowCount(0);
        if (params != null) {
            params.forEach((k, v) -> tableModel.addRow(new String[]{k, v}));
        }
        tableModel.fireTableDataChanged();
    }

    public JScrollPane getPanel() {
        return panel;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
