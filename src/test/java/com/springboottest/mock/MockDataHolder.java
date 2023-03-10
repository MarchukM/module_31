package com.springboottest.mock;

import com.springboottest.model.Person;

import java.util.Arrays;
import java.util.List;

public class MockDataHolder {

    public static final long PERSON_ID = 1L;
    public static final int NUM_OF_PERSONS = 5;


    private MockDataHolder() {
    }

    public static Person getDefaultPerson() {
        Person person = new Person();
        person.setFirstName("RepoTest");
        person.setLastName("RepoTest");
        person.setEmail("RepoTest@email.com");
        return person;
    }

    public static List<Person> getDefaultPersons() {
        Person person1 = new Person();
        person1.setFirstName("RepoTest1");
        person1.setLastName("RepoTest1");
        person1.setEmail("RepoTest1@email.com");

        Person person2 = new Person();
        person2.setFirstName("RepoTest2");
        person2.setLastName("RepoTest2");
        person2.setEmail("RepoTest2@email.com");

        Person person3 = new Person();
        person3.setFirstName("RepoTest3");
        person3.setLastName("RepoTest3");
        person3.setEmail("RepoTest3@email.com");

        return Arrays.asList(person1, person2, person3);
    }



}
