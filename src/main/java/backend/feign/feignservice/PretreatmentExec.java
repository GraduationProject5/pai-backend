package backend.feign.feignservice;

import backend.feign.feignclient.PretreatmentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public class PretreatmentExec {

    @Autowired
    PretreatmentFeign pretreatmentFeign;

    /**
     * 哑变量
     * 介绍：将变量变为哑变量，如sex有male和female两个值，变换后将变为如下形式：
     * sex_male            sex_female
     * 0                       1
     * 1                       0
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：   哑变量处理的目标列名(可多个，多个列以逗号分隔，如：Sex,Parch）
     * 返回：
     * 将目标列转换为哑变量后的csv数据文件
     */
    public MultipartFile setDummy(
            MultipartFile csv_file,
            String target
    ) {
        return pretreatmentFeign.setDummy(csv_file, target);
    }


    /**
     * 添加id列
     * <p>
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * 返回：
     * csv数据文件
     */
    public MultipartFile setId(
            MultipartFile csv_file
    ) {
        return pretreatmentFeign.setId(csv_file);
    }

    /**
     * 标准化
     * <p>
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  标准化目标列名(可多个，同1.1）
     * 返回：
     * csv数据文件
     */
    public MultipartFile standardized(
            MultipartFile csv_file,
            String target
    ) {
        return pretreatmentFeign.standardized(csv_file, target);
    }

    /**
     * 归一化
     * <p>
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  归一化目标列名(可多个）
     * 返回：
     * csv数据文件
     */

    public MultipartFile normalized(
            MultipartFile csv_file,
            String target
    ) {
        return pretreatmentFeign.normalized(csv_file, target);
    }

    /**
     * 随机森林填充数据
     * <p>
     * <p>
     * 介绍:  当某个列存在缺失数据时，可以通过此方法，由一些有关联的列来计算填充该列的缺失数据
     * 参数：
     * csv_file： csv格式的数据文件
     * target：  填充目标列
     * ref：        用来关联计算的列名（多个，以逗号隔开）
     * 返回：
     * csv数据文件
     */

    public MultipartFile setRandomForest(
            MultipartFile csv_file,
            String target,
            String ref
    ) {
        return pretreatmentFeign.setRandomForest(csv_file, target, ref);
    }

    /**
     * 数据格式化
     * <p>
     * <p>
     * 介绍:  将数据转为x_train,y_train的列表格式
     * 参数：
     * csv_file： 	csv格式的数据文件
     * label：    		标签列名，即结果的y_train
     * all_used：        是否除了标签列的所有列都需要（0：否；1：是）
     * data_col：
     * 如果all_used=1，则不需要；如果all_used=0，则给出需要的列名，以逗号分隔
     * 返回：
     * json数据
     * 如：
     * {
     * "X_train":[[-1, -1], [-2, -1], [1,1], [2, 1]],
     * "Y_train":[1, 1, 2, 2]
     * }
     */

    public Map<String, Object> formatting(
            MultipartFile csv_file,
            String label,
            String all_used,
            String data_col
    ) {
        return pretreatmentFeign.formatting(csv_file, label, all_used, data_col);
    }


}
