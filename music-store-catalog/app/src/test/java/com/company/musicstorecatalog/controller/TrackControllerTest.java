package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
    @MockBean
    TrackRepository repo;

    private Track inputTrack;

    private Track outputTrack;

    private String inputTrackString;

    private String outputTrackString;

    private List<Track> allTracks;

    private String allTracksString;

    private int albumId = 1;

    private int trackId = 8;

    private int nonExistentTrackId = 3012;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        inputTrack = new Track(albumId,"Songsville", 200);
        outputTrack = new Track(trackId, albumId, "Songsville", 200);
        Optional<Track> optionalOutputTrack = Optional.of(outputTrack);
        inputTrackString = mapper.writeValueAsString(inputTrack);
        outputTrackString = mapper.writeValueAsString(outputTrack);
        allTracks = Arrays.asList(outputTrack);
        allTracksString = mapper.writeValueAsString(allTracks);

        when(repo.save(inputTrack)).thenReturn(outputTrack);
        when(repo.findAll()).thenReturn(allTracks);
        when(repo.findById(trackId)).thenReturn(optionalOutputTrack);

    }
    @Test
    public void shouldCreateTrack() throws Exception {
        mockMvc.perform(post("/track")
                        .content(inputTrackString)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTrackString));
    }

    @Test
    public void shouldGetAllTracks() throws Exception {
        mockMvc.perform(get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allTracksString));
    }

    @Test
    public void shouldGetTrackById() throws Exception {
        mockMvc.perform(get("/track/" + trackId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTrackString));
    }

    @Test
    public void shouldReport404WhenFindTrackByInvalidId() throws Exception {
        mockMvc.perform(get("/track/" + nonExistentTrackId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldUpdateTrack() throws Exception {
        mockMvc.perform(put("/track/" + trackId)
                        .content(outputTrackString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/track/" + nonExistentTrackId)
                        .content(outputTrackString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteTrack() throws Exception {
        mockMvc.perform(delete("/track/" + trackId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }



}