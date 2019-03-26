package backend.service.impl;

import backend.feign.MLFeign;
import backend.service.MLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MLServiceImpl implements MLService{

    @Autowired
    MLFeign mlFeign;

    public Map<String,Object> support_vector_machine(Map<String, Object> map) {
        return mlFeign.support_vector_machine(map);
    }

    public Map<String, Object> logic_regression(Map<String, Object> map){
        return mlFeign.logic_regression(map);
    }

    @Override
    public Map<String, Object> gbdt_binary_classification(Map<String, Object> map) {
        return mlFeign.gbdt_binary_classification(map);
    }

    @Override
    public Map<String, Object> k_nearest_neighbors(Map<String, Object> map) {
        return mlFeign.k_nearest_neighbors(map);
    }

    @Override
    public Map<String, Object> random_forest(Map<String, Object> map) {
        return mlFeign.random_forest(map);
    }

    @Override
    public Map<String, Object> naive_bayes(Map<String, Object> map) {
        return mlFeign.naive_bayes(map);
    }

    @Override
    public Map<String, Object> linear_regression(Map<String, Object> map) {
        return mlFeign.linear_regression(map);
    }

    @Override
    public Map<String, Object> gbdt_regression(Map<String, Object> map) {
        return mlFeign.gbdt_regression(map);
    }

    @Override
    public Map<String, Object> k_means_cluster(Map<String, Object> map) {
        return mlFeign.k_means_cluster(map);
    }


}
