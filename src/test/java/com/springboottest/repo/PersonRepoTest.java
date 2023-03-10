package com.springboottest.repo;

import com.springboottest.exception.PersonNotFoundException;
import com.springboottest.mock.MockDataHolder;
import com.springboottest.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.springboottest.mock.MockDataHolder.NUM_OF_PERSONS;
import static com.springboottest.mock.MockDataHolder.PERSON_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PersonRepoTest {

    private Person toCreate;
    private Person created;

    @Autowired
    private PersonRepo personRepo;

    @BeforeEach
    public void init() {
        toCreate = MockDataHolder.getDefaultPerson();
    }

    @Test
    public void shouldCreateNewPerson() {
        created = personRepo.save(toCreate);

        List<Person> allPersons = personRepo.findAll();

        assertThat(allPersons, notNullValue());
        assertThat(allPersons, hasItem(created));
        assertThat(created.getFirstName(), equalTo(toCreate.getFirstName()));
        assertThat(created.getLastName(), equalTo(toCreate.getLastName()));
    }

    @Test
    public void shouldFindAll() {
        created = personRepo.save(toCreate);

        List<Person> allPersons = personRepo.findAll();

        assertThat(allPersons, notNullValue());
        assertThat(allPersons, hasItem(created));
        assertThat(allPersons, hasSize(NUM_OF_PERSONS + 1));
    }

    @Test
    public void shouldDelete() {
        Person toDelete = personRepo.findById(PERSON_ID).orElseThrow(() -> new PersonNotFoundException("test"));
        personRepo.delete(toDelete);

        List<Person> allPersons = personRepo.findAll();

        assertThat(allPersons, notNullValue());
        assertThat(allPersons, hasSize(NUM_OF_PERSONS - 1));
    }
}