package springframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springframework.domain.Node;
import springframework.repository.LabelRepository;

import java.util.List;

@Service
public class LabelServiceImp implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    public List<Node> getAllNodes() {
        return null;
    }
}
