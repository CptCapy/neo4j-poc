package springframework.service;

import springframework.domain.Node;

import java.util.List;

public interface NodeService {

    List<Node> getNodeFromTitle(String title, String label);
    List<Node> getAllNodesByLabel(String label);
    void createNode(Node node, String label);
    void createRelation(Node startNode, Node endNode, String startLabel, String endLabel, String relation);
}
