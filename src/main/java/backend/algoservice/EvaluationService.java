package backend.algoservice;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 评估组件
 * - 聚类
 * - 回归
 * - 二分类
 * - 多分类
 * - 混淆评估
 */

@Service
@FeignClient(url = "${ml.feign.url}", name = "algorithm")
public interface EvaluationService {

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
    @PostMapping(value = "/ce/")
    Map<String, Object> cluster_evaluation(
            @RequestBody Map<String, Object> map
    );

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
    @PostMapping(value = "/re/")
    Map<String, Object> regression_evaluation(
            @RequestBody Map<String, Object> map
    );

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
    @PostMapping(value = "/tcd/")
    Map<String, Object> tcd(
            @RequestBody Map<String, Object> map
    );

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
    @PostMapping(value = "/mcd/")
    Map<String, Object> mcd(
            @RequestBody Map<String, Object> map
    );


    /** 混淆矩阵
     *
     * @param map  int[] y_true, int[] y_pred
     * @return "confusion_matrix": int[][]
     */
    @PostMapping(value = "/cm/")
    Map<String, Object> confusion_matrix(
            @RequestBody Map<String, Object> map
    );

}
