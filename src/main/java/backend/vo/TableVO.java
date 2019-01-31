package backend.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienming on 2019/1/17.
 */
public class TableVO {
    public String tableName ;
    public List<ColumnVO> columnVOList;
    public String description ;


    private static String lineSeperator = System.getProperty("line.separator");

    public String toString(){
        String str = "";
        for(ColumnVO c : columnVOList) {
            str = str + c.toString() + lineSeperator ;
        }
        return str;
    }
}
