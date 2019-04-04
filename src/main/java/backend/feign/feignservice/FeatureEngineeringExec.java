package backend.feign.feignservice;

import backend.feign.feignclient.FeatureEngineeringFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FeatureEngineeringExec {

    @Autowired
    FeatureEngineeringFeign featureEngineeringFeign;

    /**
     * 主成分分析
     * <p>
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  PCA需要使用的列名（多个，以逗号隔开）
     * feature_num： 保留的特征个数
     * 返回：
     * json数据:
     * pca_result：降维后的数据列表（列名自己加上吧，直接col_0,col_1这样)
     * explained_variance_ratio_: 降维后各个特征在总方差中占比，也就可以理解为信息包含的比例
     */
    public Map<String, Object> getPCA(
            MultipartFile csv_file,
            String target,
            int feature_num
    ) {
        return featureEngineeringFeign.getPCA(csv_file, target, feature_num);
    }

    /**
     * 离散化
     *
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  离散化目标列名
     * discrete_method： 离散方法，等频 frequency 、等距 metric、聚类 cluster
     * num： 离散区间个数
     * 返回：
     * csv数据文件
     */
    public MultipartFile getDiscretization(
            MultipartFile csv_file,
            String target,
            String discrete_method,
            int num
    ) {
        return featureEngineeringFeign.getDiscretization(csv_file, target, discrete_method, num);
    }


    /**
     * 尺度变换
     * <p>
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  尺度变换目标列名（多个，以逗号隔开）
     * scale： 尺度变换方法，log2、log10、ln、abs、sqrt
     * 返回：
     * csv数据文件
     */
    public MultipartFile getScale(
            MultipartFile csv_file,
            String target,
            String scale
    ) {
        return featureEngineeringFeign.getScale(csv_file, target, scale);
    }


    /**
     * 异常平滑
     * <p>
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  目标列名
     * soften_method：平滑方法:百分位 per 、阈值 thresh
     * min:  下百分位或最小阈值
     * max:  上百分位或最大阈值
     * 返回：
     * csv数据文件
     */
    public MultipartFile getSoften(
            MultipartFile csv_file,
            String target,
            String soften_method,
            String min,
            String max
    ) {
        return featureEngineeringFeign.getSoften(csv_file, target, soften_method, min, max);
    }

    /**
     * 随机森林特征重要性
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  评估重要性目标列名（多个，以逗号隔开）
     * label：    	标签列名
     * 返回：
     * json数据：
     * importances: 重要性列表，顺序和输入的target一致
     */
    public Map<String, Object> getRFImportance(
            MultipartFile csv_file,
            String target,
            String label
    ) {
        return featureEngineeringFeign.getRFImportance(csv_file, target, label);
    }

    /**
     * GDBT特征重要性
     * <p>
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  评估重要性目标列名（多个，以逗号隔开）
     * label：    	标签列名
     * 返回：
     * json数据：
     * importances: 重要性列表，顺序和输入的target一致
     */
    public Map<String, Object> getGDBTImportance(
            MultipartFile csv_file,
            String target,
            String label
    ) {
        return featureEngineeringFeign.getGDBTImportance(csv_file, target, label);
    }

}