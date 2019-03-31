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

    /**
     *    输出为字符串时 描述中不带主键信息！！！！
     * @return
     */
    public String toMySqlString() {
        return this.columnName + " " +
                this.columnType.toMySqlString() + " " +description.replace("PRIMARY KEY","");
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
