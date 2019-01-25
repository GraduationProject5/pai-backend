package backend.dao;

import backend.enumclass.ColumnType;
import backend.vo.ColumnVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/GraduationProject5?characterEncoding=UTF-8&useSSL=true&serverTimezone=Asia/Shanghai";
    //数据库连接账号密码
    private String user = "root" ;
    private String password = "xx" ;

    private Connection con = null ;

    public void init(){
        try {
            //找到驱动
            Class.forName(driver);

            //建立连接
            this.con = DriverManager.getConnection(url,user,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String formatMysqlCreate(String tableName, List<ColumnVO> clist){
        String createSql = "CREATE TABLE "+tableName+ "(" ;
        for(int i=0;i<clist.size()-1;i++) {
            createSql += clist.get(i).toMySqlString()+",";
        }
        createSql += clist.get(clist.size()-1).toMySqlString() + ");" ;
        return createSql;
    }

    public void createTable(String mysqlText){
        Statement st = null;
        try {
            st = con.createStatement();
            st.execute(mysqlText);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        DatabaseHelper dh = new DatabaseHelper();
        dh.init();
        List<ColumnVO> clist = new ArrayList<>();
        ColumnVO cvo1 = new ColumnVO() ;
        cvo1.columnName = "c1" ;
        cvo1.columnType = ColumnType.INT;
        cvo1.description = "NOT NULL PRIMARY KEY" ;
        ColumnVO cvo2 = new ColumnVO() ;
        cvo2.columnName = "c2" ;
        cvo2.columnType = ColumnType.STRING;
        cvo2.description = "NOT NULL" ;
        clist.add(cvo1);
        clist.add(cvo2);

        String sql = dh.formatMysqlCreate("test1",clist);
        dh.createTable(sql);

    }
}
