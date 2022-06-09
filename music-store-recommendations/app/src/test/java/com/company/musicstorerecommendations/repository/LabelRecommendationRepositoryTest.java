package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.LabelRecommendation;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LabelRecommendationRepositoryTest {

    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;

    LabelRecommendation labelRecommendation1;
    LabelRecommendation labelRecommendation2;
    private List<LabelRecommendation> expectedLabelRecommendationList = new ArrayList<>();


    @Before
    public void setUp() {
        labelRecommendationRepository.deleteAll();

        labelRecommendation1 = labelRecommendationRepository.save(new LabelRecommendation(1,1,1,true));
        labelRecommendation2 = labelRecommendationRepository.save(new LabelRecommendation(2,1,2,true));

        expectedLabelRecommendationList.clear();
    }


    @Test
    public void addGetDeleteLabelRecommendation() {
        Optional<LabelRecommendation> actualLabelRecommendation = labelRecommendationRepository.findById(labelRecommendation1.getId());

        assertEquals(labelRecommendation1, actualLabelRecommendation.get());

        labelRecommendationRepository.deleteById(labelRecommendation1.getId());
        actualLabelRecommendation = labelRecommendationRepository.findById(labelRecommendation1.getId());

        assertFalse(actualLabelRecommendation.isPresent());
    }

    @Test
    public void updateLabelRecommendation() {
        labelRecommendation1.setLiked(false);
        labelRecommendationRepository.save(labelRecommendation1);
        Optional<LabelRecommendation> actualLabelRecommendation = labelRecommendationRepository.findById(labelRecommendation1.getId());

        assertEquals(actualLabelRecommendation.get(), labelRecommendation1);
    }

    @Test
    public void getAllLabelRecommendations() {
        expectedLabelRecommendationList.add(labelRecommendation1);
        expectedLabelRecommendationList.add(labelRecommendation2);

        List<LabelRecommendation> actualLabelList = labelRecommendationRepository.findAll();

        assertEquals(actualLabelList.size(), 2);
        assertEquals(expectedLabelRecommendationList, actualLabelList);
    }

}