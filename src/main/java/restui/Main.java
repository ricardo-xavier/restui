package restui;

import restui.ui.PnlMain;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import javax.swing.*;

import static restui.Constants.FRM_HEIGHT;
import static restui.Constants.FRM_WIDTH;

public class Main extends JFrame {
    public Main() {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.SA_EAST_1)
                .build();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();

        setTitle("restUI");
        JPanel pnlMain = new PnlMain(enhancedClient).getPanel();
        getContentPane().add(pnlMain);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FRM_WIDTH, FRM_HEIGHT);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
