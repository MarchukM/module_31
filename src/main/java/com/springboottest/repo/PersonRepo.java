package com.springboottest.repo;

import com.springboottest.model.Person;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends ListCrudRepository<Person, Long> {
}
