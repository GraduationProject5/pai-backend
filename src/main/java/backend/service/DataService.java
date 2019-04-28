package backend.service;

import backend.model.po.Experiment;
import backend.model.po.TablePO;
import backend.model.vo.ColumnVO;
import backend.model.vo.ExperimentVO;
import backend.model.vo.TableVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */
public interface DataService {

    ///////////////////////////  实验相关 begin //////////////////////////////////

    /**
     * 创建实验
     */
    Map<String, Object> createExperiment(long userID, String experimentName, String description);


    /**
     * 删除实验以及相关的数据
     */
    void deleteExperiment(Long experimentID);

    /**
     * 更新实验信息
     */
    Map<String, Object> updateExperimentInfo(ExperimentVO experimentVO);


    /**
     * 获取用户所有实验
     */
    List<Experiment> getExperimentsByUser(long userID);

    ///////////////////////////  实验相关 end //////////////////////////////////

    ///////////////////////////  数据表相关 begin //////////////////////////////////

    /**
     * 表的列属性存在VO
     */
    Map<String,Object> createTableByVO(long userID, String tableName,
                         List<ColumnVO> columnVOList, String description);

    /**
     * 表的列属性描述在ScriptText
     */
    Map<String,Object> createTableByScript(long userID, String tableName, String ScriptText);

    /**
     * 查看用户下所有数据库
     */
    List<TablePO> getDatabasesByUser(long userID);

    /**
     * 将csv数据插入数据库表
     */
    Map<Boolean, Integer> insertCsv(String userID, String fileName, String tableName, MultipartFile csvFile) throws Exception;

    /**
     * 将数据表导出为csv文件 ->> 文件预处理和特征工程
     *
     * @param userID
     * @param tableName
     * @return
     * @throws Exception
     */
    String exportCsv(String userID, String tableName) throws IOException;

    /**
     * 插入数据 [在csv实现后，弃用]
     */
    void insertData(long userID, String tableName, String[] lines, String splitChar);

    /**
     * 查看表的属性
     */
    TableVO getTableAttr(long userID, String tableName);

    /**
     * 查看数据库的行
     * //返回格式 List<Map>  一个Map是一条数据行的映射.  {"列名1:(Object)值1","..."}
     */
    List getData(long userID, String tableName);

    /**
     * 删除用户的数据库表和表相关关系
     */
    void dropUserTable(Long userID, Long tableID);

    ///////////////////////////  数据表相关 end //////////////////////////////////

    /**
     * string数组转为list
     */
    List<String> stringArrayToList(String[] strings);

}
