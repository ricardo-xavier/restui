package restui.repository;

import restui.model.Request;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

public class RequestRepository {
    private final DynamoDbEnhancedClient enhancedClient;

    public RequestRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    public List<Request> getByProject(String projectId) {
        DynamoDbTable<Request> table = enhancedClient.table("REQUESTS", TableSchema.fromBean(Request.class));

        QueryConditional keyEqual = QueryConditional.keyEqualTo(k -> k.partitionValue(projectId));

        QueryEnhancedRequest tableQuery = QueryEnhancedRequest.builder()
                .queryConditional(keyEqual)
                .build();

        PageIterable<Request> pagedResults = table.query(tableQuery);
        return pagedResults.items().stream().collect(Collectors.toList());
    }
}
