package backend.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MLService {

    @Autowired
    MLFeign mlFeign;

    /**
     * svm
     *
     * @param map double tol, int c, int[][] practice_x, int[]   practice_y, double[][] test_x, int[] test_y
     * @return "result_y": int[]
     */
    public Map<String,Object> support_vector_machine(Map<String, Object> map) {
        return mlFeign.support_vector_machine(map);
    }

    //TODO 结果需要后端整理组合数据

    /** 逻辑回归
     *
     * @param map  double tol, int c, String penalty, int[][] X_train, int[] y_train, double[][] X_test
     * @return   "prediction_result":int[]
     *           "prediction_detail":double[]
     */
    public Map<String, Object> logic_regression(Map<String, Object> map){
        return mlFeign.logic_regression(map);
    }

    //TODO 结果需要后端整理组合数据

    /** GBDT二分类
     *
     * @param map  String loss, double learning_rate, int n_estimators, int subsample,
    int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
    int verbose,    //double?
    int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result" :double[]
     */
    public Map<String, Object> gbdt_binary_classification(Map<String, Object> map) {
        return mlFeign.gbdt_binary_classification(map);
    }

    //todo 结果需要后端整理组合数据

    /** K近邻
     *
     * @param map  int k, int[][] X_train, int[] y_train, double[][] X_test
     * @return    "prediction_result": int[],
     *             "prediction_detail": double[][]
     */
    public Map<String, Object> k_nearest_neighbors(Map<String, Object> map) {
        return mlFeign.k_nearest_neighbors(map);
    }

    //todo 结果需要后端整理组合数据

    /** 随机森林
     *
     * @param map  int n_estimators, int[][] X_train, int[] y_train, double[][] X_test
     * @return    "prediction_result": int[],
     *             "prediction_detail": int[][]
     */
    public Map<String, Object> random_forest(Map<String, Object> map) {
        return mlFeign.random_forest(map);
    }

    //todo 结果需要后端整理组合数据

    /** 朴素贝叶斯
     *
     * @param map int[][] X_train, int[] y_train, double[][] X_test
     * @return   "prediction_result": int[],
     *            "prediction_detail": int[][]
     */
    public Map<String, Object> naive_bayes(Map<String, Object> map) {
        return mlFeign.naive_bayes(map);
    }

    //todo 结果需要后端整理组合数据

    /** 线性回归
     *
     * @param map  int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":double[]
     */
    public Map<String, Object> linear_regression(Map<String, Object> map) {
        return mlFeign.linear_regression(map);
    }

    /** GBDT二分类
     *
     * @param map  String loss, double learning_rate, int n_estimators, int subsample,
    int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
    int verbose,    //double?
    int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":double[]
     */
    public Map<String, Object> gbdt_regression(Map<String, Object> map) {
        return mlFeign.gbdt_regression(map);
    }

    /** KMeans聚类
     *
     * @param  map int k, int[][] X_train
     * @return   "prediction_result":int[]
     */
    public Map<String, Object> k_means_cluster(Map<String, Object> map) {
        return mlFeign.k_means_cluster(map);
    }


}
