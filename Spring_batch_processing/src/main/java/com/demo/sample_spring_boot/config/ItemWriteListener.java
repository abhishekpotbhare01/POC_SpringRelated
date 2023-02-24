package com.demo.sample_spring_boot.config;

import com.demo.sample_spring_boot.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;



@Component
public class ItemWriteListener implements org.springframework.batch.core.ItemWriteListener<Person> {
    @Override
    public void beforeWrite(List list) {
        System.out.println(" Data fetch from csv "+list.size());

    }

    @Override
    public void afterWrite(List list) {
        System.out.println(" Data added to db "+list.size());
    }

    @Override
    public void onWriteError(Exception e, List list) {

    }
}