package backend.feign.feignservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainExec {


    /**
     * 使用表驱动
     * 用 索引访问表 建立 方法访问表
     */
    //评估
    EvaluationExec evaluationExec = new EvaluationExec();
    //特征工程
    FeatureEngineeringExec featureEngineeringExec = new FeatureEngineeringExec();
    //预处理
    PretreatmentExec pretreatmentExec = new PretreatmentExec();
    //机器学习
    MLExec mlExec = new MLExec();
    //文本分析
    TextAnalysisExec textAnalysisExec = new TextAnalysisExec();

    /**
     * 继任者链表
     */
    List<AbstractHandler> successors = new ArrayList<>();

    public List<AbstractHandler> getSuccessors() {
        return successors;
    }

    /**
     * 方法表内容
     */
    //==============评估================//

    //聚类
    public void setClusterEvaluation() {
        EvaluationExec.ClusterEvaluation clusterEvaluation = evaluationExec.new ClusterEvaluation();
        successors.add(clusterEvaluation);
    }

    //回归
    public void setRegressionEvaluation() {
        EvaluationExec.RegressionEvaluation regressionEvaluation = evaluationExec.new RegressionEvaluation();
        successors.add(regressionEvaluation);
    }

    //二分类
    public void setTcdEvaluation() {
        EvaluationExec.TcdEvaluation tcdEvaluation = evaluationExec.new TcdEvaluation();
        successors.add(tcdEvaluation);
    }

    //多分类
    public void setMcdEvaluation() {
        EvaluationExec.McdEvaluation mcdEvaluation = evaluationExec.new McdEvaluation();
        successors.add(mcdEvaluation);
    }

    //混淆矩阵
    public void setConfusionMatrixEvaluation() {
        EvaluationExec.ConfusionMatrixEvaluation confusionMatrixEvaluation = evaluationExec.new ConfusionMatrixEvaluation();
        successors.add(confusionMatrixEvaluation);
    }

    //============== TODO 特征工程==============//
    //pca

    //=============机器学习================//

    //svm
    public void setSVM() {
        MLExec.Svm svm = mlExec.new Svm();
        successors.add(svm);
    }

    //逻辑回归
    public void setLogicRegression() {
        MLExec.LogicRegression logicRegression = mlExec.new LogicRegression();
        successors.add(logicRegression);
    }

    //GBDT 二分类
    public void setGBDT() {
        MLExec.GBDT gbdt = mlExec.new GBDT();
        successors.add(gbdt);
    }

    //k近邻
    public void setKNearestNeighbors() {
        MLExec.KNearestNeighbors kNearestNeighbors = mlExec.new KNearestNeighbors();
        successors.add(kNearestNeighbors);
    }

    //随即森林
    public void setRandomForest() {
        MLExec.RandomForest randomForest = mlExec.new RandomForest();
        successors.add(randomForest);
    }

    //朴素贝叶斯
    public void setNaiveBayes() {
        MLExec.NaiveBayes naiveBayes = mlExec.new NaiveBayes();
        successors.add(naiveBayes);
    }

    //线性回归
    public void setLinearRegression() {
        MLExec.LinearRegression linearRegression = mlExec.new LinearRegression();
        successors.add(linearRegression);
    }

    //GBDTRegression
    public void setGBDTRegression() {
        MLExec.GBDTRegression gbdtRegression = mlExec.new GBDTRegression();
        successors.add(gbdtRegression);
    }

    //


    //=============预处理=================//


    //=============文本分析===============//


}
