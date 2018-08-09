package springframework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private static final String INDEX = "index";
    private static final String COMMANDS = "commands";

    /**
     *
     * @return
     */
    @RequestMapping("/" + INDEX)
    public String getIndexPage() {
        logger.info("Loading index page.");
        return INDEX;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping("/" + COMMANDS)
    public String getAllCypherCommands(Model model) {
        logger.info("Loading commands overview page.");

        List<String> cmdList = new ArrayList<>();
        cmdList.add("/searchNode/{title}");
        cmdList.add("/createNode/title/{title}/description/{description}");
        cmdList.add("/allNodes/label/{label}");
        cmdList.add("/createRelation/title1/{title1}/title2/{title2}/relation/{relation}");

        try {
            if (model != null) {
                model.addAttribute(COMMANDS, cmdList);
            }
        } catch (NullPointerException npe) {
            logger.error("Could not add to Model {}", model, npe);
        }

        return COMMANDS;
    }
}
