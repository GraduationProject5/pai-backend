package backend.service;

import backend.vo.Table;

/**
 * Created by lienming on 2019/1/17.
 */
public interface DataService {

    String createTable(String userID,String projectID,Table table);

}
