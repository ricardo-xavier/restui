package restui.ui;

import restui.model.Request;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static restui.Constants.*;

public class PnlSelectRequest {
    private final JPanel panel;
    private final JTable tabRequests;
    private List<Request> requests;

    public PnlSelectRequest(DynamoDbEnhancedClient enhancedClient)  {
        panel = new JPanel();

        tabRequests = new JTable();
        String[] columns = { "Method", "RequestId", "Description" };
        TableModel tableModel = new DefaultTableModel(columns, 0);
        tabRequests.setModel(tableModel);
        tabRequests.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabRequests.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabRequests.getColumnModel().getColumn(2).setPreferredWidth(400);
        tabRequests.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel pnlCell = new JPanel(new BorderLayout(HGAP, VGAP));
                if (requests == null) {
                    return pnlCell;
                }
                JLabel lblCell = new JLabel((String) value);
                if (column == 0) {
                    Request request = requests.get(row);
                    switch (request.getMethod()) {
                        case "GET":
                            pnlCell.setBackground(Color.GREEN);
                            break;
                        case "POST":
                            pnlCell.setBackground(Color.BLUE);
                            break;
                        case "DELETE":
                            pnlCell.setBackground(Color.RED);
                            break;
                        case "PATCH":
                            pnlCell.setBackground(Color.MAGENTA);
                            break;
                    }
                } else {
                    if (isSelected) {
                        pnlCell.setBackground(SELECTED_BACKGROUND);
                    }
                }
                pnlCell.add(lblCell);
                return pnlCell;
            }
        });
        tabRequests.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                Request request = requests.get(tabRequests.getSelectedRow());
                if (mouseEvent.getClickCount() == 2) {
                    FrmCall frmCall = new FrmCall(enhancedClient, request);
                    frmCall.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        tabRequests.setRowHeight(32);
        tabRequests.setDefaultEditor(Object.class, null);

        panel.add(tabRequests);
    }

    public void update(List<Request> requests) {
        this.requests = requests;
        DefaultTableModel tableModel = (DefaultTableModel) tabRequests.getModel();
        tableModel.setRowCount(0);
        requests.stream()
                .map(request -> new String[] { request.getMethod(), request.getRequestId(), request.getDescription()})
                .forEach(tableModel::addRow);
        tableModel.fireTableDataChanged();
    }

    public JPanel getPanel() {
        return panel;
    }
}
