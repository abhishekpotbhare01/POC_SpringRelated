package com.demo.sample_spring_boot.config;

import com.demo.sample_spring_boot.model.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        return person;
    }
}