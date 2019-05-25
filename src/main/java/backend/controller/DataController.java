package backend.controller;

import backend.feign.feignservice.PicClassificationExec;
import backend.model.po.TablePO;
import backend.model.vo.TableVO;
import backend.service.DataService;
import backend.service.UserService;
import backend.util.config.DatabaseProperties;
import backend.util.file.CsvImportUtils;
import backend.util.json.HttpResponseHelper;
import backend.util.json.JSONHelper;
import backend.model.vo.ColumnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lienming on 2019/1/17.
 */

@RestController
@RequestMapping(value = "/api/data")
public class DataController {

    @Autowired
    DataService dataService;
    @Autowired
    UserService userService;
    @Autowired
    PicClassificationExec picClassificationExec;

//    private Map newResultMap = HttpResponseHelper.newResultMap();
//    newResultMap.clear();

    //用户建表 （通过表格填写列属性）
    @PostMapping(value = "/createTableByColumn")
    public Map<String, Object> createTableByColumn(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("description") String description,
            @RequestBody Map<String, ColumnVO> map) {
        List<ColumnVO> columnVOList = JSONHelper.toColumnVOList(map);
        return dataService.createTableByVO
                (Long.parseLong(userID), tableName, columnVOList, description);

    }

    //用户建表 （通过MySql脚本）
    @PostMapping(value = "/createTableByScript")
    public Map<String, Object> createTableByScript(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableName") String tableName,
            @RequestParam("sql") String sqlScript) {
        return dataService.createTableByScript
                (Long.parseLong(userID), tableName, sqlScript);
    }

    /**
     * 将用户上传的 csv数据表 插入到自建的数据库
     *
     * @param userID
     * @param tableName
     * @param csvFile
     * @return
     */
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
                httpResult.put("result", "数据上传失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpResult;

    }

    //用户导入数据到自建表中,弃用(改用csv)
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

    /**
     * 删除用户的数据库表和表相关关系
     */
    @GetMapping(value = "/dropTable")
    public void dropTable(
            @SessionAttribute("userID") String userID,
            @RequestParam("tableID") Long tableID
    ) {
        dataService.dropUserTable(Long.parseLong(userID), tableID);
    }

    /**
     * 创建训练集文件夹
     *
     * @param userID
     * @param dirParams
     * @return
     */
    @PostMapping(value = "/createTrainDir")
    public Map<String, ?> createTrainDir(
            @SessionAttribute("userID") String userID,
//            Param("expName") String expName,
//            //文件夹名称，比如bird/cat
//            Param("dirName") String dirName,
            @RequestBody Map<String, String> dirParams
    ) {
        String userName = userService.getUserNameByUserID(Long.parseLong(userID));
        return picClassificationExec.createTrainDir(userName, dirParams);
    }

    /**
     * 上传图片
     *
     * @param request
     * @param userID
     * @return
     */
    @PostMapping(value = "/uploadPics")
    @ResponseBody
    public Map<String, ?> uploadPics(HttpServletRequest request,
                                     @SessionAttribute("userID") String userID
    ) {

        List<MultipartFile> pics = ((MultipartHttpServletRequest) request)
                .getFiles("pic");

        String expName = request.getParameter("expName");
        String dirName = request.getParameter("dirName");

        String userName = userService.getUserNameByUserID(Long.parseLong(userID));

        return picClassificationExec.uploadPics(userName, expName, dirName, pics);
    }

}
