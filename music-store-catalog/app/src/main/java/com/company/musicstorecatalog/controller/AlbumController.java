package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.InvalidRequestException;
import com.company.musicstorecatalog.exception.NoRecordFoundException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private AlbumRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@RequestBody Album album) {
        return repo.save(album);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable int id) {
        Optional<Album> optionalAlbum = repo.findById(id);
        if (!optionalAlbum.isPresent()) {
            throw new NoRecordFoundException("Album id " + id + " not found.");
        }
        return optionalAlbum.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@PathVariable int id, @RequestBody Album album) {
        if (album.getId() == 0) {
            album.setId(id);
        }
        if (album.getId() != id) {
            throw new InvalidRequestException("id in request body must match id in path");
        }
        repo.save(album);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable int id) {
        repo.deleteById(id);
    }
}
