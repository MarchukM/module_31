package com.springboottest.service;

import com.springboottest.model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> findAll();
    Optional<Person> findById(Long id);
    Person create(Person person);
    Person update(Long personId, Person person);
    void delete(Long personId);
}
