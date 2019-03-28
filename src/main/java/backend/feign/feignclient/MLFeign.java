package backend.feign.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
public interface MLFeign {

    /**
     * svm
     *
     * @param map double tol, int c, int[][] practice_x, int[]   practice_y, double[][] test_x, int[] test_y
     * @return "result_y": int[]
     */
    @PostMapping(value = "/svm/")
    Map<String, Object> support_vector_machine(
            @RequestBody Map<String, Object> map
    );

    //TODO 结果需要后端整理组合数据

    /**
     * 逻辑回归
     *
     * @param map double tol, int c, String penalty, int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":int[]
     * "prediction_detail":double[]
     */
    @PostMapping(value = "/lr/")
    Map<String, Object> logic_regression(
            @RequestBody Map<String, Object> map
    );

    //TODO 结果需要后端整理组合数据

    /**
     * GBDT二分类
     *
     * @param map String loss, double learning_rate, int n_estimators, int subsample,
     *            int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
     *            int verbose,    //double?
     *            int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result" :double[]
     */
    @PostMapping(value = "/GBDT/")
    Map<String, Object> gbdt_binary_classification(
            @RequestBody Map<String, Object> map
    );

    //todo 结果需要后端整理组合数据

    /**
     * K近邻
     *
     * @param map int k, int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result": int[],
     * "prediction_detail": double[][]
     */
    @PostMapping(value = "/knn/")
    Map<String, Object> k_nearest_neighbors(
            @RequestBody Map<String, Object> map

    );

    //todo 结果需要后端整理组合数据

    /**
     * 随机森林
     *
     * @param map int n_estimators, int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result": int[],
     * "prediction_detail": int[][]
     */
    @PostMapping(value = "/rf/")
    Map<String, Object> random_forest(
            @RequestBody Map<String, Object> map
    );

    //todo 结果需要后端整理组合数据

    /**
     * 朴素贝叶斯
     *
     * @param map int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result": int[],
     * "prediction_detail": int[][]
     */
    @PostMapping(value = "/nb/")
    Map<String, Object> naive_bayes(
            @RequestBody Map<String, Object> map
    );

    //todo 结果需要后端整理组合数据

    /**
     * 线性回归
     *
     * @param map int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":double[]
     */
    @PostMapping(value = "/linear/")
    Map<String, Object> linear_regression(
            @RequestBody Map<String, Object> map
    );


    /**
     * GBDT二分类
     *
     * @param map String loss, double learning_rate, int n_estimators, int subsample,
     *            int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
     *            int verbose,    //double?
     *            int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":double[]
     */
    @PostMapping(value = "/GBDT_regression/")
    Map<String, Object> gbdt_regression(
            @RequestBody Map<String, Object> map
    );

    /**
     * KMeans聚类
     *
     * @param map int k, int[][] X_train
     * @return "prediction_result":int[]
     */
    @PostMapping(value = "/KMeans/")
    Map<String, Object> k_means_cluster(
            @RequestBody Map<String, Object> map
    );


}
