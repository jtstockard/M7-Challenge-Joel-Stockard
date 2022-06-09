package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {

    @MockBean
    TrackRecommendationRepository repo;

    private TrackRecommendation inputTrackRecommendation;

    private TrackRecommendation outputTrackRecommendation;
    private String inputTrackRecommendationString;
    private String outputTrackRecommendationString;

    private List<TrackRecommendation> allTrackRecommendations;
    private String allTrackRecommendationsString;
    private int trackRecommendationId = 18;
    private int nonExistentTrackRecommendationId = 601;

    private int trackId = 7;

    private int userId = 2;


    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        inputTrackRecommendation = new TrackRecommendation(trackId, userId, true);
        outputTrackRecommendation = new TrackRecommendation(trackRecommendationId, trackId, userId, true);
        Optional<TrackRecommendation> optionalOutputTrackRecommendation = Optional.of(outputTrackRecommendation);
        inputTrackRecommendationString = mapper.writeValueAsString(inputTrackRecommendation);
        outputTrackRecommendationString = mapper.writeValueAsString(outputTrackRecommendation);
        allTrackRecommendations = Arrays.asList(outputTrackRecommendation);
        allTrackRecommendationsString = mapper.writeValueAsString(allTrackRecommendations);

        when(repo.save(inputTrackRecommendation)).thenReturn(outputTrackRecommendation);
        when(repo.findAll()).thenReturn(allTrackRecommendations);
        when(repo.findById(trackRecommendationId)).thenReturn(optionalOutputTrackRecommendation);
    }

    @Test
    public void shouldCreateTrack() throws Exception {
        mockMvc.perform(post("/trackRecommendation")
                        .content(inputTrackRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackRecommendationString));
    }

    @Test
    public void shouldGetAllTracks() throws Exception {
        mockMvc.perform(get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allTrackRecommendationsString));
    }

    @Test
    public void shouldGetTrackById() throws Exception {
        mockMvc.perform(get("/trackRecommendation/" + trackRecommendationId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTrackRecommendationString));
    }

    @Test
    public void shouldReport404WhenFindTrackByInvalidId() throws Exception {
        mockMvc.perform(get("/trackRecommendation/" + nonExistentTrackRecommendationId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateTrack() throws Exception {
        mockMvc.perform(put("/trackRecommendation/" + trackRecommendationId)
                        .content(outputTrackRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/trackRecommendation/" + nonExistentTrackRecommendationId)
                        .content(outputTrackRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteTrack() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/" + trackRecommendationId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}