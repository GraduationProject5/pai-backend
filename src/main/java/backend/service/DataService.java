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

    long createExperiment(long userID, String experimentName,String description);

    //表的列属性存在VO
    long createTableByVO(long userID,  String tableName,
                          List<ColumnVO> columnVOList, String description);

    //表的列属性描述在ScriptText
    long createTableByScript(long userID, String tableName, String ScriptText);

    //插入数据
    void insertData(long userID,String tableName,String[] lines,String splitChar);

    //查看数据库的行
    List getData(long userID,String tableName);

    //查看用户下所有数据库
    List<TablePO> getDatabasesByUser(long userID);

    List<Experiment> getExperimentsByUser(long userID);

}
