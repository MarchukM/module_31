package com.springboottest.rest;

import com.springboottest.model.Person;
import com.springboottest.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> findById(@PathVariable Long personId) {
        return personService.findById(personId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping("/{personId}")
    public Person updatePerson(@PathVariable Long personId, @RequestBody Person person) {
        return personService.update(personId, person);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long personId) {
        personService.delete(personId);
        return ResponseEntity.noContent().build();
    }
}
