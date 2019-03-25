package backend.algoservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * 机器学习组件
 * - svm
 * - 逻辑回归
 * - GBDT 二分类
 * - K近邻
 * - 随机森林
 * - 朴素贝叶斯
 * - 线性回归
 * - GBDT 回归
 * - KMeans 聚类
 * - 混淆回归
 */
@Service
@FeignClient(url = "${ml.feign.url}", name = "algorithm")
public interface MLService {

    /** svm
     *
     * @param tol
     * @param c
     * @param practice_x
     * @param practice_y
     * @param test_x
     * @param test_y
     * @return  "result_y": int[]
     */
    @PostMapping(value = "/svm/")
    Map<String, Object> support_vector_machine(
            @RequestParam("tol")double tol,
            @RequestParam("c")int c,
            @RequestParam("practice_x")int[][] practice_x,
            @RequestParam("practice_y")int[]   practice_y,
            @RequestParam("test_x")double[][] test_x,
            @RequestParam("test_y")int[]   test_y
            );

    //TODO 结果需要后端整理组合数据
    /** 逻辑回归
     *
     * @param tol
     * @param c
     * @param penalty
     * @param X_train
     * @param y_train
     * @param X_test
     * @return   "prediction_result":int[]
     *           "prediction_detail":double[]
     */
    @PostMapping(value = "/lr/")
    Map<String, Object> logic_regression(
            @RequestParam("tol")double tol,
            @RequestParam("c")int c,
            @RequestParam("penalty")String penalty,
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );

    //TODO 结果需要后端整理组合数据
    /** GBDT二分类
     *
     * @param loss
     * @param learning_rate
     * @param subsample
     * @param min_samples_split
     * @param min_samples_leaf
     * @param max_depth
     * @param alpha
     * @param verbose
     * @param X_train
     * @param y_train
     * @param X_test
     * @return "prediction_result" :double[]
     */
    @PostMapping(value = "/GBDT/")
    Map<String, Object> gbdt_binary_classification(
            @RequestParam("loss")String loss,
            @RequestParam("learning_rate")double learning_rate,
            @RequestParam("n_estimators")int n_estimators,
            @RequestParam("subsample")int subsample,
            @RequestParam("min_samples_split")int min_samples_split,
            @RequestParam("min_samples_leaf")int min_samples_leaf,
            @RequestParam("max_depth")int max_depth,
            @RequestParam("alpha")double alpha,
            @RequestParam("verbose")int verbose,    //double?
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );

    //todo 结果需要后端整理组合数据
    /** K近邻
     *
     * @param k
     * @param X_train
     * @param y_train
     * @param X_test
     * @return    "prediction_result": int[],
     *             "prediction_detail": double[][]
     */
    @PostMapping(value = "/knn/")
    Map<String, Object> k_nearest_neighbors(
            @RequestParam("k")int k,
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );

    //todo 结果需要后端整理组合数据
    /** 随机森林
     *
     * @param n_estimators
     * @param X_train
     * @param y_train
     * @param X_test
     * @return    "prediction_result": int[],
     *             "prediction_detail": int[][]
     */
    @PostMapping(value = "/rf/")
    Map<String, Object> random_forest(
            @RequestParam("n_estimators")int n_estimators,
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );

    //todo 结果需要后端整理组合数据
    /** 朴素贝叶斯
     *
     * @param X_train
     * @param y_train
     * @param X_test
     * @return   "prediction_result": int[],
     *            "prediction_detail": int[][]
     */
    @PostMapping(value = "/nb/")
    Map<String, Object> naive_bayes(
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );

    //todo 结果需要后端整理组合数据
    /** 线性回归
     *
     * @param X_train
     * @param y_train
     * @param X_test
     * @return "prediction_result":double[]
     */
    @PostMapping(value = "/.../")
    Map<String, Object> linear_regression(
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );


    /** GBDT二分类
     *
     * @param loss
     * @param learning_rate
     * @param n_estimators
     * @param subsample
     * @param min_samples_split
     * @param min_samples_leaf
     * @param max_depth
     * @param alpha
     * @param verbose
     * @param X_train
     * @param y_train
     * @param X_test
     * @return "prediction_result":double[]
     */
    @PostMapping(value = "/.../")
    Map<String, Object> gbdt_regression(
            @RequestParam("loss")String loss,
            @RequestParam("learning_rate")double learning_rate,
            @RequestParam("n_estimators")int n_estimators,
            @RequestParam("subsample")int subsample,
            @RequestParam("min_samples_split")int min_samples_split,
            @RequestParam("min_samples_leaf")int min_samples_leaf,
            @RequestParam("max_depth")int max_depth,
            @RequestParam("alpha")double alpha,
            @RequestParam("verbose")int verbose,    //double?
            @RequestParam("X_train")int[][] X_train,
            @RequestParam("y_train")int[] y_train,
            @RequestParam("X_test")double[][] X_test
    );

    /** KMeans聚类
     *
     * @param k
     * @param X_train
     * @return   "prediction_result":int[]
     */
    @PostMapping(value = "/.../")
    Map<String, Object> k_means_cluster(
            @RequestParam("k")int k,
            @RequestParam("X_train")int[][] X_train
    );

    //todo 是不是跟EvaluationService里的重复了?
    /** 混淆矩阵
     *
     * @param y_true
     * @param y_pred
     * @return "confusion_matrix": int[][]
     */
    @PostMapping(value = "/cm/")
    Map<String, Object> confusionMatrix(
            @RequestParam("y_true") int[] y_true,
            @RequestParam("y_pred") int[] y_pred
    );









}
