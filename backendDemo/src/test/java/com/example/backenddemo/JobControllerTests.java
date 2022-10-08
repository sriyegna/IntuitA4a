package com.example.backenddemo;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.repository.JobRepository;
import com.example.backenddemo.service.JobService;
import com.google.gson.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BackendDemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class JobControllerTests {
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d'T'HH:mm");

        @Override
        public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
            return new JsonPrimitive(formatter.format(localDateTime));
        }
    }).create();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void postJob()
            throws Exception {

        JobDTO jobDTO = JobDTO.builder()
                .userId(0)
                .description("description")
                .requirements("requirements")
                .name("name")
                .contact("contact")
                .lowestBid(null)
                .numberOfBids(0)
                .expirationTime(LocalDateTime.now().plusWeeks(1))
                .winner(null)
                .closed(false)
                .build();
        String jobJSON = gson.toJson(jobDTO);


        mvc.perform(post("/job/post")
                        .contentType(MediaType.APPLICATION_JSON).content(jobJSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value(jobDTO.getDescription()))
                .andExpect(jsonPath("$.requirements").value(jobDTO.getRequirements()))
                .andExpect(jsonPath("$.name").value(jobDTO.getName().toLowerCase()))
                .andExpect(jsonPath("$.contact").value(jobDTO.getContact()));

        jobRepository.deleteAll();
    }


    @Test
    public void getJobById()
            throws Exception {

        JobDTO jobDTO = JobDTO.builder()
                .userId(0)
                .description("description")
                .requirements("requirements")
                .name("name")
                .contact("contact")
                .lowestBid(null)
                .numberOfBids(0)
                .expirationTime(LocalDateTime.now().plusWeeks(1))
                .winner(null)
                .closed(false)
                .build();
        String jobJSON = gson.toJson(jobDTO);

        Job job = jobService.createJob(jobDTO);

        mvc.perform(get("/job/").param("id", Long.toString(job.getId()))
                        .contentType(MediaType.APPLICATION_JSON).content(jobJSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value(jobDTO.getDescription()))
                .andExpect(jsonPath("$.requirements").value(jobDTO.getRequirements()))
                .andExpect(jsonPath("$.name").value(jobDTO.getName().toLowerCase()))
                .andExpect(jsonPath("$.contact").value(jobDTO.getContact()));

        jobRepository.deleteAll();
    }

    @Test
    public void perfTestCreate()
            throws Exception {

        int jobsToGenerate = 1000;

        for (int i = 0; i < jobsToGenerate; i++) {
            JobDTO jobDTO = JobDTO.builder()
                    .userId(0)
                    .description(String.format("%ddescription", i))
                    .requirements(String.format("%drequirements", i))
                    .name(String.format("%dname", i))
                    .contact(String.format("%dcontact", i))
                    .lowestBid(null)
                    .numberOfBids(0)
                    .expirationTime(LocalDateTime.now().plusWeeks(1))
                    .winner(null)
                    .closed(false)
                    .build();
            jobService.createJob(jobDTO);
        }
        assert (jobRepository.count() == jobsToGenerate);
        jobRepository.deleteAll();
    }

    @Test
    public void perfTestCreateWeekly()
            throws Exception {

        int jobsToGenerate = 250;

        for (int i = 0; i < jobsToGenerate; i++) {
            JobDTO jobDTO = JobDTO.builder()
                    .userId(0)
                    .description(String.format("%ddescription", i))
                    .requirements(String.format("%drequirements", i))
                    .name(String.format("%dname", i))
                    .contact(String.format("%dcontact", i))
                    .lowestBid(null)
                    .numberOfBids(0)
                    .expirationTime(LocalDateTime.now().plusWeeks(1))
                    .winner(null)
                    .closed(false)
                    .build();
            String jobJSON = gson.toJson(jobDTO);

            Job job = jobService.createJob(jobDTO);

            mvc.perform(get("/job/").param("id", Long.toString(job.getId()))
                            .contentType(MediaType.APPLICATION_JSON).content(jobJSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.description").value(jobDTO.getDescription()))
                    .andExpect(jsonPath("$.requirements").value(jobDTO.getRequirements()))
                    .andExpect(jsonPath("$.name").value(jobDTO.getName().toLowerCase()))
                    .andExpect(jsonPath("$.contact").value(jobDTO.getContact()));
        }
        assert (jobRepository.count() == jobsToGenerate);
        jobRepository.deleteAll();
    }
}
