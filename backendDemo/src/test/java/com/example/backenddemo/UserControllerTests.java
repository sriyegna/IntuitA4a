package com.example.backenddemo;

import com.example.backenddemo.dto.UserDTO;
import com.example.backenddemo.repository.UserRepository;
import com.example.backenddemo.service.UserService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BackendDemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void register()
            throws Exception {

        UserDTO userDTO = UserDTO.builder().name("registerTest").password("registerPassword").actorType("poster").build();
        String userJSON = new Gson().toJson(userDTO);

        mvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON).content(userJSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(userDTO.getName().toLowerCase()))
                .andExpect(jsonPath("$.password").value(userDTO.getPassword()))
                .andExpect(jsonPath("$.actorType").value(userDTO.getActorType()));

        userRepository.deleteAll();
    }

    @Test
    public void login()
            throws Exception {

        UserDTO userDTO = UserDTO.builder().name("loginTest").password("loginPassword").actorType("poster").build();
        String userJSON = new Gson().toJson(userDTO);

        // Setup user
        userService.createUser(userDTO);

        mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON).content(userJSON))
                .andExpect(status().isAccepted())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(userDTO.getName().toLowerCase()))
                .andExpect(jsonPath("$.password").value(userDTO.getPassword()))
                .andExpect(jsonPath("$.actorType").value(userDTO.getActorType()));

        userRepository.deleteAll();
    }

    @Test
    public void perfTestCreate()
            throws Exception {

        int usersToGenerate = 100000;

        for (int i = 0; i < usersToGenerate; i++) {
            UserDTO userDTO = UserDTO
                    .builder()
                    .name(String.format("%dperfCreate", i))
                    .password("123")
                    .actorType("poster")
                    .build();
            userService.createUser(userDTO);
        }
        assert (userRepository.count() == usersToGenerate);
        userRepository.deleteAll();
    }

    @Test
    public void perfTestDailyCreate()
            throws Exception {

        int usersToGenerate = 2000;

        for (int i = 0; i < usersToGenerate; i++) {
            UserDTO userDTO = UserDTO
                    .builder()
                    .name(String.format("%dperfCreate", i))
                    .password("123")
                    .actorType("bidder")
                    .build();
            String userJSON = new Gson().toJson(userDTO);

            mvc.perform(post("/user/register")
                            .contentType(MediaType.APPLICATION_JSON).content(userJSON))
                    .andExpect(status().isCreated())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value(userDTO.getName().toLowerCase()))
                    .andExpect(jsonPath("$.password").value(userDTO.getPassword()))
                    .andExpect(jsonPath("$.actorType").value(userDTO.getActorType()));
        }

        assert (userRepository.count() == usersToGenerate);
        userRepository.deleteAll();
    }


}
