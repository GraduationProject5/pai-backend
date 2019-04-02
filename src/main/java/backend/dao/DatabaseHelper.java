package backend.dao;

import backend.daorepository.*;
import backend.enumclass.ColumnType;
import backend.model.po.*;
import backend.model.vo.ColumnVO;
import backend.model.vo.TableVO;
import backend.util.config.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 使用这个类的原意是:有一些不能用Spring Data JPA处理的SQL查询用这个类代替实现.
 */
@Repository
public class DatabaseHelper {

    @Autowired
    TablePORepository tablePORepository;
    @Autowired
    RUserTableRepository rUserTableRepository;
    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    RUserExperimentRepository rUserExperimentRepository;
    @Autowired
    UserRepository userRepository;

    private String driver = "com.mysql.cj.jdbc.Driver";
//    @Value("${spring.datasource.url}")
//    private String url = "jdbc:mysql://47.102.152.224:3306/GraduationProject5?characterEncoding=UTF-8&useSSL=true&verifyServerCertificate=false&serverTimezone=Asia/Shanghai";
//    //数据库连接账号密码
//    @Value("${spring.datasource.username}")
//    private String username = "root";
//    @Value("${spring.datasource.password}")
//    private String password = "1156489606cbB!";

    private Connection con = null;

    public DatabaseHelper(@Value("${spring.datasource.username}") String username,
                          @Value("${spring.datasource.password}") String password,
                          @Value("${spring.datasource.url}") String url) {
        init(url, username, password);
    }

    private void init(String url, String username, String password) {
        try {
            //Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
//            Class.forName(driver);
            //建立连接
            this.con = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String formatMysqlCreate(long userID, TableVO tableVO) {

        String tableName = formatUserTableName(userID, tableVO.tableName);
        List<ColumnVO> clist = tableVO.columnVOList; //
        List<String> primaryKeyList = tableVO.getPrimaryKey();

        String createSql = "CREATE TABLE " + tableName + "(";
        for (int i = 0; i < clist.size() - 1; i++) {
            createSql += clist.get(i).toMySqlString() + ",";
        }
        createSql += clist.get(clist.size() - 1).toMySqlString() ;
        if(primaryKeyList.size()>0){
            createSql += ",PRIMARY KEY(" ;
            for(int i=0; i<primaryKeyList.size()-1;i++) {
                createSql += primaryKeyList.get(i)+",";
            }
            createSql += primaryKeyList.get(primaryKeyList.size()-1);
            createSql += ")";
        }
        createSql += ");";
        return createSql;
    }

    public boolean createTableByText(String mysqlText) {
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
    public long executeCreateTableByVO(long userID, TableVO tableVO) {
        String sql = formatMysqlCreate(userID, tableVO);
        if (createTableByText(sql))
            return createTableRelationship(userID, tableVO.tableName, tableVO.description);
        else
            return DatabaseProperties.Code_ExecuteSqlFail;
    }

    //return tableID
    public long executeCreateTableByScript(long userID, String tableName, String sql) {

        sql = sql.replaceFirst(tableName, formatUserTableName(userID, tableName));
        if (createTableByText(sql))
            return createTableRelationship(userID, tableName, "no description");
        else
            return DatabaseProperties.Code_ExecuteSqlFail;
    }

    public long createTableRelationship(long userID, String tableName, String description) {
//        String tableName = tableVO.tableName;
//        List<ColumnVO> list = tableVO.columnVOList;
//        String description = tableVO.description;

        TablePO tablePO = createTablePO(tableName, description);
        long tableID = tablePO.getTableID();

        R_User_Table rut = createR_User_Table(userID, tablePO);
        long rutID = rut.getRutID();

        return tableID;
    }

    public TablePO createTablePO(String tableName, String description) {
        TablePO tp = new TablePO(tableName, description);
//        System.out.println(tp.getTableID());
//        System.out.println(tp.getTableName());
//        System.out.println(tp.getDescription());

        TablePO tablePO = tablePORepository.save(tp);
        return tablePO;
    }

    public R_User_Table createR_User_Table(long userID, TablePO tablePO_getID) {
        long tableID = tablePO_getID.getTableID();
        R_User_Table rut = new R_User_Table(userID, tableID);
        R_User_Table rut_getID = rUserTableRepository.save(rut);
        return rut_getID;
    }


    public long executeCreateExperiment(long userID, String experimentName, String description) {
        Experiment experiment = createExperiment(experimentName, description);
        long eid = experiment.getExperimentID();
        createR_User_Experiment(userID, experiment);
        return eid;
    }

    public Experiment createExperiment(String experimentName, String description) {
        Experiment experiment = new Experiment(experimentName, description);
        Experiment experiment_getID = experimentRepository.save(experiment);
        return experiment_getID;
    }

    public R_User_Experiment createR_User_Experiment(long userID, Experiment experiment_getID) {
        long experimentID = experiment_getID.getExperimentID();
        R_User_Experiment rue = new R_User_Experiment(userID, experimentID);
        R_User_Experiment rue_getID = rUserExperimentRepository.save(rue);
        return rue_getID;
    }

    //返回列数 输入的tableName已经过添加前缀处理
    public int getTableColumns(String tableName) {
        DatabaseMetaData dbmd;
        int columnNum = 0;
        try {
            dbmd = con.getMetaData();
            ResultSet colRet = dbmd.getColumns(null, "%", tableName, "%");

            String columnName;
            String columnType;
            while (colRet.next()) {
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

    // 把csv数据插入数据库
    public Map<Boolean, Integer> insertCsv(String userID, String tableName, File tmpFile) throws IOException {

        HashMap<Boolean, Integer> resultMap = new HashMap<>();

        String tmpPath = tmpFile.getAbsolutePath();

        String path = tmpFile.getCanonicalPath();

        System.out.println(tmpPath);
        System.out.println(path);
        try {

//            String insertSql = "load data infile \'" + path + "\'\n" +
//                    "into table " + "user"+userID+"_"+tableName + " character set gb2312\n" +
//                    "fields terminated by ',' optionally enclosed by '\"' escaped by '\"' \n" +
//                    "lines terminated by '\\r\\n';";

//            String insertSql = "load data local infile \'" + path + "\'\n" +
//                    "into table " + "user"+userID+"_"+tableName + "\n" +
//                    "fields terminated by ',' optionally enclosed by '\"' escaped by '\"' \n" +
//                    "lines terminated by '\\r\\n';";

            String insertSql = "LOAD DATA LOCAL INFILE \'" + path + "\' INTO TABLE " + "user" + userID + "_" + tableName + " FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n';";

            System.out.println(insertSql);
            Statement stmt = con.createStatement();
            int resultSet = stmt.executeUpdate(insertSql);
            stmt.close();
            resultMap.put(true, resultSet);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(false, 0);
            return resultMap;
        }
    }
    //lines:每行  splitChar 列分隔符

    public void insertToUserTable(long userID, String tableName, String[] lines, String splitChar) {

        try {
            this.con.setAutoCommit(false);

            String sql = formatInsertExpression(userID, tableName);
            PreparedStatement ps = con.prepareStatement
                    (sql);

            int columnNum = getTableColumns(formatUserTableName(userID, tableName));
            for (int i = 0; i < lines.length; i++) {
//                System.out.println(lines[i]);
                String[] parts = lines[i].split(splitChar);

                for (int j = 0; j < parts.length; j++) {
                    String part = parts[j];
//                    System.out.println(part);

                    //TODO 日期？
                    if (isDigit(part)) {
                        //Long or Int ?
                        ps.setInt(j + 1, Integer.parseInt(part));
                    } else if (isBoolean(part)) {
                        ps.setBoolean(j + 1, Boolean.parseBoolean(part));
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

    public TableVO getColoumAttrFromTable(long userID, String tableName) {
        //先find 再对手动查询作命名处理
        TablePO tablePO = tablePORepository.findByTableName(tableName);

        tableName = formatUserTableName(userID, tableName);

        TableVO tableVO = new TableVO();
        tableVO.tableName = tableName;
        tableVO.description = tablePO.getDescription();

        DatabaseMetaData dbmd;
        try {
            dbmd = con.getMetaData();
            ResultSet colRet = dbmd.getColumns(null, "%", tableName, "%");

            String columnName;
            String columnType;
            String columnDescription;

            while (colRet.next()) {
                columnName = colRet.getString("COLUMN_NAME");
                columnType = colRet.getString("TYPE_NAME");
                columnDescription = colRet.getString("REMARKS");
                ColumnVO columnVO = new ColumnVO();

                columnVO.columnName = columnName;
                columnVO.columnType = ColumnVO.getColumnType(columnType);
                columnVO.description = columnDescription;

                tableVO.columnVOList.add(columnVO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableVO;
    }

    //TODO 没有测试！获取用户表的所有行
    public List getRecordFromUserTable(long userID, String tableName) {
        tableName = formatUserTableName(userID, tableName);
        TablePO tablePO = tablePORepository.findByTableName(tableName);
//        long tableID = tablePO.getTableID();

        String query_sql = "SELECT * FROM " + tableName + ";";

        Statement st = null;
        ResultSet resultSet = null;
        List list = new ArrayList();

        try {
            st = con.createStatement();
            resultSet = st.executeQuery(query_sql);

            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();//获取行的数量

            while (resultSet.next()) {
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

    //获取用户的所有表信息[tableID,tableName,description]，不包含表的列属性
    public List<TablePO> getDatabasesByUser(long userID) {

        List<R_User_Table> rutlist = rUserTableRepository.findByUserID(userID);

        List<TablePO> tablePOList = new ArrayList<>();
        for (R_User_Table rut : rutlist) {
            long tableID = rut.getTableID();
            TablePO tablePO = tablePORepository.findByTableID(tableID);
            tablePOList.add(tablePO);
        }
        if (tablePOList.size() > 0)
            return tablePOList;
        else  //无则返回null
            return null;
    }

    public List<Experiment> getExperimentsByUser(long userID) {
        List<R_User_Experiment> ruelist = rUserExperimentRepository.findByUserID(userID);
        List<Experiment> elist = new ArrayList<>();
        for (R_User_Experiment rue : ruelist) {
            long experimentID = rue.getExperimentID();
            Experiment e = experimentRepository.findByExperimentID(experimentID);
            elist.add(e);
        }
        if (elist.size() > 0)
            return elist;
        else
            return null;
    }

    public String formatInsertExpression(long userID, String tableName) {
        String prefix = "INSERT INTO "
                + formatUserTableName(userID, tableName) + " VALUES(";

        String postfix = ")";
        int columnNum = getTableColumns(tableName);
        for (int i = 0; i < columnNum - 1; i++) {
            prefix += "?,";
        }
        prefix += "?" + postfix;
        return prefix;
    }

    public boolean isDigit(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public boolean isBoolean(String str) {
        String lower_str = str.toLowerCase();
        if ("true".equals(lower_str) || "false".equals(lower_str))
            return true;
        return false;
    }

    private String formatUserTableName(long userID, String tableName) {
        return "user" + userID + "_" + tableName;
    }

    //todo

    /**
     * 删除用户表,以及用户_表关系
     */
    public void dropUserTable(Long userID,Long tableID){
        TablePO tablePO = tablePORepository.findByTableID(tableID);
        String tableName = tablePO.getTableName();

        String tableNameInMySQL = formatUserTableName(userID,tableName);
        //drop table
        Statement st = null;
        String drop_sql = "drop TABLE "+tableNameInMySQL+";";
        try {
            st = con.createStatement();
            st.execute(drop_sql);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //delete record in tablePO
        tablePORepository.delete(tablePO);

        //delete record in r_user_table
        R_User_Table r_user_table = rUserTableRepository.findByTableID(tableID);
        rUserTableRepository.delete(r_user_table);
        //note: rUserTableRepository.deleteByTableID()会报错,原因不清楚


    }


}
