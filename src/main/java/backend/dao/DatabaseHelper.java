package backend.dao;

import backend.daorepository.*;
import backend.entity.*;
import backend.vo.ColumnVO;
import backend.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Repository
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
            st.close();
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
            return createTableRelationship(userID,tableVO.tableName,tableVO.description);
        else
            return -1;
    }

    //return tableID
    public long executeCreateTableByScript(long userID,String tableName,String sql){
        if(createTableByText(sql))
            return createTableRelationship(userID,tableName,"");
        else
            return -1;
    }

    public long createTableRelationship(long userID,String tableName,String description){
//        String tableName = tableVO.tableName;
//        List<ColumnVO> list = tableVO.columnVOList;
//        String description = tableVO.description;

        TablePO tablePO = createTablePO(tableName,description);
        long tableID = tablePO.getTableID();

        R_User_Table rut = createR_User_Table(userID,tablePO);
        long rutID = rut.getRutID();

        return tableID;
    }

    public TablePO createTablePO(String tableName,String description){
        TablePO tp = new TablePO(tableName,description);
//        System.out.println(tp.getTableID());
//        System.out.println(tp.getTableName());
//        System.out.println(tp.getDescription());

        TablePO tablePO =  tablePORepository.save(tp);
        return  tablePO ;
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

    public void insertToUserTable(long userID,String tableName,String[] lines,String splitChar) {

        try {
            this.con.setAutoCommit(false);

            String sql = formatInsertExpression(tableName);
            PreparedStatement ps = con.prepareStatement
                    (sql);

            int columnNum = getTableColumns(tableName);
            for(int i=0;i<lines.length;i++){
//                System.out.println(lines[i]);
                String[] parts = lines[i].split(splitChar);

                for(int j=0;j<parts.length;j++) {
                    String part = parts[j];
//                    System.out.println(part);

                    //TODO 日期？
                    if (isDigit(part)) {
                        //Long or Int ?
                        ps.setInt(j+1,Integer.parseInt(part));
                    }
                    else if(isBoolean(part)){
                        ps.setBoolean(j+1,Boolean.parseBoolean(part));
                    } else {
                        ps.setString(j + 1, part);
                    }
                }
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();
            ps.close();
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

    //TODO 没有测试！获取用户表的所有行
    public List getFromUserTable(long userID,String tableName){
        TablePO tablePO = tablePORepository.findByTableName(tableName);
        long tableID = tablePO.getTableID();

        String query_sql = "SELECT * FROM "+tableName+";";

        Statement st = null;
        ResultSet resultSet = null ;
        List list = new ArrayList();

        try {
            st = con.createStatement();
            resultSet = st.executeQuery(query_sql);

            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();//获取行的数量

            while(resultSet.next()){
                Map rowData = new HashMap();//声明Map
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), resultSet.getObject(i));//获取键名及值
                }
                list.add(rowData);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    //TODO 获取用户的所有表信息[tableID,tableName,description]，不包含表的列属性
    public List<TablePO> getDatabasesByUser(long userID){

        List<R_User_Table> rutlist = rUserTableRepository.findByUserID(userID);

        List<TablePO> tablePOList = new ArrayList<>();
        for(R_User_Table rut:rutlist){
            long tableID = rut.getTableID();
            TablePO tablePO = tablePORepository.findByTableID(tableID);
            tablePOList.add(tablePO);
        }
        if(tablePOList.size()>0)
            return tablePOList;
        else
            return null;
    }

    //TODO 测试！
    public List<Experiment> getExperimentsByUser(long userID){
        List<R_User_Experiment> ruelist = rUserExperimentRepository.findByUserID(userID);
        List<Experiment> elist = new ArrayList<>();
        for ( R_User_Experiment rue :ruelist){
            long experimentID = rue.getExperimentID() ;
            Experiment e = experimentRepository.findByExperimentID(experimentID);
            elist.add(e);
        }
        if(elist.size()>0)
            return elist;
        else
            return null;
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

    //test
//    public static void main(String[] args){
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
//    }


}
