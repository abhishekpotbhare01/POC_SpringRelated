package com.demo.sample_spring_boot.writer;

import com.demo.sample_spring_boot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sender {

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    public void send(List<Person> personList) {
        for (Person person1 : personList
        ) {
            kafkaTemplate.send("person-sender", person1);
        }

    }
}