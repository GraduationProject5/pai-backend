package backend.dao;

import backend.entity.*;
import backend.enumclass.ColumnType;
import backend.vo.ColumnVO;
import backend.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DatabaseHelper {

    @Autowired
    TablePORepository tablePORepository ;
    @Autowired
    RUserTableRepository rUserTableRepository;
    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    RUserExperimentRepository rUserExperimentRepository;
    @Autowired
    UserRepository userRepository;

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/GraduationProject5?characterEncoding=UTF-8&useSSL=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true";

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
    public long executeCreateTableByVO(long userID,TableVO tableVO){
        String sql = formatMysqlCreate(tableVO);
        if(createTableByText(sql))
            return createTableRelationship(userID,tableVO);
        else
            return -1;
    }

    //return tableID
    public long executeCreateTableByScript(long userID,TableVO tableVO,String sql){
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
        System.out.println(tp.getTableID());
        System.out.println(tp.getTableName());
        System.out.println(tp.getDescription());

        tablePORepository.save(tp);
        return  null ;
    }

    public R_User_Table createR_User_Table(long userID,TablePO tablePO_getID){
        long tableID = tablePO_getID.getTableID();
        R_User_Table rut = new R_User_Table(userID,tableID);
        R_User_Table rut_getID = rUserTableRepository.save(rut);
        return rut_getID;
    }




    public long executeCreateExperiment(long userID, String experimentName,String description){
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

    //返回列数
    public int getTableColumns(String tableName) {
        DatabaseMetaData dbmd;
        int columnNum = 0 ;
        try {
            dbmd = con.getMetaData();
            ResultSet colRet = dbmd.getColumns(null, "%",tableName,"%");

            String columnName;
            String columnType;
            while(colRet.next()) {
//                columnName = colRet.getString("COLUMN_NAME");
//                columnType = colRet.getString("TYPE_NAME");
//                int datasize = colRet.getInt("COLUMN_SIZE");
//                int digits = colRet.getInt("DECIMAL_DIGITS");
//                int nullable = colRet.getInt("NULLABLE");
//                System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ nullable);
                columnNum++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnNum;
    }


    //lines:每行  splitChar 列分隔符

    public void insertData(long userID,String tableName,String[] lines,String splitChar) {

        try {
            this.con.setAutoCommit(false);

            String sql = formatInsertExpression(tableName);
            PreparedStatement ps = con.prepareStatement
                    (sql);

            int columnNum = getTableColumns(tableName);
            for(int i=0;i<lines.length;i++){
                String[] parts = lines[i].split(splitChar);
                for(int j=0;j<parts.length;j++) {
                    String part = parts[j];
                    if (isDigit(part)) {
                        //Long or Int ?
                        ps.setInt(j+1,Integer.parseInt(part));
                    }
                    else if(isBoolean(part)){
                        ps.setBoolean(j+1,Boolean.parseBoolean(part));
                    } else
                        ps.setString(j+1,part);
                }
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public String formatInsertExpression(String tableName){
        String prefix = "INSERT INTO " + tableName + " VALUES(" ;
        String postfix = ")";
        int columnNum = getTableColumns(tableName);
        for(int i=0;i<columnNum-1;i++){
            prefix += "?,";
        }
        prefix += "?"+postfix;
        return prefix;
    }

    public boolean isDigit(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public boolean isBoolean(String str){
        String lower_str = str.toLowerCase();
        if("true".equals(lower_str)||"false".equals(lower_str))
            return true;
        return false ;
    }


    public static void main(String[] args){
//        DatabaseHelper dh = new DatabaseHelper();

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
//        dh.executeCreateTableByVO(1,tableVO);



//未测试成功 ，因为要启动项目 @Autowired repository
//        String[] lines = {
//                "2,asd,ewr",
//                "23,ss,sss"
//        };
//
//        dh.insertData(1,"user",lines,",");

//        User user = new User("223re","ASDw");
//       System.out.println(null==dh.userRepository.findByEmailAndPassword("javalem@163.com","asd"));
    }


}
