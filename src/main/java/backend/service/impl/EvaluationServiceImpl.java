package backend.service.impl;

import backend.service.EvaluationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 评估组件
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Override
    public Map<String, List<String>> getCluster(List labels_true, List labels_pred) {
        return null;
    }
}
