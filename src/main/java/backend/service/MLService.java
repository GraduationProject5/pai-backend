package backend.service;

import java.util.Map;

public interface MLService {
    /** svm
     *
     * @param map double tol, int c, int[][] practice_x, int[]   practice_y, double[][] test_x, int[] test_y
     * @return  "result_y": int[]
     */
    Map<String, Object> support_vector_machine(
            Map<String, Object> map
    );

    //TODO 结果需要后端整理组合数据
    /** 逻辑回归
     *
     * @param map  double tol, int c, String penalty, int[][] X_train, int[] y_train, double[][] X_test
     * @return   "prediction_result":int[]
     *           "prediction_detail":double[]
     */
    Map<String, Object> logic_regression(
            Map<String, Object> map
    );

    //TODO 结果需要后端整理组合数据
    /** GBDT二分类
     *
     * @param map  String loss, double learning_rate, int n_estimators, int subsample,
    int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
    int verbose,    //double?
    int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result" :double[]
     */
    Map<String, Object> gbdt_binary_classification(
           Map<String, Object> map
    );

    //todo 结果需要后端整理组合数据
    /** K近邻
     *
     * @param map  int k, int[][] X_train, int[] y_train, double[][] X_test
     * @return    "prediction_result": int[],
     *             "prediction_detail": double[][]
     */
    Map<String, Object> k_nearest_neighbors(
           Map<String, Object> map

    );

    //todo 结果需要后端整理组合数据
    /** 随机森林
     *
     * @param map  int n_estimators, int[][] X_train, int[] y_train, double[][] X_test
     * @return    "prediction_result": int[],
     *             "prediction_detail": int[][]
     */
    Map<String, Object> random_forest(
            Map<String, Object> map
    );

    //todo 结果需要后端整理组合数据
    /** 朴素贝叶斯
     *
     * @param map int[][] X_train, int[] y_train, double[][] X_test
     * @return   "prediction_result": int[],
     *            "prediction_detail": int[][]
     */
    Map<String, Object> naive_bayes(
           Map<String, Object> map
    );

    //todo 结果需要后端整理组合数据
    /** 线性回归
     *
     * @param map  int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":double[]
     */
    Map<String, Object> linear_regression(
            Map<String, Object> map
    );


    /** GBDT二分类
     *
     * @param map  String loss, double learning_rate, int n_estimators, int subsample,
    int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
    int verbose,    //double?
    int[][] X_train, int[] y_train, double[][] X_test
     * @return "prediction_result":double[]
     */
    Map<String, Object> gbdt_regression(
             Map<String, Object> map
    );

    /** KMeans聚类
     *
     * @param  map int k, int[][] X_train
     * @return   "prediction_result":int[]
     */
    Map<String, Object> k_means_cluster(
            Map<String, Object> map
    );

}
