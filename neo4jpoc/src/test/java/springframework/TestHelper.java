package springframework;

import org.springframework.util.LinkedCaseInsensitiveMap;
import springframework.domain.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestHelper {

    /**
     * Generate some test nodes for unit tests
     *
     * @return List of nodes
     */
    public static List<Node> getGeneratedTestNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("node1", "This is description 1!"));
        nodes.add(new Node("node2", "This is description 2!"));
        nodes.add(new Node("node3", "This is description 3!"));
        nodes.add(new Node("node4", "This is description 4!"));
        nodes.add(new Node("node5", "This is description 5!"));
        nodes.add(new Node("node3", "This is description 3 again!"));
        return nodes;
    }

    /**
     * Generates a List with multiple map entries.
     *
     * @return
     */
    public static List<Map<String, Object>> getGeneratedTestNodeMapList() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new LinkedCaseInsensitiveMap<>();
        Map<String, Object> map2 = new LinkedCaseInsensitiveMap<>();
        Map<String, Object> map3 = new LinkedCaseInsensitiveMap<>();
        Map<String, Object> map4 = new LinkedCaseInsensitiveMap<>();
        Map<String, Object> map5 = new LinkedCaseInsensitiveMap<>();
        Map<String, Object> map33 = new LinkedCaseInsensitiveMap<>();

        map1.put("title", "node1");
        map1.put("description", "This is description 1!");
        map2.put("title", "node2");
        map2.put("description", "This is description 2!");
        map3.put("title", "node3");
        map3.put("description", "This is description 3!");
        map4.put("title", "node4");
        map4.put("description", "This is description 4!");
        map5.put("title", "node5");
        map5.put("description", "This is description 5!");
        map33.put("title", "node3");
        map33.put("description", "This is description 3 again!");

        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        mapList.add(map4);
        mapList.add(map5);
        mapList.add(map33);

        return mapList;
    }
}
