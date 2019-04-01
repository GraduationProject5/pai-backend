package backend.service;

import backend.model.po.Experiment;
import backend.model.po.TablePO;
import backend.model.vo.ColumnVO;
import backend.model.vo.ExperimentVO;
import backend.model.vo.TableVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */
public interface DataService {

    /**
     * 创建实验
     *
     * @param userID
     * @param experimentName
     * @param description
     * @return
     */
    long createExperiment(long userID, String experimentName, String description);

    public Map<String,Object> updateExperimentInfo(ExperimentVO experimentVO);

    /**
     * 表的列属性存在VO
     * @param userID
     * @param tableName
     * @param columnVOList
     * @param description
     * @return
     */
    long createTableByVO(long userID,  String tableName,
                          List<ColumnVO> columnVOList, String description);

    /**
     * 表的列属性描述在ScriptText
     * @param userID
     * @param tableName
     * @param ScriptText
     * @return
     */
    long createTableByScript(long userID, String tableName, String ScriptText);


    /**
     * 将scv数据插入数据库表
     *
     * @param fileName
     * @param tableName
     * @param csvFile
     */
    Map<Boolean, Integer> insertCsv(String userID, String fileName, String tableName, MultipartFile csvFile) throws Exception;

    /**
     * 插入数据
     * @param userID
     * @param tableName
     * @param lines
     * @param splitChar
     */
    void insertData(long userID,String tableName,String[] lines,String splitChar);

    /**
     * 查看表的属性
     * @param userID
     * @param tableName
     * @return
     */
    TableVO getTableAttr(long userID, String tableName);

    /**
     * 查看数据库的行
     * //返回格式 List<Map>  一个Map是一条数据行的映射.  {"列名1:(Object)值1","..."}
     * @param userID
     * @param tableName
     * @return
     */
    List getData(long userID,String tableName);

    /**
     * 查看用户下所有数据库
     * @param userID
     * @return
     */
    List<TablePO> getDatabasesByUser(long userID);

    /**
     *
     * @param userID
     * @return
     */
    List<Experiment> getExperimentsByUser(long userID);

}
