package backend.controller;

import backend.model.po.Experiment;
import backend.model.po.TablePO;
import backend.model.vo.TableVO;
import backend.service.DataService;
import backend.util.file.CsvImportUtils;
import backend.util.json.HttpResponseHelper;
import backend.util.json.JSONHelper;
import backend.model.vo.ColumnVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lienming on 2019/1/17.
 */

@RestController
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    private DataService dataService;

//    private Map newResultMap = HttpResponseHelper.newResultMap();
//    newResultMap.clear();

    //用户建表 （通过表格填写列属性）
    @PostMapping(value = "/createTableByColumn")
    public Map<String, Object> createTableByColumn(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("description") String description,
            @RequestBody Map<String, ColumnVO> map) {

        Map<String, Object> result = HttpResponseHelper.newResultMap();

//        Map<String, Object> map = JSONHelper.convertToMap(json);
//        Map<String,ColumnVO> voMap = (Map<String,ColumnVO>)map;

        List<ColumnVO> columnVOList = JSONHelper.toColumnVOList(map);
//        for (String key : map.keySet()) {
//            ColumnVO cvo = map.get(key);
//            columnVOList.add(cvo);
//        }

        long tableID = dataService.createTableByVO
                (Long.parseLong(userID), tableName, columnVOList, description);

        if (tableID < 0) {
            result.put("result", false);
        } else {
            result.put("result", true);
            result.put("tableID", tableID);
        }

        return result;
    }

    //用户建表 （通过MySql脚本）
    @PostMapping(value = "/createTableByScript")
    public Map<String, Object> createTableByScript(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("sql") String sqlScript) {

        Map<String, Object> result = HttpResponseHelper.newResultMap();

        long tableID = dataService.createTableByScript
                (Long.parseLong(userID), tableName, sqlScript);

        if (tableID < 0) {
            result.put("result", false);
        } else {
            result.put("result", true);
            result.put("tableID", tableID);
        }

        return result;
    }

    // 将用户上传的 csv数据表 插入到自建的数据库
    @PostMapping(value = "/insertCsv")
    public Map<String, Object> insertCsv(@SessionAttribute("userID") String userID,
                                         @RequestParam("tableName") String tableName,
                                         @RequestParam("file") MultipartFile csvFile) {

        Map<String, Object> httpResult = HttpResponseHelper.newResultMap();

        //文件为空
        if (csvFile == null) {
            httpResult.put("result", "文件为空！");
            return httpResult;
        }

        //获取文件名
        String fileName = csvFile.getOriginalFilename();

        //判断文件格式
        if (!CsvImportUtils.isCsv(fileName)) {
            httpResult.clear();
            httpResult.put("result", "请上传csv格式文件！");
            return httpResult;
        }

//        String path = request.getSession().getServletContext().getRealPath("upload/data");

        try {
            Map<Boolean, Integer> insertRes = dataService.insertCsv(userID, fileName, tableName, csvFile);
            if (insertRes.get(true) != null) {
                httpResult.put("result", "成功插入" + insertRes.get(true) + "条数据！");
            } else {
                httpResult.put("result", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpResult;

    }

    //用户导入数据到自建表中
    @PostMapping(value = "/importData")
    public void importData(@SessionAttribute("userID") String userID,
                           @RequestParam("tableName") String tableName,
                           @RequestParam(value = "splitChar", defaultValue = ";") String splitChar,
                           @RequestBody Map<String, String[]> map) {
//                           @RequestParam("file") String[] file) {
        //默认分隔符为 ;
        String[] file = map.get("file");
        dataService.insertData(Long.parseLong(userID), tableName, file, splitChar);

    }

    //查看用户自建表列表
    @GetMapping(value = "/allTable")
    public Map<String, Object> allTable(@SessionAttribute("userID") String userID) {
        List<TablePO> list = dataService.getDatabasesByUser(Long.parseLong(userID));

        Map<String, Object> result = HttpResponseHelper.newResultMap();

        result.put("tables", list);
        return result;
    }

    //查看某张表的属性（有哪些列）
    @GetMapping(value = "/tableDetail")
    public TableVO tableDetail(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName) {

        TableVO tableVO = dataService.getTableAttr(Long.parseLong(userID), tableName);

        return tableVO;
    }

    //获取表的所有行
    @GetMapping(value = "/tableData")
    public Map<String, Object> tableData(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName) {
        Map<String, Object> result = HttpResponseHelper.newResultMap();

        List list = dataService.getData(Long.parseLong(userID), tableName);

        result.put("list", list);
        return result;
    }

}
