package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
    @MockBean
    AlbumRepository repo;

    private Album inputAlbum;
    private Album outputAlbum;
    private String inputAlbumString;
    private String outputAlbumString;

    private List<Album> allAlbums;
    private String allAlbumsString;
    private int albumId = 18;
    private int nonExistentAlbumId = 601;

    private int artistId = 25;

    private int labelId = 9;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        inputAlbum = new Album("My Album", artistId, LocalDate.of(2022, 05, 18), labelId, new BigDecimal("10.95"));
        outputAlbum = new Album(albumId, "My Album", artistId, LocalDate.of(2022, 05, 18), labelId, new BigDecimal("10.95"));
        Optional<Album> optionalOutputAlbum = Optional.of(outputAlbum);
        inputAlbumString = mapper.writeValueAsString(inputAlbum);
        outputAlbumString = mapper.writeValueAsString(outputAlbum);
        allAlbums = Arrays.asList(outputAlbum);
        allAlbumsString = mapper.writeValueAsString(allAlbums);

        when(repo.save(inputAlbum)).thenReturn(outputAlbum);
        when(repo.findAll()).thenReturn(allAlbums);
        when(repo.findById(albumId)).thenReturn(optionalOutputAlbum);
    }

    @Test
    public void shouldCreateAlbum() throws Exception {
        mockMvc.perform(post("/album")
                        .content(inputAlbumString)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputAlbumString));
    }

    @Test
    public void shouldGetAllAlbums() throws Exception {
        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allAlbumsString));
    }

    @Test
    public void shouldGetAlbumById() throws Exception {
        mockMvc.perform(get("/album/" + albumId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputAlbumString));
    }

    @Test
    public void shouldReport404WhenFindAlbumByInvalidId() throws Exception {
        mockMvc.perform(get("/album/" + nonExistentAlbumId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateAlbum() throws Exception {
        mockMvc.perform(put("/album/" + albumId)
                        .content(outputAlbumString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/album/" + nonExistentAlbumId)
                        .content(outputAlbumString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteArtist() throws Exception {
        mockMvc.perform(delete("/album/" + albumId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }




}