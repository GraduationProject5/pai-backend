package backend.service;

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
    Map<String, Object> svm(
            @RequestParam("tol")double tol,
            @RequestParam("c")int c,
            @RequestParam("practice_x")int[][] practice_x,
            @RequestParam("practice_y")int[]   practice_y,
            @RequestParam("test_x")double[][] test_x,
            @RequestParam("test_y")int[]   test_y
            );

    //TODO 注释：结果需要后端整理组合数据
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
    Map<String, Object> lr(
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
    Map<String, Object> gbdt(
            @RequestParam("loss")String loss,
            @RequestParam("learning_rate")double learning_rate,
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


}
