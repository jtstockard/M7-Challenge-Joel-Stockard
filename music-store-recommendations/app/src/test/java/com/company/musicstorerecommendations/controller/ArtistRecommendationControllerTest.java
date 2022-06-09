package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {

    @MockBean
    ArtistRecommendationRepository repo;

    private ArtistRecommendation inputArtistRecommendation;

    private ArtistRecommendation outputArtistRecommendation;
    private String inputArtistRecommendationString;
    private String outputArtistRecommendationString;

    private List<ArtistRecommendation> allArtistRecommendations;
    private String allArtistRecommendationsString;
    private int artistRecommendationId = 18;
    private int nonExistentArtistRecommendationId = 601;

    private int artistId = 7;

    private int userId = 2;


    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        inputArtistRecommendation = new ArtistRecommendation(artistId, userId, true);
        outputArtistRecommendation = new ArtistRecommendation(artistRecommendationId, artistId, userId, true);
        Optional<ArtistRecommendation> optionalOutputArtistRecommendation = Optional.of(outputArtistRecommendation);
        inputArtistRecommendationString = mapper.writeValueAsString(inputArtistRecommendation);
        outputArtistRecommendationString = mapper.writeValueAsString(outputArtistRecommendation);
        allArtistRecommendations = Arrays.asList(outputArtistRecommendation);
        allArtistRecommendationsString = mapper.writeValueAsString(allArtistRecommendations);

        when(repo.save(inputArtistRecommendation)).thenReturn(outputArtistRecommendation);
        when(repo.findAll()).thenReturn(allArtistRecommendations);
        when(repo.findById(artistRecommendationId)).thenReturn(optionalOutputArtistRecommendation);
    }

    @Test
    public void shouldCreateArtist() throws Exception {
        mockMvc.perform(post("/artistRecommendation")
                        .content(inputArtistRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputArtistRecommendationString));
    }

    @Test
    public void shouldGetAllArtists() throws Exception {
        mockMvc.perform(get("/artistRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allArtistRecommendationsString));
    }

    @Test
    public void shouldGetArtistById() throws Exception {
        mockMvc.perform(get("/artistRecommendation/" + artistRecommendationId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputArtistRecommendationString));
    }

    @Test
    public void shouldReport404WhenFindArtistByInvalidId() throws Exception {
        mockMvc.perform(get("/artistRecommendation/" + nonExistentArtistRecommendationId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateArtist() throws Exception {
        mockMvc.perform(put("/artistRecommendation/" + artistRecommendationId)
                        .content(outputArtistRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/artistRecommendation/" + nonExistentArtistRecommendationId)
                        .content(outputArtistRecommendationString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteArtist() throws Exception {
        mockMvc.perform(delete("/artistRecommendation/" + artistRecommendationId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}