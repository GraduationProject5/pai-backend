package backend.service.impl;

import backend.service.MLService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * 机器学习组件
 */
@Service
public class MLServiceImpl implements MLService {

    @Override
    public Map<String, List<String>> getLinearSVM(float tol, float c, List<Array> practice_x, List<String> practice_y, List<Array> test_x, List<String> test_y) {
        return null;
    }
}
