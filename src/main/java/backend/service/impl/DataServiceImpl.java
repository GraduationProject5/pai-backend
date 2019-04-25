package backend.service.impl;

import backend.dao.DatabaseHelper;
import backend.daorepository.ExperimentRepository;
import backend.daorepository.RUserExperimentRepository;
import backend.model.po.Experiment;
import backend.model.po.TablePO;
import backend.model.vo.ExperimentVO;
import backend.service.DataService;
import backend.model.vo.ColumnVO;
import backend.model.vo.TableVO;
import backend.service.ScenarioService;
import backend.util.json.HttpResponseHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by lienming on 2019/1/17.
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    ScenarioService scenarioService;

    @Autowired
    ExperimentRepository experimentRepository;
    @Autowired
    RUserExperimentRepository rUserExperimentRepository;

    @Autowired
    private DatabaseHelper databaseHelper;

    @Value(value = "${spring.resources.static-locations}")
    String newPath;


    public Map<String, Object> createExperiment(long userID, String experimentName, String description) {
        Map<String, Object> result = HttpResponseHelper.newResultMap();

        long experimentID = databaseHelper.executeCreateExperiment(userID, experimentName, description);

        if (experimentID > 0) {
            result.put("result", true);
            result.put("experimentID", experimentID);
        } else {
            result.put("result", false);
        }
        return result;
    }

    @Override
    public void deleteExperiment(Long experimentID) {
        //dataParams, dataResults, dataSet, edges, experiment, nodes, r_user_experiment
        scenarioService.clearScenario(experimentID);
        databaseHelper.deleteExperimentPOAndRelation(experimentID);
    }

    @Override
    public Map<String, Object> updateExperimentInfo(ExperimentVO experimentVO) {
        Long experimentID = experimentVO.experimentID;
        Experiment experiment = experimentRepository.findByExperimentID(experimentID);
        Map<String, Object> result = HttpResponseHelper.newResultMap();
        if (experiment == null) {
            result.put("result", false);
            result.put("message", "No such ExperimentID");
            return result;
        } else {
            if (experimentVO.experimentName != null)
                experiment.setExperimentName(experimentVO.experimentName);
            if (experimentVO.description != null)
                experiment.setDescription(experimentVO.description);
            experimentRepository.save(experiment);
            result.put("result", true);
            return result;
        }
    }

    /*
        根据给定列建表，成功返回(Long)tableID，失败返回(String)errorMessage
     */
    public Map<String, Object> createTableByVO(long userID, String tableName,
                                               List<ColumnVO> columnVOList, String description) {
        TableVO tableVO = new TableVO();
        tableVO.tableName = tableName;
        tableVO.columnVOList = columnVOList;
        tableVO.description = description;
        return databaseHelper.executeCreateTableByVO(userID, tableVO);
    }

    // 根据SQL建表，成功返回(Long)tableID，失败返回(String)errorMessage
    public Map<String, Object> createTableByScript(long userID, String tableName, String scriptText) {
        return databaseHelper.executeCreateTableByScript(userID, tableName, scriptText);


    }

    private Object handleCreateTableResult(Map<String, Object> result) {
        boolean createSuccess = (boolean) result.get("result");
        if (createSuccess) {
            Long tableID = (Long) result.get("tableID");
            return tableID;
        } else {
            String errorMessage = (String) result.get("message");
            return errorMessage;
        }
    }

    // 将csv数据插入数据库表
    public Map<Boolean, Integer> insertCsv(String userID, String fileName, String tableName, MultipartFile csvFile) {

        Map<Boolean, Integer> map = null;

        File uploadDir = new File(newPath);
        //如果不存在，则新建文件夹
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

    // 将数据库表导出为csv文件，进行数据预处理
    public File exportCsv(String userID, String tableName) throws IOException {

//        File exportDir = new File(newPath);
//        //如果不存在，新建文件夹
//        if (!exportDir.exists()) {
//            exportDir.mkdirs();
//        }
        //导出的文件路径+文件名
        String exportFileName = newPath + new Date().getTime() + ".csv";

        File file = null;
        MultipartFile multi = null;

        if (databaseHelper.exportCsv(userID, tableName, exportFileName)) {
//            String dockerFilePath = "/var/lib/docker/overlay2/826f6a5ebb79dabfd32dc38a34522bcf7fbcc7761bcc9117880c65e55029e854/diff";
            file = new File(exportFileName);
            FileInputStream in_file = new FileInputStream(file);
//            multi = new MockMultipartFile(file.getName(), IOUtils.toByteArray(in_file));

            /**
             * common
             */
//            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
//            try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
//                IOUtils.copy(input, os);
//                multi = new CommonsMultipartFile(fileItem);
//            }
//            System.out.println(dockerFilePath+exportFileName);
        }
        return file;
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

    @Override
    public void dropUserTable(Long userID, Long tableID) {
        databaseHelper.dropUserTable(userID, tableID);
    }

    public List<Experiment> getExperimentsByUser(long userID) {
        return databaseHelper.getExperimentsByUser(userID);
    }

    public List<String> stringArrayToList(String[] strings) {
        List<String> list = new ArrayList<>();
        for (String str :
                strings) {
            list.add(str);
        }

        return list;
    }


}
