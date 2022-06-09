package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {

        @MockBean
        AlbumRecommendationRepository repo;

        private AlbumRecommendation inputAlbumRecommendation;

        private AlbumRecommendation outputAlbumRecommendation;
        private String inputAlbumRecommendationString;
        private String outputAlbumRecommendationString;

        private List<AlbumRecommendation> allAlbumRecommendations;
        private String allAlbumRecommendationsString;
        private int albumRecommendationId = 18;
        private int nonExistentAlbumRecommendationId = 601;

        private int albumId = 7;

        private int userId = 2;


        private ObjectMapper mapper = new ObjectMapper();

        @Autowired
        private MockMvc mockMvc;

        @Before
        public void setUp() throws Exception {
            inputAlbumRecommendation = new AlbumRecommendation(albumId, userId, true);
            outputAlbumRecommendation = new AlbumRecommendation(albumRecommendationId, albumId, userId, true);
            Optional<AlbumRecommendation> optionalOutputAlbumRecommendation = Optional.of(outputAlbumRecommendation);
            inputAlbumRecommendationString = mapper.writeValueAsString(inputAlbumRecommendation);
            outputAlbumRecommendationString = mapper.writeValueAsString(outputAlbumRecommendation);
            allAlbumRecommendations = Arrays.asList(outputAlbumRecommendation);
            allAlbumRecommendationsString = mapper.writeValueAsString(allAlbumRecommendations);

            when(repo.save(inputAlbumRecommendation)).thenReturn(outputAlbumRecommendation);
            when(repo.findAll()).thenReturn(allAlbumRecommendations);
            when(repo.findById(albumRecommendationId)).thenReturn(optionalOutputAlbumRecommendation);
        }

        @Test
        public void shouldCreateAlbum() throws Exception {
            mockMvc.perform(post("/albumRecommendation")
                            .content(inputAlbumRecommendationString)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(outputAlbumRecommendationString));
        }

        @Test
        public void shouldGetAllAlbums() throws Exception {
            mockMvc.perform(get("/albumRecommendation"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(allAlbumRecommendationsString));
        }

        @Test
        public void shouldGetAlbumById() throws Exception {
            mockMvc.perform(get("/albumRecommendation/" + albumRecommendationId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(outputAlbumRecommendationString));
        }

        @Test
        public void shouldReport404WhenFindAlbumByInvalidId() throws Exception {
            mockMvc.perform(get("/albumRecommendation/" + nonExistentAlbumRecommendationId))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        public void shouldUpdateAlbum() throws Exception {
            mockMvc.perform(put("/albumRecommendation/" + albumRecommendationId)
                            .content(outputAlbumRecommendationString)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

        @Test
        public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
            mockMvc.perform(put("/albumRecommendation/" + nonExistentAlbumRecommendationId)
                            .content(outputAlbumRecommendationString)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void shouldDeleteAlbum() throws Exception {
            mockMvc.perform(delete("/albumRecommendation/" + albumRecommendationId))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        }

}