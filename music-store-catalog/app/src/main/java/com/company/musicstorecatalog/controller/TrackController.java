package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.InvalidRequestException;
import com.company.musicstorecatalog.exception.NoRecordFoundException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@RequestBody Track track) {
        return repo.save(track);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrackById(@PathVariable Integer id) {
        Optional<Track> optionalTrack = repo.findById(id);
        if (!optionalTrack.isPresent()) {
            throw new NoRecordFoundException("Track id " + id + " not found.");
        }
        return optionalTrack.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@PathVariable Integer id, @RequestBody Track track) {
        if (track.getId() == 0) {
            track.setId(id);
        }
        if (track.getId() != id) {
            throw new InvalidRequestException("id in request body must match id in path");
        }
        repo.save(track);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
