package backend.service.impl;

import backend.dao.DatabaseHelper;
import backend.entity.Experiment;
import backend.entity.TablePO;
import backend.service.DataService;
import backend.vo.ColumnVO;
import backend.vo.TableVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lienming on 2019/1/17.
 */
@Service
public class DataServiceImpl implements DataService {

    private static DatabaseHelper databaseHelper = new DatabaseHelper() ;

    public long createExperiment(long userID, String experimentName,String description){
        return databaseHelper.executeCreateExperiment(userID,experimentName,description);
    }

    /*
        根据给定列建表，返回tableID
     */
    public long createTableByVO(long userID, String tableName,
                                List<ColumnVO> columnVOList, String description) {
        TableVO tableVO = new TableVO();
        tableVO.tableName=tableName;
        tableVO.columnVOList=columnVOList;
        tableVO.description=description;
        return databaseHelper.executeCreateTableByVO(userID,tableVO);
    }

    // 根据SQL建表，返回tableID
    public long createTableByScript(long userID,String tableName,String scriptText) {
        return databaseHelper.executeCreateTableByScript(userID,tableName,scriptText);
    }

    public void insertData(long userID,String tableName,String[] lines,String splitChar){
        databaseHelper.insertToUserTable(userID, tableName, lines, splitChar);
    }

    //返回格式 List<Map>  一个Map是一条数据行的映射{"列1:(Object)值1","..."}
    public List getData(long userID,String tableName) {
        return databaseHelper.getFromUserTable(userID,tableName);
    }


    public List<TablePO> getDatabasesByUser(long userID) {
        return databaseHelper.getDatabasesByUser(userID);
    }

    public List<Experiment> getExperimentsByUser(long userID) {
        return databaseHelper.getExperimentsByUser(userID);
    }

}
