package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exception.InvalidRequestException;
import com.company.musicstorecatalog.exception.NoRecordFoundException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody Label label) {
        return repo.save(label);
    }

    @RequestMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable Integer id) {
        Optional<Label> optionalLabel = repo.findById(id);
        if (!optionalLabel.isPresent()) {
            throw new NoRecordFoundException("Label with id " + id + " does not exist.");
        }
        return optionalLabel.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable Integer id, @RequestBody Label label) {
        if (label.getId() == 0) {
            label.setId(id);
        }
        if (label.getId() != id) {
            throw new InvalidRequestException("id in request body must match id in path");
        }
        repo.save(label);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Integer id) {
        repo.deleteById(id);
    }


}

