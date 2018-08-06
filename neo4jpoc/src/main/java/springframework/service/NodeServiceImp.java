package springframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springframework.domain.Node;
import springframework.repository.NodeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class NodeServiceImp implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;

    /**
     * Get all nodes with a specific title and label parameter
     *
     * @param title
     * @param label
     * @return
     */
    public List<Node> getNodeFromTitle(String title, String label) {
        if (title != null && label != null && !title.isEmpty() && !label.isEmpty()) {
            return nodeRepository.getNodeFromTitle(title, label).stream()
                    .map(this::createNodeFromMap)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Get all nodes with a specific label parameter
     *
     * @param label
     * @return
     */
    public List<Node> getAllNodesByLabel(String label) {
        if (label != null && !label.isEmpty()) {
            return nodeRepository.getAllNodesByLabel(label).stream().map(this::createNodeFromMap).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Create a new node with specific label parameter
     *
     * @param node
     * @param label
     */
    public void createNode(Node node, String label) {
        if (node != null && label != null) {
            nodeRepository.createNode(node, label);
        }
    }


    /**
     * Create a new relation between two existing nodes with specific label parameter
     *
     * @param startNode
     * @param endNode
     * @param startLabel
     * @param endLabel
     * @param relation
     */
    public void createRelation(Node startNode, Node endNode, String startLabel, String endLabel, String relation) {
        if (startNode != null && endNode != null && startLabel != null && endLabel != null && relation != null) {
            nodeRepository.createRelation(startNode, endNode, startLabel, endLabel, relation);
        }
    }

    /**
     * Create a single node instance from a given Map entry
     *
     * @param map
     * @return
     */
    private Node createNodeFromMap(Map<String, Object> map) {
        return new Node(map.get("title").toString(), map.get("description").toString());
    }
}
