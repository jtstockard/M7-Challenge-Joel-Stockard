package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.InvalidRequestException;
import com.company.musicstorecatalog.exception.NoRecordFoundException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@RequestBody Artist artist) {
        return repo.save(artist);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable Integer id) {
        Optional<Artist> optionalArtist = repo.findById(id);
        if (!optionalArtist.isPresent()) {
            throw new NoRecordFoundException("Artist with id " + id + " does not exist.");
        }
        return optionalArtist.get();
        }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@PathVariable Integer id, @RequestBody Artist artist) {
        if (artist.getId() == 0) {
            artist.setId(id);
        }
        if (artist.getId() != id) {
            throw new InvalidRequestException("id in request body must match id in path");
        }
        repo.save(artist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Integer id) {
        repo.deleteById(id);
    }

}

