package backend.vo;

import java.util.List;

/**
 * Created by lienming on 2019/1/17.
 */
public class Table {
    public String tableName ;
    public List<Column> columnList;
    public String description ;
    private static String lineSeperator = System.getProperty("line.separator");

    public String toString(){
        String str = "";
        for(Column c : columnList) {
            str = str + c.toString() + lineSeperator ;
        }
        return str;
    }
}
