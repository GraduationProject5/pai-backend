package backend.feign.feignservice;

import backend.feign.feignclient.MLFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MLExec {

    @Autowired
    MLFeign mlFeign;

    public class Svm extends AbstractHandler {

        /**
         * svm
         *
         * @param map double tol, int c, int[][] practice_x, int[]   practice_y, double[][] test_x, int[] test_y
         * @return "result_y": int[]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.support_vector_machine(map);
        }
    }

    public class LogicRegression extends AbstractHandler {

        //TODO 结果需要后端整理组合数据

        /**
         * 逻辑回归
         *
         * @param map double tol, int c, String penalty, int[][] X_train, int[] y_train, double[][] X_test
         * @return "prediction_result":int[]
         * "prediction_detail":double[]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.logic_regression(map);
        }
    }

    public class GBDT extends AbstractHandler {
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
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.gbdt_binary_classification(map);
        }
    }

    public class KNearestNeighbors extends AbstractHandler {
        //todo 结果需要后端整理组合数据

        /**
         * K近邻
         *
         * @param map int k, int[][] X_train, int[] y_train, double[][] X_test
         * @return "prediction_result": int[],
         * "prediction_detail": double[][]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.k_nearest_neighbors(map);
        }
    }

    public class RandomForest extends AbstractHandler {
        //todo 结果需要后端整理组合数据

        /**
         * 随机森林
         *
         * @param map int n_estimators, int[][] X_train, int[] y_train, double[][] X_test
         * @return "prediction_result": int[],
         * "prediction_detail": int[][]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.random_forest(map);
        }
    }

    public class NaiveBayes extends AbstractHandler {
        //todo 结果需要后端整理组合数据

        /**
         * 朴素贝叶斯
         *
         * @param map int[][] X_train, int[] y_train, double[][] X_test
         * @return "prediction_result": int[],
         * "prediction_detail": int[][]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.naive_bayes(map);
        }
    }

    public class LinearRegression extends AbstractHandler {
        //todo 结果需要后端整理组合数据

        /**
         * 线性回归
         *
         * @param map int[][] X_train, int[] y_train, double[][] X_test
         * @return "prediction_result":double[]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.linear_regression(map);
        }
    }

    public class GBDTRegression extends AbstractHandler {
        /**
         * GBDT二分类
         *
         * @param map String loss, double learning_rate, int n_estimators, int subsample,
         *            int min_samples_split, int min_samples_leaf, int max_depth, double alpha,
         *            int verbose,    //double?
         *            int[][] X_train, int[] y_train, double[][] X_test
         * @return "prediction_result":double[]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.gbdt_regression(map);
        }
    }

    public class KMeansCluster extends AbstractHandler {
        /**
         * KMeans聚类
         *
         * @param map int k, int[][] X_train
         * @return "prediction_result":int[]
         */
        public Map<String, Object> handleFeign(Map<String, Object> map) {
            return mlFeign.k_means_cluster(map);
        }

    }
}
