package backend.dao;

import backend.entity.Experiment;
import backend.entity.R_User_Experiment;
import backend.entity.R_User_Table;
import backend.entity.TablePO;
import backend.enumclass.ColumnType;
import backend.vo.ColumnVO;
import backend.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    @Autowired
    TablePORepository tablePORepository ;
    @Autowired
    RUserTableRepository rUserTableRepository;
    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    RUserExperimentRepository rUserExperimentRepository;


    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/GraduationProject5?characterEncoding=UTF-8&useSSL=true&serverTimezone=Asia/Shanghai";
    //数据库连接账号密码
    private String user = "root" ;
    private String password = "xx" ;

    private Connection con = null ;

    public DatabaseHelper(){
        init();
    }

    private void init(){
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








    public String formatMysqlCreate(TableVO tableVO){
        String tableName = tableVO.tableName;
        List<ColumnVO> clist = tableVO.columnVOList; //

        String createSql = "CREATE TABLE "+tableName+ "(" ;
        for(int i=0;i<clist.size()-1;i++) {
            createSql += clist.get(i).toMySqlString()+",";
        }
        createSql += clist.get(clist.size()-1).toMySqlString() + ");" ;
        return createSql;
    }

    public boolean createTableByText(String mysqlText){
        Statement st = null;
        try {
            st = con.createStatement();
            st.execute(mysqlText);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //return tableID
    public long excuteCreateTableByVO(long userID,TableVO tableVO){
        String sql = formatMysqlCreate(tableVO);
        if(createTableByText(sql))
            return createTableRelationship(userID,tableVO);
        else
            return -1;
    }

    //return tableID
    public long excuteCreateTableByScript(long userID,TableVO tableVO,String sql){
        if(createTableByText(sql))
            return createTableRelationship(userID,tableVO);
        else
            return -1;
    }

    public long createTableRelationship(long userID,TableVO tableVO){
        String tableName = tableVO.tableName;
        List<ColumnVO> list = tableVO.columnVOList;
        String description = tableVO.description;

        TablePO tablePO = createTablePO(tableName,description);
        long tableID = tablePO.getTableID();

        R_User_Table rut = createR_User_Table(userID,tablePO);
        long rutID = rut.getRutID();

        return tableID;
    }

    public TablePO createTablePO(String tableName,String description){
        TablePO tp = new TablePO(tableName,description);
        TablePO tp_getID = tablePORepository.save(tp);
        return tp_getID;
    }

    public R_User_Table createR_User_Table(long userID,TablePO tablePO_getID){
        long tableID = tablePO_getID.getTableID();
        R_User_Table rut = new R_User_Table(userID,tableID);
        R_User_Table rut_getID = rUserTableRepository.save(rut);
        return rut_getID;
    }






    public long excuteCreateExperiment(long userID, String experimentName,String description){
        Experiment experiment = createExperiment(experimentName,description);
        long eid = experiment.getExperimentID();
        createR_User_Experiment(userID,experiment);
        return eid;
    }

    public Experiment createExperiment(String experimentName,String description){
        Experiment experiment = new Experiment(experimentName,description);
        Experiment experiment_getID = experimentRepository.save(experiment);
        return experiment_getID;
    }

    public R_User_Experiment createR_User_Experiment(long userID,Experiment experiment_getID){
        long experimentID = experiment_getID.getExperimentID();
        R_User_Experiment rue = new R_User_Experiment(userID,experimentID);
        R_User_Experiment rue_getID = rUserExperimentRepository.save(rue);
        return rue_getID;
    }


//    public static void main(String[] args){
//        DatabaseHelper dh = new DatabaseHelper();
//
//        List<ColumnVO> clist = new ArrayList<>();
//        ColumnVO cvo1 = new ColumnVO() ;
//        cvo1.columnName = "c1" ;
//        cvo1.columnType = ColumnType.INT;
//        cvo1.description = "NOT NULL PRIMARY KEY" ;
//        ColumnVO cvo2 = new ColumnVO() ;
//        cvo2.columnName = "c2" ;
//        cvo2.columnType = ColumnType.STRING;
//        cvo2.description = "NOT NULL" ;
//        clist.add(cvo1);
//        clist.add(cvo2);
//        TableVO tableVO = new TableVO();
//        tableVO.tableName="test1";
//        tableVO.columnVOList=clist;
//        String sql = dh.formatMysqlCreate(tableVO);
//        dh.createTable(sql);
//
//    }


}
