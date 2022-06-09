package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {

    @MockBean
    LabelRecommendationRepository repo;

    private LabelRecommendation inputLabelRecommendation;

    private LabelRecommendation outputLabelRecommendation;
    private String inputLabelRecommendationString;
    private String outputLabelRecommendationString;

    private List<LabelRecommendation> allLabelRecommendations;
    private String allLabelRecommendationsString;
    private int labelRecommendationId = 18;
    private int nonExistentLabelRecommendationId = 601;

    private int labelId = 7;

    private int userId = 2;


    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        inputLabelRecommendation = new LabelRecommendation(labelId, userId, true);
        outputLabelRecommendation = new LabelRecommendation(labelRecommendationId, labelId, userId, true);
        Optional<LabelRecommendation> optionalOutputLabelRecommendation = Optional.of(outputLabelRecommendation);
        inputLabelRecommendationString = mapper.writeValueAsString(inputLabelRecommendation);
        outputLabelRecommendationString = mapper.writeValueAsString(outputLabelRecommendation);
        allLabelRecommendations = Arrays.asList(outputLabelRecommendation);
        allLabelRecommendationsString = mapper.writeValueAsString(allLabelRecommendations);

        when(repo.save(inputLabelRecommendation)).thenReturn(outputLabelRecommendation);
        when(repo.findAll()).thenReturn(allLabelRecommendations);
        when(repo.findById(labelRecommendationId)).thenReturn(optionalOutputLabelRecommendation);
    }

    @Test
    public void shouldCreateLabel() throws Exception {
        mockMvc.perform(post("/labelRecommendation")
                        .content(inputLabelRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputLabelRecommendationString));
    }

    @Test
    public void shouldGetAllLabels() throws Exception {
        mockMvc.perform(get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allLabelRecommendationsString));
    }

    @Test
    public void shouldGetLabelById() throws Exception {
        mockMvc.perform(get("/labelRecommendation/" + labelRecommendationId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputLabelRecommendationString));
    }

    @Test
    public void shouldReport404WhenFindLabelByInvalidId() throws Exception {
        mockMvc.perform(get("/labelRecommendation/" + nonExistentLabelRecommendationId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateLabel() throws Exception {
        mockMvc.perform(put("/labelRecommendation/" + labelRecommendationId)
                        .content(outputLabelRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/labelRecommendation/" + nonExistentLabelRecommendationId)
                        .content(outputLabelRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteLabel() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/" + labelRecommendationId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}