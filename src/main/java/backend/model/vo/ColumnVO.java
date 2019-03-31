package backend.model.vo;

import backend.enumclass.ColumnType;

/**
 * Created by lienming on 2019/1/17.
 */
public class ColumnVO {
    public String columnName ;
    public ColumnType columnType ;

    public String description ;
    // NOT NULL / PRIMARY KEY...

//    private char splitChar = ';';

    //弃用
    public String toString(){
        return this.columnName + " " +
                this.columnType.toMySqlString() + " " +description;
//        return columnName+splitChar+columnType.toString()+splitChar+description;
    }

    public String toMySqlString() {
        return this.columnName + " " +
                this.columnType.toMySqlString() + " " +description;
    }

    public static ColumnType getColumnType(String sqltype){
        System.out.println("sql type :  " + sqltype) ;
        for(ColumnType ct : ColumnType.values()) {
            if( ct.sqltype.equals( sqltype.toLowerCase())) {
                return ct;
            }
        }
        return null;
    }
}
