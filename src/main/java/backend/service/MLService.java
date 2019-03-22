package backend.service;

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
public interface MLService {

    /**
     * svm
     *
     * @param tol
     * @param c
     * @param practice_x
     * @param practice_y
     * @param test_x
     * @param test_y
     * @return
     */
    Map<String, List<String>> getLinearSVM(float tol, float c, List<Array> practice_x,
                                           List<String> practice_y, List<Array> test_x, List<String> test_y);


}
