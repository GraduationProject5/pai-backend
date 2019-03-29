package backend;


import backend.enumclass.ColumnType;
import backend.model.vo.ColumnVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testCreateTableByColumn() throws Exception {
        ColumnVO cvo1 = new ColumnVO();
        cvo1.columnName = "c1";
        cvo1.columnType = ColumnType.INT;
        cvo1.description = "NOT NULL PRIMARY KEY";
        ColumnVO cvo2 = new ColumnVO();
        cvo2.columnName = "c2";
        cvo2.columnType = ColumnType.STRING;
        cvo2.description = "NOT NULL";

        Map<String, ColumnVO> map = new HashMap();
        map.put("cvo1", cvo1);
        map.put("cvo2", cvo2);
        mvc.perform(MockMvcRequestBuilders
                .post("/data/createTableByColumn")
                .sessionAttr("userID", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(map))
                .param("tableName", "test2ByColumn")
                .param("description", "test2CreateTableByColumn"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful()
                )
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testCreateTableByScript() throws Exception {

//        String sql =
//                "create table test2ByScript(column_1 int not null, column_2 int null, column_3 int null, constraint user1_test_pk primary key (column_1));";

        String sql = "create table texts\n" +
                "(\n" +
                "\tid int auto_increment,\n" +
                "\tcontent MEDIUMTEXT not null,\n" +
                "\tconstraint texts_pk\n" +
                "\t\tprimary key (id)\n" +
                ");";
//        Map<String,String> map = new HashMap();
//        map.put("sql",sql);

        mvc.perform(MockMvcRequestBuilders
                        .post("/data/createTableByScript")
                        .sessionAttr("userID", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JSONObject.toJSONString(map))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("tableName", "texts")
                        .param("sql", sql)
        )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testInsertCsv() throws Exception {

        File file = new File("/Users/Chen/Downloads/text_csv.csv");

        mvc.perform(MockMvcRequestBuilders
                        .post("data/insertCsv")
                        .sessionAttr("userID", 1)
                        .param("tableName", "texts")
//                .buildRequest(file)
        )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testImportData_1() throws Exception {

        String[] file = {
                "1;2;3",
                "4;5;6",
                "7;8;9"
        };

        Map<String, String[]> map = new HashMap();

        map.put("file", file);

        mvc.perform(MockMvcRequestBuilders
                .post("/data/importData")
                .sessionAttr("userID", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(map))
                .param("tableName", "test2ByScript")
        )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

    }

    @Test
    public void testImportData_2() throws Exception {
        String[] file = {
                "aaa;111",
                "bbb;222",
                "ccc;333"
        };

        Map<String, String[]> map = new HashMap();

        map.put("file", file);

        mvc.perform(MockMvcRequestBuilders
                .post("/data/importData")
                .sessionAttr("userID", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(map))
                .param("tableName", "test2ByColumn")
        )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

    }


    @Test
    public void testAllTable() throws Exception {
//        map.put("file",file);

        mvc.perform(MockMvcRequestBuilders
                .get("/data/allTable")
                .sessionAttr("userID", 1)
        )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();

    }

}
