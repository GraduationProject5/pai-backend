package backend.model.vo;

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

    public TableVO(){
        this.columnVOList = new ArrayList<>();
    }

    //处理主键，将主键提出列，作为表约束存在声明最后
    public List<String> getPrimaryKey(){
        List<String> result = new ArrayList<>();
        for (ColumnVO columnVO : columnVOList ) {
             if(columnVO.description.contains("PRIMARY KEY")) {
                 result.add(columnVO.columnName);
             }
        }
        return result;
    }


    public String toString(){
        String str = "";
        for(ColumnVO c : columnVOList) {
            str = str + c.toString() + lineSeperator ;
        }
        return str;
    }
}
