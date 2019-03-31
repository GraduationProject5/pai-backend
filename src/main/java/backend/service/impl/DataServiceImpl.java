package backend.service.impl;

import backend.dao.DatabaseHelper;
import backend.model.po.Experiment;
import backend.model.po.TablePO;
import backend.service.DataService;
import backend.model.vo.ColumnVO;
import backend.model.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DatabaseHelper databaseHelper;
    @Value(value = "${spring.resources.static-locations}")
    String newPath;

    public long createExperiment(long userID, String experimentName, String description) {
        return databaseHelper.executeCreateExperiment(userID, experimentName, description);
    }

    /*
        根据给定列建表，返回tableID
     */
    public long createTableByVO(long userID, String tableName,
                                List<ColumnVO> columnVOList, String description) {
        TableVO tableVO = new TableVO();
        tableVO.tableName = tableName;
        tableVO.columnVOList = columnVOList;
        tableVO.description = description;
        return databaseHelper.executeCreateTableByVO(userID, tableVO);
    }

    // 根据SQL建表，返回tableID
    public long createTableByScript(long userID, String tableName, String scriptText) {
        return databaseHelper.executeCreateTableByScript(userID, tableName, scriptText);
    }

    // 将scv数据插入数据库表
    public Map<Boolean, Integer> insertCsv(String userID, String fileName, String tableName, MultipartFile csvFile) {

        //String rootPath = ClassUtils.getDefaultClassLoader().getResource("PaiBackendApplication").getPath();

        Map<Boolean, Integer> map = null;

        File uploadDir = new File(newPath);
        //如果不存在，則新建文件夹
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File tempFile = new File(newPath + new Date().getTime() + ".csv");
        //初始化输入流
        InputStream is = null;
        try {
            //将上传的文件写入新建的文件中
            csvFile.transferTo(tempFile);
            map = databaseHelper.insertCsv(userID, tableName, tempFile);
            //删除临时文件
            tempFile.delete();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        map.put(false, 0);
        return map;
    }

    public void insertData(long userID, String tableName, String[] lines, String splitChar) {
        databaseHelper.insertToUserTable(userID, tableName, lines, splitChar);
    }

    public TableVO getTableAttr(long userID, String tableName) {
        return databaseHelper.getColoumAttrFromTable(userID, tableName);
    }

    //返回格式 List<Map>  一个Map是一条数据行的映射.  {"列名1:(Object)值1","..."}
    public List getData(long userID, String tableName) {
        return databaseHelper.getRecordFromUserTable(userID, tableName);
    }


    public List<TablePO> getDatabasesByUser(long userID) {

        return databaseHelper.getDatabasesByUser(userID);
    }

    public List<Experiment> getExperimentsByUser(long userID) {
        return databaseHelper.getExperimentsByUser(userID);
    }

}
