package backend.service;

import backend.vo.TableVO;

/**
 * Created by lienming on 2019/1/17.
 */
public interface DataService {

    long createExperiment(long userID, String experimentName,String description);

    //表的列属性存在VO
    long createTableByVO(long userID,  TableVO tableVO);

    //表的列属性描述在ScriptText
    long createTableByScript(long userID, TableVO tableVO, String ScriptText);

    //TODO 插入数据
    void insertData(long userID);
}
