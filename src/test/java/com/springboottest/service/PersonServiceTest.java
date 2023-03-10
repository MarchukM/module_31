package com.springboottest.service;

import com.springboottest.exception.PersonNotFoundException;
import com.springboottest.model.Person;
import com.springboottest.repo.PersonRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.springboottest.mock.MockDataHolder.PERSON_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepo personRepo;

    @Mock
    private Person person;

    @Mock
    private List<Person> persons;

    @InjectMocks
    private PersonService personService;

    @Test
    public void shouldFindAll() {
        when(personRepo.findAll()).thenReturn(persons);

        var actual = personService.findAll();

        assertThat(actual, notNullValue());
        assertThat(actual, equalTo(persons));
    }

    @Test
    public void shouldFindById() {
        when(personRepo.findById(any())).thenReturn(Optional.of(person));
        
        var actual = personService.findById(PERSON_ID);

        assertThat(actual, notNullValue());
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get(), CoreMatchers.equalTo(person));
    }

    @Test
    public void shouldCreate() {
        when(personRepo.save(any(Person.class))).thenReturn(person);

        var actual = personService.create(new Person());

        assertThat(actual, notNullValue());
        assertThat(actual, equalTo(person));
    }

    @Test
    public void shouldUpdate() {
        when(personRepo.findById(any())).thenReturn(Optional.of(person));
        when(personRepo.save(any(Person.class))).thenReturn(person);

        var actual = personService.update(PERSON_ID, new Person());

        assertThat(actual, notNullValue());
        assertThat(actual, equalTo(person));
    }

    @Test
    public void shouldThrowOnUpdate() {
        when(personRepo.findById(any())).thenReturn(Optional.empty());
        PersonNotFoundException thrown = assertThrows(
                PersonNotFoundException.class,
                () -> personService.update(PERSON_ID, new Person()));

        assertThat(thrown, notNullValue());
    }
}