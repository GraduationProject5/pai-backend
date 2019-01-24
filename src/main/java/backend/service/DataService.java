package backend.service;

import backend.vo.TableVO;

/**
 * Created by lienming on 2019/1/17.
 */
public interface DataService {

    long createTableByVO(String userID, String experimentID, TableVO tableVO);

    long createTableByScript(String userID, String experimentID, String ScriptText);

}
