package backend.vo;

import backend.enumclass.ColumnType;

/**
 * Created by lienming on 2019/1/17.
 */
public class ColumnVO {
    public String columnName ;
    public ColumnType columnType ;
    public String description ;

    private char splitChar = ';';

    public String toString(){
        return columnName+splitChar+columnType.toString()+splitChar+description;
    }
}
