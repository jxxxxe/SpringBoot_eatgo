package kr.co.fastcampus.eatgo.Interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users= new ArrayList<>();
        users.add(User.builder().email("tester@example.com").name("Tester").level(1l).build());

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tester")));
    }

    @Test
    public void create() throws Exception {
        String email="tester@example.com";
        String name="Tester";

        given(userService.addUser(email,name)).willReturn(User.builder().email("tester@example.com").name("Tester").level(1l).build());

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"name\":\"Tester\",\"level\":1}"))
                .andExpect(status().isCreated());


        verify(userService).addUser(email,name);
    }

    @Test
    public void update() throws Exception {
        String email="admin@example.com";
        String name="Administrator";
        Long level=100L;

        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@example.com\", \"name\":\"Administrator\",\"level\":100}"))
                .andExpect(status().isOk());

        verify(userService).updateUser(1004L, email,name,level);
    }

    @Test
    public void deactivate() throws Exception {

        mvc.perform(delete("/users/1004"))
                .andExpect(status().isOk());

        verify(userService).deactivateUser(1004L);
    }
}