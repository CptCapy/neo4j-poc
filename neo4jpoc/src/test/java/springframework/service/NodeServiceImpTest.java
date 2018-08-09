package springframework.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import springframework.TestHelper;
import springframework.domain.Node;
import springframework.repository.NodeRepository;
import springframework.service.NodeServiceImp;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NodeServiceImpTest {

    @InjectMocks
    private NodeServiceImp nodeService;

    @Mock
    private NodeRepository nodeRepository;

    private List<Node> nodes;
    private List<Map<String, Object>> mapList;

    private static final String LABEL = "TestLabel";
    private static final String TITLE = "TestTitle";
    private static final String DESCRIPTION = "TestDescription";
    private static final String RELATION = "TestRelationName";


    @Before
    public void setUp() {
        nodes = TestHelper.getGeneratedTestNodes();
        mapList = TestHelper.getGeneratedTestNodeMapList();
        doNothing().when(nodeRepository).createNode(any(), anyString());
        doNothing().when(nodeRepository).createRelation(any(), any(), anyString(), anyString(), anyString());

        when(nodeRepository.getAllNodesByLabel(anyString())).thenReturn(mapList);
        when(nodeRepository.getNodeFromTitle(anyString(), anyString())).thenReturn(mapList);
    }

    @After
    public void tearDown() {
        nodes.clear();
        mapList.clear();
    }

    @Test
    public void createNode_ShouldCreateNode() {
        Node n = new Node(TITLE, DESCRIPTION);

        nodeService.createNode(n, LABEL);
        verify(nodeRepository, atLeastOnce()).createNode(any(), anyString());
    }

    @Test
    public void createNode_WithNull() {
        nodeService.createNode(null, null);
        verify(nodeRepository, never()).createNode(null, null);

        nodeService.createNode(new Node(TITLE, DESCRIPTION), null);
        verify(nodeRepository, never()).createNode(new Node(TITLE, DESCRIPTION), null);

        nodeService.createNode(new Node(TITLE, DESCRIPTION), "");
        verify(nodeRepository, never()).createNode(new Node(TITLE, DESCRIPTION), "");
    }

    @Test
    public void createRelation_ShouldCreateRelation() {
        Node n1 = new Node(TITLE, DESCRIPTION);
        Node n2 = new Node(TITLE, DESCRIPTION);

        nodeService.createRelation(n1, n2, LABEL, LABEL, RELATION);
        verify(nodeRepository, atLeastOnce()).createRelation(any(), any(), anyString(), anyString(), anyString());
    }

    @Test
    public void createRelation_WithNull() {
        nodeService.createRelation(null, null, LABEL, null, RELATION);
        verify(nodeRepository, never()).createRelation(any(), any(), anyString(), anyString(), anyString());
    }

    @Test
    public void getAllNodes_ShouldReturnAllNodes() {
        List<Node> list = nodeService.getAllNodesByLabel(LABEL);
        boolean isEqual = false;
        int count = 0;

        for (Node node : list) {
            for (Node node2 : nodes) {
                if (node.getTitle().equals(node2.getTitle())) {
                    if (node.getDescription().equals(node2.getDescription())) {
                        count++;
                        break;
                    }
                }
            }
        }
        if (count == list.size() && list.size() == nodes.size()) {
            isEqual = true;
        }
        assertThat(list).isNotEmpty();
        assertThat(isEqual).isTrue();
    }

    @Test
    public void getAllNodes_WithNull() {
        assertThat(nodeService.getAllNodesByLabel(null)).isEmpty();
        assertThat(nodeService.getAllNodesByLabel("")).isEmpty();
    }

    @Test
    public void getNodeFromTitle_ShouldReturnNode() {
        List<Node> list = nodeService.getNodeFromTitle("node2", "Glossary");
        assertThat(list).isNotEmpty();
    }

    @Test
    public void getNodeFromTitle_WithNull() {
        assertThat(nodeService.getNodeFromTitle(null, "")).isEmpty();
        assertThat(nodeService.getNodeFromTitle("", null)).isEmpty();
        assertThat(nodeService.getNodeFromTitle(null, null)).isEmpty();
        assertThat(nodeService.getNodeFromTitle("", "")).isEmpty();
    }
}
