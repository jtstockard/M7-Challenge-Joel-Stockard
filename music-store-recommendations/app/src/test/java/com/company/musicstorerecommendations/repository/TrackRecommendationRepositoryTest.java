package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import org.junit.Before;
import org.junit.Test;
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
public class TrackRecommendationRepositoryTest {
    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;

    TrackRecommendation trackRecommendation1;
    TrackRecommendation trackRecommendation2;
    private List<TrackRecommendation> expectedTrackRecommendationList = new ArrayList<>();

    @Before
    public void setUp() {
        trackRecommendationRepository.deleteAll();

        trackRecommendation1 = trackRecommendationRepository.save(new TrackRecommendation(1,1,1,true));
        trackRecommendation2 = trackRecommendationRepository.save(new TrackRecommendation(2,1,2,true));

        expectedTrackRecommendationList.clear();
    }

    @Test
    public void addGetDeleteTrackRecommendation() {
        Optional<TrackRecommendation> track = trackRecommendationRepository.findById(trackRecommendation1.getId());

        assertEquals(trackRecommendation1, track.get());

        trackRecommendationRepository.deleteById(trackRecommendation1.getId());
        track = trackRecommendationRepository.findById(trackRecommendation1.getId());

        assertFalse(track.isPresent());
    }

    @Test
    public void updateTrackRecommendation() {
        trackRecommendation1.setLiked(false);
        trackRecommendationRepository.save(trackRecommendation1);
        Optional<TrackRecommendation> trackRecommendation = trackRecommendationRepository.findById(trackRecommendation1.getId());

        assertEquals(trackRecommendation.get(), trackRecommendation1);
    }

    @Test
    public void getAllTrackRecommendations() {
        expectedTrackRecommendationList.add(trackRecommendation1);
        expectedTrackRecommendationList.add(trackRecommendation2);

        List<TrackRecommendation> actualTrackList = trackRecommendationRepository.findAll();

        assertEquals(actualTrackList.size(), 2);
        assertEquals(expectedTrackRecommendationList, actualTrackList);
    }


}