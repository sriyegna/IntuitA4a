package com.example.backenddemo;

import com.example.backenddemo.domain.Job;
import com.example.backenddemo.dto.BidDTO;
import com.example.backenddemo.dto.JobDTO;
import com.example.backenddemo.repository.BidRepository;
import com.example.backenddemo.repository.JobRepository;
import com.example.backenddemo.service.JobService;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BackendDemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")

public class BidControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private BidRepository bidRepository;

    @Test
    public void postBid()
            throws Exception {

        //Setup job
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

        Job job = jobService.createJob(jobDTO);

        // Test bid
        BidDTO bidDTO = BidDTO.builder()
                .jobId(job.getId())
                .userId(0)
                .bidAmount(1000)
                .build();

        String bidJSON = new Gson().toJson(bidDTO);

        mvc.perform(post("/bid/post")
                        .contentType(MediaType.APPLICATION_JSON).content(bidJSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jobId").value(bidDTO.getJobId()))
                .andExpect(jsonPath("$.userId").value(bidDTO.getUserId()))
                .andExpect(jsonPath("$.bidAmount").value(bidDTO.getBidAmount()));

        bidRepository.deleteAll();
        jobRepository.deleteAll();
    }

    @Test
    public void perfTestCreate()
            throws Exception {

        int jobsToGenerate = 1000;
        int bidsToGenerate = 20;

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
            Job job = jobService.createJob(jobDTO);


            for (int j = 0; j < bidsToGenerate; j++) {
                BidDTO bidDTO = BidDTO.builder()
                        .jobId(job.getId())
                        .userId(0)
                        .bidAmount(1000 - i)
                        .build();

                String bidJSON = new Gson().toJson(bidDTO);

                mvc.perform(post("/bid/post")
                                .contentType(MediaType.APPLICATION_JSON).content(bidJSON))
                        .andExpect(status().isCreated())
                        .andExpect(content()
                                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.jobId").value(bidDTO.getJobId()))
                        .andExpect(jsonPath("$.userId").value(bidDTO.getUserId()))
                        .andExpect(jsonPath("$.bidAmount").value(bidDTO.getBidAmount()));
            }
        }
        assert (jobRepository.count() == jobsToGenerate);
        assert (bidRepository.count() == (jobsToGenerate * bidsToGenerate));


        jobRepository.deleteAll();
        bidRepository.deleteAll();
    }
}
