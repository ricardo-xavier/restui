package restui.repository;

import restui.model.UserProject;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

public class UserProjectRepository {
    private final DynamoDbEnhancedClient enhancedClient;

    public UserProjectRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    public List<UserProject> getByUser(String userId) {
        DynamoDbTable<UserProject> table = enhancedClient.table("USER_PROJECTS", TableSchema.fromBean(UserProject.class));

        QueryConditional keyEqual = QueryConditional.keyEqualTo(k -> k.partitionValue(userId));

        QueryEnhancedRequest tableQuery = QueryEnhancedRequest.builder()
                .queryConditional(keyEqual)
                .build();

        PageIterable<UserProject> pagedResults = table.query(tableQuery);
        return pagedResults.items().stream().collect(Collectors.toList());
    }
}
