package com.springboottest.service;

import com.springboottest.exception.PersonNotFoundException;
import com.springboottest.model.Person;
import com.springboottest.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepo personRepo;

    @Override
    public List<Person> findAll() {
        return personRepo.findAll();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepo.findById(id);
    }

    @Override
    public Person create(Person person) {
        return personRepo.save(person);
    }

    @Override
    public Person update(Long personId, Person person) {
        Person toUpdate = personRepo.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not exists with id " + personId));

        toUpdate.setFirstName(person.getFirstName());
        toUpdate.setLastName(person.getLastName());
        toUpdate.setEmail(person.getEmail());

        return personRepo.save(person);
    }

    @Override
    public void delete(Long personId) {
        Person toDelete = personRepo.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person not exists with id " + personId));

        personRepo.delete(toDelete);
    }
}
