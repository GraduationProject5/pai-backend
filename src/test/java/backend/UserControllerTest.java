package backend;

import backend.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testLogin() throws Exception{
        //调用接口，传入添加的用户参数
        mvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email","javalem@163.com")
                        .param("password","asdasd"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful()
                )
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testSendEmail() throws Exception{
        //调用接口，传入添加的用户参数
        mvc.perform(MockMvcRequestBuilders
                .post("/sendEmail")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email","335143116@qq.com")
                )
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();
    }




}
