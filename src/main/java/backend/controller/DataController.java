package backend.controller;

import backend.entity.Experiment;
import backend.entity.TablePO;
import backend.service.DataService;
import backend.vo.ColumnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @ResponseBody
    public String createTableByColumn(Model model,
                        @SessionAttribute("userID")String userID,
                        @RequestParam("tableName") String tableName,
                        @RequestBody Map<String,ColumnVO> map,
                        @RequestParam("description")String description) {

        List<ColumnVO> columnVOList = new ArrayList<>();
        for(String key : map.keySet()){
            ColumnVO cvo = map.get(key);
            columnVOList.add(cvo);
        }

        long tableID = dataService.createTableByVO
                (Long.parseLong(userID),tableName,columnVOList,description);

        if(tableID<0)
        {
            model.addAttribute("result",false);
        }else {
            model.addAttribute("result",true);
            model.addAttribute("tableID",tableID);
        }

        return "data/..1";
    }

    //用户建表 （通过MySql脚本）
    @PostMapping(value = "/createTableByScript")
    @ResponseBody
    public String createTableByScript(Model model,
                                      @SessionAttribute("userID")String userID,
                                      @RequestParam("tableName") String tableName,
                                      @RequestBody Map<String,String> map ) {

        String sqlScript = map.get("sql") ;

        long tableID = dataService.createTableByScript
                (Long.parseLong(userID),tableName,sqlScript);

        if(tableID<0)
        {
            model.addAttribute("result",false);
        }else {
            model.addAttribute("result",true);
            model.addAttribute("tableID",tableID);
        }

        return "data/..2";
    }

    //用户导入数据到自建表中
    @PostMapping(value = "/importData")
    @ResponseBody
    public String importData(@SessionAttribute("userID")String userID,
                             @RequestParam("tableName") String tableName,
                             @RequestBody String[] file) {
        dataService.insertData(Long.parseLong(userID),tableName,file,";");

        return "data/..3";
    }

    //查看用户自建表列表
    @PostMapping(value = "/allTable")
    @ResponseBody
    public String allTable(Model model,
                            @SessionAttribute("userID")String userID) {
        List<TablePO> list = dataService.getDatabasesByUser(Long.parseLong(userID));
        model.addAttribute("result",true);
        model.addAttribute("tables",list);
        return "data/..4";
    }

    //查看某张表的属性（有哪些列）
    @PostMapping(value = "/tableDetail")
    @ResponseBody
    public String tableDetail(Model model,
                           @SessionAttribute("userID")String userID ,
                           @RequestParam("tableName") String tableName) {
        List list = dataService.getData(Long.parseLong(userID),tableName);
        model.addAttribute("result",true);
        model.addAttribute("list",list);
        return "data/..5";
    }

    //创建实验
    @PostMapping(value = "/createExperiment")
    @ResponseBody
    public String createExperiment(Model model,
                              @SessionAttribute("userID")String userID ,
                              @RequestParam("experimentName") String experimentName,
                              @RequestParam("description") String description) {
        long experimentID = dataService.
                createExperiment(Long.parseLong(userID),experimentName,description);
        if(experimentID>0) {
            model.addAttribute("result", true);
            model.addAttribute("experimentID", experimentID);
        } else {
            model.addAttribute("result", false);
        }

        return "data/..6";
    }

    //查看实验
    @PostMapping(value = "/allExperiment")
    @ResponseBody
    public String allExperiment(Model model,
                           @SessionAttribute("userID")String userID) {
        List<Experiment> list = dataService.getExperimentsByUser(Long.parseLong(userID));
        model.addAttribute("result",true);
        model.addAttribute("experiments",list);
        return "data/..7";
    }


}
