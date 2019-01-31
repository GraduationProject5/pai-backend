package backend.service.impl;

import backend.dao.DatabaseHelper;
import backend.service.DataService;
import backend.vo.TableVO;

/**
 * Created by lienming on 2019/1/17.
 */
public class DataServiceImpl implements DataService {

    private static DatabaseHelper databaseHelper = new DatabaseHelper() ;

    public long createExperiment(long userID, String experimentName,String description){
        return databaseHelper.excuteCreateExperiment(userID,experimentName,description);
    }

    /*
        根据给定列建表，返回tableID
     */
    public long createTableByVO(long userID, TableVO tableVO) {
        return databaseHelper.excuteCreateTableByVO(userID,tableVO);
    }

    /*
            根据SQL建表，返回tableID
         */
    public long createTableByScript(long userID,TableVO tableVO,String scriptText) {
        return databaseHelper.excuteCreateTableByScript(userID,tableVO,scriptText);
    }

}
