package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exception.InvalidRequestException;
import com.company.musicstorerecommendations.exception.NoRecordFoundException;
import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumRecommendation")
public class AlbumRecommendationController {
    @Autowired
    private AlbumRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendations() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbum(@RequestBody AlbumRecommendation albumRecommendation) {
        return repo.save(albumRecommendation);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable int id) {
        Optional<AlbumRecommendation> optionalAlbumRecommendation = repo.findById(id);
        if (!optionalAlbumRecommendation.isPresent()) {
            throw new NoRecordFoundException("Album Recommendation id " + id + " not found.");
        }
        return optionalAlbumRecommendation.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@PathVariable int id, @RequestBody AlbumRecommendation albumRecommendation) {
        if (albumRecommendation.getId() == 0) {
            albumRecommendation.setId(id);
        }
        if (albumRecommendation.getId() != id) {
            throw new InvalidRequestException("id in request body must match id in path");
        }
        repo.save(albumRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }


}
