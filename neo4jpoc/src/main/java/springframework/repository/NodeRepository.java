package springframework.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import springframework.domain.Node;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class NodeRepository {

    @Value("${spring.data.neo4j.uri}")
    private String URI;

    @Value("${spring.data.neo4j.username}")
    private String USERNAME;

    @Value("${spring.data.neo4j.password}")
    private String PASSWORD;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String LABEL_PLACEHOLDER = "*LABEL_PLACEHOLDER";
    private static final String START_LABEL = "*START_LABEL";
    private static final String END_LABEL = "*END_LABEL";
    private static final String RELATION_PLACEHOLDER = "*RELATION_PLACEHOLDER";

    //Neo4j Queries
    private static final String GET_NODE_BY_TITLE_QUERY =
            "MATCH (n:*LABEL_PLACEHOLDER {title:{1}})" + " " +
                    "RETURN n.title as title, n.description as description";

    private static final String CREATE_NODE_QUERY =
            "MERGE (n:*LABEL_PLACEHOLDER {title:{1}, description:{2}})" + " " +
                    "RETURN n.title as title, n.description as description";

    private static final String GET_ALL_NODES_BY_LABEL_QUERY =
            "MATCH (n:*LABEL_PLACEHOLDER)" + " " +
                    "RETURN n.title as title, n.description as description";

    private static final String CREATE_RELATION_BETWEEN_NODES_QUERY =
            "MATCH (n:*START_LABEL {title:{1}}), (m:*END_LABEL {title:{2}})" + " " +
                    "CREATE (n)-[r:*RELATION_PLACEHOLDER]->(m)" + " " +
                    "RETURN n.title as title, n.description as description, m.title as title2, m.description as description2";


    /**
     * Get nodes with a specific title and label parameter
     *
     * @param title
     * @param label
     * @return
     */
    public List<Map<String, Object>> getNodeFromTitle(String title, String label) {
        String sqlQuery = GET_NODE_BY_TITLE_QUERY.replace(LABEL_PLACEHOLDER, label);
        return jdbcTemplate.queryForList(sqlQuery, title);
    }


    /**
     * Create a new node with label parameter
     *
     * @param node
     * @param label
     */
    public void createNode(Node node, String label) {
        if (node != null) {
            String sqlQuery = CREATE_NODE_QUERY.replace(LABEL_PLACEHOLDER, label);
            jdbcTemplate.queryForMap(sqlQuery, node.getTitle(), node.getDescription());
        }
    }

    /**
     * Search for all existing nodes with specific label
     *
     * @param label
     * @return
     */
    public List<Map<String, Object>> getAllNodesByLabel(String label) {
        String sqlQuery = GET_ALL_NODES_BY_LABEL_QUERY.replace(LABEL_PLACEHOLDER, label);
        return jdbcTemplate.queryForList(sqlQuery);
    }

    /**
     * Create a relation between two existing nodes
     *
     * @param startNode
     * @param endNode
     * @param startLabel
     * @param endLabel
     * @param relationName
     */
    public void createRelation(Node startNode, Node endNode, String startLabel, String endLabel, String relationName) {
        String sqlQuery = CREATE_RELATION_BETWEEN_NODES_QUERY
                .replace(START_LABEL, startLabel)
                .replace(END_LABEL, endLabel)
                .replace(RELATION_PLACEHOLDER, relationName);
        jdbcTemplate.queryForMap(sqlQuery, startNode.getTitle(), endNode.getTitle());
    }

    /**
     * Connection to neo4j graph database
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {
        String neo4jUrl = "jdbc:neo4j:" + URI + "?user=" + USERNAME + ",password=" + PASSWORD;
        return new DriverManagerDataSource(neo4jUrl);
    }

}
