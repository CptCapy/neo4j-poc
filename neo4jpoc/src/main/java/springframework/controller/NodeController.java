package springframework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springframework.domain.Node;
import springframework.service.NodeService;

import java.util.Collections;
import java.util.List;

@RestController
public class NodeController {

    @Autowired
    private NodeService nodeService;

    private static final Logger logger = LoggerFactory.getLogger(NodeController.class);
    private static final String LABEL = "Glossary";

    @RequestMapping("")
    public String getEntry() {
        logger.debug("Page load.");
        return String.format("If graph database is running try following commands:" + ", " +
                "/searchNode/{title}" + ", " +
                "/createNode/title/{title}/description/{description}" + ", " +
                "/allNodes/label/{label}" + ", " +
                "/createRelation/title1/{title1}/title2/{title2}/relation/{relation}");
    }

    /**
     * @param title
     * @return
     */
    @RequestMapping("/searchNode/{title}")
    public List<Node> searchNode(@PathVariable(value = "title") String title) {

        try {
            List<Node> nodes = nodeService.getNodeFromTitle(title, LABEL);
            if (!nodes.isEmpty()) {
                return nodes;
            }
        } catch (Exception e) {
            logger.error("Could not find node with title: " + title, e);
        }
        return Collections.emptyList();
    }

    /**
     * @param title
     * @param description
     * @return
     */
    @RequestMapping("/createNode/title/{title}/description/{description}")
    public String createNode(@PathVariable(value = "title") String title,
                             @PathVariable(value = "description") String description) {

        try {
            Node node = new Node(title, description.replace("+", " "));
            nodeService.createNode(node, LABEL);
            return "Created " + nodeService.getNodeFromTitle(title, LABEL).toString();
        } catch (Exception e) {
            logger.error("Could not create Node with title {} and description {}", title, description, e);
        }
        return null;
    }

    /**
     * @param label
     * @return
     */
    @RequestMapping("/allNodes/label/{label}")
    public List<Node> getAllNodesByLabel(@PathVariable(value = "label") String label) {

        try {
            List<Node> nodes = nodeService.getAllNodesByLabel(label);
            if (nodes != null) {
                return nodes;
            }
        } catch (Exception e) {
            logger.error("Could not get nodes with label {}", label, e);
        }
        return null;
    }

    /**
     * @param title1
     * @param title2
     * @param relation
     * @return
     */
    @RequestMapping("/createRelation/title1/{title1}/title2/{title2}/relation/{relation}")
    public String createRelation(@PathVariable(value = "title1") String title1,
                                 @PathVariable(value = "title2") String title2,
                                 @PathVariable(value = "relation") String relation) {

        try {
            List<Node> nodes = nodeService.getAllNodesByLabel(LABEL);
            for (Node startNode : nodes) {
                if (startNode.getTitle().equals(title1)) {
                    for (Node endNode : nodes) {
                        if (endNode.getTitle().equals(title2)) {
                            nodeService.createRelation(startNode, endNode, LABEL, LABEL, relation);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Could not create a relation between the node {} and node {}", title1, title2, e);
        }

        return null;
    }
}
