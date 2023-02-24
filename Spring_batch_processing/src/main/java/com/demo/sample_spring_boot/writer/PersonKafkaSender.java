package com.demo.sample_spring_boot.writer;

import com.demo.sample_spring_boot.model.Person;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
public class PersonKafkaSender implements ItemWriter<Person> {

    @Autowired
    private Sender sender;

    @Override
    @Scheduled(fixedDelay = 5000)
    public void write(List<? extends Person> list) throws Exception {
        sender.send((List<Person>) list);
        System.out.printf("  Message sent to kafka data: {} ", list);
    }
}
