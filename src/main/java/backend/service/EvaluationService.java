package backend.service;

import java.util.List;
import java.util.Map;


/**
 * 评估组件
 * - 聚类
 * - 回归
 * - 二分类
 * - 多分类
 * - 混淆评估
 */
public interface EvaluationService {

    /**
     * 聚类组件
     *
     * @param labels_true
     * @param labels_pred
     * @return
     */
    Map<String, List<String>> getCluster(List labels_true, List labels_pred);



}
