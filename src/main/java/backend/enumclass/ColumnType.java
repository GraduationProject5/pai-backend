package backend.enumclass;

/**
 * Created by lienming on 2019/1/17.
 */
public enum ColumnType {
    INT,BIGINT,DOUBLE,DECIMAL,STRING,BOOLEAN,DATETIME;

    public String toMySqlString(){
        if("STRING".equals(this.name()))
            return "VARCHAR(100)";
        else
            return this.name();
    }

//    public static void main(String[] args){
//        ColumnType a = ColumnType.STRING ;
//        System.out.println(a.toMySqlString());
//    }
}
