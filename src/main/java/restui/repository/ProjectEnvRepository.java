package restui.repository;

import restui.model.ProjectEnv;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectEnvRepository {
    private final DynamoDbEnhancedClient enhancedClient;

    public ProjectEnvRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    public List<ProjectEnv> getByProject(String projectId) {
        DynamoDbTable<ProjectEnv> table = enhancedClient.table("PROJECT_ENVS", TableSchema.fromBean(ProjectEnv.class));

        QueryConditional keyEqual = QueryConditional.keyEqualTo(k -> k.partitionValue(projectId));

        QueryEnhancedRequest tableQuery = QueryEnhancedRequest.builder()
                .queryConditional(keyEqual)
                .build();

        PageIterable<ProjectEnv> pagedResults = table.query(tableQuery);
        return pagedResults.items().stream().collect(Collectors.toList());
    }

    public ProjectEnv getByProjectAndId(String projectId, String envId) {
        DynamoDbTable<ProjectEnv> table = enhancedClient.table("PROJECT_ENVS", TableSchema.fromBean(ProjectEnv.class));
        return table.getItem(Key.builder().partitionValue(projectId).sortValue(envId).build());
    }
}
