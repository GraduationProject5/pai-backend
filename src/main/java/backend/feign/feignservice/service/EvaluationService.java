package backend.feign.feignservice.service;

import backend.feign.feignclient.EvaluationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Service
public class EvaluationService {

    @Autowired
    EvaluationFeign evaluationFeign;

    /** 聚类组件
     * @param map  int[] labels_true,  int[] labels_pred
     * @return   "adjusted_Rand_index",
     *           "mutual_information_based_scores",
     *           "homogeneity_score",
     *           "completeness_score",
     *           "v_measure_score"
     *           "fowlkes_mallows_score"
     *           均为double
     */
    public Map cluster_evaluation(Map<String, Object> map) {
        return evaluationFeign.cluster_evaluation(map);
    }

    /** 回归评估
     *
     * @param map   int[] y_true,  int[] y_pred
     * @return  "explained_variance_score"
     *          "mean_absolute_error"
     *          "mean_squared_error"
     *          "median_absolute_error"
     *          "r2_score"
     *          均为double
     */
    public Map regression_evaluation(Map<String, Object> map) {
        return evaluationFeign.regression_evaluation(map);
    }

    /** 二分类评估
     *
     * @param map int[] y_true,int[] y_pred
     * @return "accuracy_score": double,
     *         "classification_report": String
     *        eg.
     *
     *                  precision  recall   f1-score    support
     *      0             0.67      0.67      0.67         3
     *      2             0.67      0.67      0.67         3
     *      micro avg     0.67      0.67      0.67         6
     *      macro avg     0.67      0.67      0.67         6
     *      weighted avg  0.67      0.67      0.67         6
     */
    public Map tcd(Map<String, Object> map) {
        return evaluationFeign.tcd(map);
    }

    /** 多分类评估
     *
     * @param  map int[] y_true, int[] y_pred
     * @return "accuracy_score": double,
     *         "classification_report": String
     *         eg.
     *                  precision  recall   f1-score    support
     *      0             0.67      1.00      0.80         2
     *      1             0.00      0.00      0.00         1
     *      2             0.67      0.67      0.67         3
     *      micro avg     0.67      0.67      0.67         6
     *      macro avg     0.44      0.56      0.49         6
     *      weighted avg  0.56      0.67      0.60         6
     */
    @GetMapping(value = "/mcd")
    public Map mcd(Map<String, Object> map) {
        return evaluationFeign.mcd(map);
    }

    /** 混淆矩阵
     *
     * @param map  int[] y_true, int[] y_pred
     * @return "confusion_matrix": int[][]
     */
    @GetMapping(value = "/confusion_matrix")
    public Map confusion_matrix(Map<String, Object> map) {
        return evaluationFeign.confusion_matrix(map);
    }


}
