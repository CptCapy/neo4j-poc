package springframework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springframework.service.LabelServiceImp;

@RestController
@RequestMapping("/")
public class LabelRestController {

    @Autowired
    private LabelServiceImp labelServiceImp;
}
