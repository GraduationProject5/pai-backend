package backend.service.impl;

import backend.service.DataService;
import backend.vo.TableVO;

/**
 * Created by lienming on 2019/1/17.
 */
public class DataServiceImpl implements DataService {

    //TODO
    /*
        根据给定列，直接在数据库里建表，返回tableID
     */
    public long createTableByVO(String userID, String experimentID, TableVO tableVO) {
        return 0;
    }

    //TODO
    /*
            根据SQL脚本，直接在数据库里建表，返回tableID
         */
    public long createTableByScript(String userID, String experimentID, String ScriptText) {
        return 0;
    }

}
