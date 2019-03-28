package backend.feign.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 特征工程 feign
 * 2.1 主成分分析
 * 2.2 离散化
 * 2.3 尺度变换
 * 2.4 异常平滑
 * 2.5 随机森林特征重要性
 * 2.6 GDBT特征重要性
 */

//TODO 修改传入参数和返回类型

@Service
@FeignClient(url = "${ml.feign.url}", name = "algorithm")
public interface FeatureEngineeringFeign {

    /**
     * 主成分分析
     * <p>
     * <p>
     * 接口：http://127.0.0.1:8000/PCA/
     * 参数：
     *      csv_file： csv格式的数据文件
     *      target：  PCA需要使用的列名（多个，以逗号隔开）
     *      feature_num： 保留的特征个数
     * 返回：
     *      json数据:
     *           pca_result：降维后的数据列表（列名自己加上吧，直接col_0,col_1这样)
     *           explained_variance_ratio_: 降维后各个特征在总方差中占比，也就可以理解为信息包含的比例
     */
    @PostMapping(value = "/PCA/")
    Map<String, Object> getPCA(
            @RequestBody Map<String, Object> map
    );

    /**
     * 离散化
     *
     * <p>
     * 接口：http://127.0.0.1:8000/discrete/
     * 参数：
     *      csv_file： csv格式的数据文件
     *      target：  离散化目标列名
     *      discrete_method： 离散方法，等频 frequency 、等距 metric、聚类 cluster
     *      num： 离散区间个数
     * 返回：
     *      csv数据文件
     */
    @PostMapping(value = "/discrete/")
    Map<String, Object> getDiscretization(
            @RequestBody Map<String, Object> map
    );


    /**
     * 尺度变换
     * <p>
     * <p>
     * 接口：http://127.0.0.1:8000/scale/
     * 参数：
     *      csv_file： csv格式的数据文件
     *      target：  尺度变换目标列名（多个，以逗号隔开）
     *      scale： 尺度变换方法，log2、log10、ln、abs、sqrt
     * 返回：
     *      csv数据文件
     */
    @PostMapping(value = "/scale/")
    Map<String, Object> getScale(
            @RequestBody Map<String, Object> map
    );

    /**
     * 异常平滑
     * <p>
     * <p>
     * 接口：http://127.0.0.1:8000/soften/
     * 参数：
     *      csv_file： csv格式的数据文件
     *      target：  目标列名
     *      soften_method：平滑方法:百分位 per 、阈值 thresh
     *      min:  下百分位或最小阈值
     *      max:  上百分位或最大阈值
     * 返回：
     *      csv数据文件
     */
    @PostMapping(value = "/soften/")
    Map<String, Object> getSoften(
            @RequestBody Map<String, Object> map
    );

    /**
     * 随机森林特征重要性
     * <p>
     * 接口：http://127.0.0.1:8000/importance/
     * 参数：
     *      csv_file： csv格式的数据文件
     *      target：  评估重要性目标列名（多个，以逗号隔开）
     *      label：    	标签列名
     * 返回：
     *      json数据：
     *          importances: 重要性列表，顺序和输入的target一致
     */
    @PostMapping(value = "/importance/")
    Map<String, Object> getRFImportance(
            @RequestBody Map<String, Object> map
    );

    /**
     * GDBT特征重要性
     * <p>
     * <p>
     * 接口：http://127.0.0.1:8000/GDBT_importance/
     * 参数：
     *      csv_file： csv格式的数据文件
     *      target：  评估重要性目标列名（多个，以逗号隔开）
     *      label：    	标签列名
     * 返回：
     *      json数据：
     *          importances: 重要性列表，顺序和输入的target一致
     */
    @PostMapping(value = "/GDBT_importance/")
    Map<String, Object> getGDBTImportance(
            @RequestBody Map<String, Object> map
    );

}
