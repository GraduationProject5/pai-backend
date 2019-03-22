package backend.service;

import backend.entity.Experiment;
import backend.entity.TablePO;
import backend.vo.ColumnVO;
import backend.vo.TableVO;

import java.util.List;

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
     * 插入数据
     * @param userID
     * @param tableName
     * @param lines
     * @param splitChar
     */
    void insertData(long userID,String tableName,String[] lines,String splitChar);

    /**
     * 查看数据库的行
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
