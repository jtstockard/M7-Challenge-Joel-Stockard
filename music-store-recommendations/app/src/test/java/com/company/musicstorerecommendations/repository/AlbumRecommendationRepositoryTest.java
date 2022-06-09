package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRecommendationRepositoryTest {
    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;

    AlbumRecommendation albumRecommendation1;
    AlbumRecommendation albumRecommendation2;
    private List<AlbumRecommendation> expectedAlbumRecommendationList = new ArrayList<>();

    @Before
    public void setUp() {
        albumRecommendationRepository.deleteAll();

        albumRecommendation1 = albumRecommendationRepository.save(new AlbumRecommendation(1,1,1,true));
        albumRecommendation2 = albumRecommendationRepository.save(new AlbumRecommendation(2,1,2,true));

        expectedAlbumRecommendationList.clear();
    }

    @Test
    public void addGetDeleteAlbumRecommendation() {
        Optional<AlbumRecommendation> album = albumRecommendationRepository.findById(albumRecommendation1.getId());

        assertEquals(albumRecommendation1, album.get());

        albumRecommendationRepository.deleteById(albumRecommendation1.getId());
        album = albumRecommendationRepository.findById(albumRecommendation1.getId());

        assertFalse(album.isPresent());
    }

    @Test
    public void updateAlbumRecommendation() {
        albumRecommendation1.setLiked(false);
        albumRecommendationRepository.save(albumRecommendation1);
        Optional<AlbumRecommendation> albumRecommendation = albumRecommendationRepository.findById(albumRecommendation1.getId());

        assertEquals(albumRecommendation.get(), albumRecommendation1);
    }

    @Test
    public void getAllAlbumRecommendations() {
        expectedAlbumRecommendationList.add(albumRecommendation1);
        expectedAlbumRecommendationList.add(albumRecommendation2);

        List<AlbumRecommendation> actualAlbumList = albumRecommendationRepository.findAll();

        assertEquals(actualAlbumList.size(), 2);
        assertEquals(expectedAlbumRecommendationList, actualAlbumList);
    }


}