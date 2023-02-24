package com.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Customer;
import com.app.model.User;

@SpringBootApplication
@RestController
@RequestMapping("/kafka")
public class KafkaConsumerApplication {

	List<String> msgs = new ArrayList<>();
	public User userFromTopic = null;
	
	public Customer customerFromTopic = null;

//	@KafkaListener(groupId = "group11", topics = "topic1", containerFactory = "kafkaListnerFactory")
//	public List<String> getMsgFromTopic(String data) {
//		msgs.add(data);
//	//	System.out.println("consume data " + data);
//		return msgs;
//
//	}

//	@GetMapping("/consumeStringMsg")
//	public List<String> consumeMessage() {
//		return msgs;
//
//	}

	@KafkaListener(groupId = "group22", topics = "topic1", containerFactory = "userKafkaListnerFactory")
	public User consumeJson(User user) {

		userFromTopic = user;

		System.out.println("consume data " + userFromTopic.toString());
		return userFromTopic;

	}

	@GetMapping("/consumeJson")
	public User consumeJson() {
		return userFromTopic;

	}
	
//	@KafkaListener(groupId = "group33", topics = "topic1", containerFactory = "customerKafkaListnerFactory")
//	public User consumeJson(Customer customer) {
//
//		customerFromTopic = customer;
//
//		//System.out.println("consume data " + customerFromTopic.toString());
//		return userFromTopic;
//
//	}
//	
//	@GetMapping("/consumeCustomerJson")
//	public Customer consumeCustomerJson() {
//		return customerFromTopic;
//
//	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
