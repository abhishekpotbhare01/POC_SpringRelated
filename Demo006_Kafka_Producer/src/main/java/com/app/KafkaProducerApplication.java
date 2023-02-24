package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Customer;
import com.app.model.User;

@SpringBootApplication
@RestController
@RequestMapping("/kafka")
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}
	
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name) {
		
		kafkaTemplate.send("topic1", name);
		return "data published";
	}
	
	@PostMapping("/publishJson")
	public String publishJson(@RequestBody User user) {
		//User user=new User(100, "ABhishek", "Bengaluru");
		
		kafkaTemplate.send("topic1",user);
		return " json data published";
	}
	
	@GetMapping("/publishCustomerJson")
	public String publishCustomerJson() {
		Customer customer=new Customer(100, "nilish", "chennai");
		
		kafkaTemplate.send("topic1",customer);
		return "customer json data published";
	}


}
