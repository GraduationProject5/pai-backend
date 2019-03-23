package backend.controller;

import backend.entity.Experiment;
import backend.entity.TablePO;
import backend.service.DataService;
import backend.util.json.HttpResponseHelper;
import backend.util.json.JSONHelper;
import backend.vo.ColumnVO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */

@Controller
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    private DataService dataService;

    //用户建表 （通过表格填写列属性）
    @PostMapping(value = "/createTableByColumn")
    public Map<String,Object> createTableByColumn(
            @SessionAttribute("userID")String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("description") String description,
            @RequestBody JSONObject json) {

        Map<String, Object> result = HttpResponseHelper.newResultMap();

        Map<String, Object> map = JSONHelper.convertToMap(json);
        Map<String,ColumnVO> voMap = ( Map<String,ColumnVO>)map.get("voMap");

        List<ColumnVO> columnVOList = new ArrayList<>();
        for(String key : voMap.keySet()){
            ColumnVO cvo = voMap.get(key);
            columnVOList.add(cvo);
        }

        long tableID = dataService.createTableByVO
                (Long.parseLong(userID),tableName,columnVOList,description);

        if(tableID<0)
        {
            result.put("result",false);
        }else {
            result.put("result",true);
            result.put("tableID",tableID);
        }

        return result;
    }

    //用户建表 （通过MySql脚本）
    @PostMapping(value = "/createTableByScript")
    public Map<String,Object> createTableByScript(
                                      @SessionAttribute("userID")String userID,
                                      @RequestParam("tableName") String tableName,
                                      @RequestParam("sql") String sqlScript) {

        Map<String,Object> result = HttpResponseHelper.newResultMap();

        long tableID = dataService.createTableByScript
                (Long.parseLong(userID),tableName,sqlScript);

        if(tableID<0)
        {
            result.put("result",false);
        }else {
            result.put("result",true);
            result.put("tableID",tableID);
        }

        return result;
    }

    //用户导入数据到自建表中
    @PostMapping(value = "/importData")
    public void importData(@SessionAttribute("userID")String userID,
                             @RequestParam("tableName") String tableName,
                             @RequestParam(value = "splitChar",defaultValue = ";") String splitChar,
                             @RequestParam("file") String[] file) {
        dataService.insertData(Long.parseLong(userID),tableName,file,splitChar);

    }

    //查看用户自建表列表
    @GetMapping(value = "/allTable")
    public Map<String,Object> allTable(@SessionAttribute("userID")String userID) {
        List<TablePO> list = dataService.getDatabasesByUser(Long.parseLong(userID));

        Map<String,Object> result = HttpResponseHelper.newResultMap();

        result.put("tables",list);
        return result;
    }

    //查看某张表的属性（有哪些列）
    @GetMapping(value = "/tableDetail")
    public Map<String,Object> tableDetail(
                    @SessionAttribute("userID")String userID ,
                           @RequestParam("tableName") String tableName) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();

        List list = dataService.getData(Long.parseLong(userID),tableName);

        result.put("list",list);
        return result;
    }

    //创建实验
    @GetMapping(value = "/createExperiment")
    public Map<String,Object> createExperiment(
                                @SessionAttribute("userID")String userID ,
                                @RequestParam("experimentName") String experimentName,
                                @RequestParam("description") String description) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();

        long experimentID = dataService.
                createExperiment(Long.parseLong(userID),experimentName,description);
        if(experimentID>0) {
            result.put("result",true);
            result.put("experimentID", experimentID);
        } else {
            result.put("result", false);
        }

        return result;
    }

    //查看实验
    @GetMapping(value = "/allExperiment")
    public Map<String,Object> allExperiment(Model model,
                           @SessionAttribute("userID")String userID) {
        Map<String,Object> result = HttpResponseHelper.newResultMap();

        List<Experiment> list = dataService.getExperimentsByUser(Long.parseLong(userID));

        result.put("experiments",list);
        return result;
    }


}
