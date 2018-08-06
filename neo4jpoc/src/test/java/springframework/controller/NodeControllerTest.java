package springframework.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springframework.TestHelper;
import springframework.domain.Node;
import springframework.repository.NodeRepository;
import springframework.service.NodeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class NodeControllerTest {

    private static Logger logger = LoggerFactory.getLogger(NodeControllerTest.class);

    @InjectMocks
    private NodeController nodeController;

    @Mock
    private NodeService nodeService;

    @Mock
    private NodeRepository nodeRepository;

    private MockMvc mockMvc;

    private List<Node> nodes = new ArrayList<>();


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(nodeController).build();
        nodes = TestHelper.getGeneratedTestNodes();
    }

    @After
    public void tearDown() {
        nodes.clear();
    }

    @Test
    public void searchNode_ShouldGetNodeList() {
        List<Node> resultList = nodes.stream()
                .filter(node -> node.getTitle().equals("node3"))
                .collect(Collectors.toList());

        when(nodeService.getNodeFromTitle(anyString(), anyString())).thenReturn(resultList);

        try {
            MvcResult result = mockMvc.perform(get("/searchNode/node3")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            assertThat(result.getResponse().getContentAsString()).containsSubsequence(
                    resultList.get(0).getTitle(),
                    resultList.get(0).getDescription(),
                    resultList.get(1).getTitle(),
                    resultList.get(1).getDescription());
        } catch (Exception e) {
            logger.error("Could not get node from request.", e);
        }
    }

    @Test
    public void searchNode_ShouldReturnNull() {
        when(nodeService.getNodeFromTitle(anyString(), anyString())).thenReturn(Collections.emptyList());
        try {
            MvcResult result = mockMvc.perform(get("/searchNode/xyz")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            assertThat(result.getResponse().getContentAsString()).isEqualTo(Collections.emptyList().toString());
        } catch (Exception e) {
            logger.error("Could not get node from request.", e);
        }
    }

    @Test
    public void createNodeTest() {
        Mockito.doNothing().when(nodeService).createNode(any(), anyString());
        when(nodeService.getNodeFromTitle(anyString(), anyString())).thenReturn(nodes);
        try {
            MvcResult result = mockMvc.perform(get("/createNode/title/nodeTest/description/This+is+a+test+description!")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            assertThat(result.getResponse().getContentAsString()).isEqualTo("Created " + nodes.toString());
        } catch (Exception e) {
            logger.error("Could not create node from request.", e);
        }
    }

    @Test
    public void getAllNodes_ShouldReturnAllNodes() {
        when(nodeService.getAllNodesByLabel(anyString())).thenReturn(nodes);
        try {
            MvcResult result = mockMvc.perform(get("/allNodes/label/Glossary")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
            assertThat(result.getResponse().getContentAsString()).containsSubsequence(
                    nodes.get(0).getTitle(),
                    nodes.get(0).getDescription(),
                    nodes.get(1).getTitle(),
                    nodes.get(1).getDescription(),
                    nodes.get(2).getTitle(),
                    nodes.get(2).getDescription(),
                    nodes.get(3).getTitle(),
                    nodes.get(3).getDescription(),
                    nodes.get(4).getTitle(),
                    nodes.get(4).getDescription(),
                    nodes.get(5).getTitle(),
                    nodes.get(5).getDescription());
        } catch (Exception e) {
            logger.error("Could not get all nodes from request.", e);
        }
    }
}
