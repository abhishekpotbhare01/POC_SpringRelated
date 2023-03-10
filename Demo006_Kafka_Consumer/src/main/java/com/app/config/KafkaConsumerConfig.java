package com.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.app.model.Customer;
import com.app.model.User;

@Configuration
public class KafkaConsumerConfig {

//	@Bean
//	public ConsumerFactory<String, String> consumerFactory() {
//
//		Map<String, Object> config = new HashMap<>();
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group11");
//
//		return new DefaultKafkaConsumerFactory<>(config);
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListnerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		return factory;
//	}
	
	
	
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory() {

		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group22");

		return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new JsonDeserializer<>(User.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListnerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}
	
	
//	@Bean
//	public ConsumerFactory<String, Customer> customerConsumerFactory() {
//
//		Map<String, Object> config = new HashMap<>();
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group33");
//
//		return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new JsonDeserializer<>(Customer.class));
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, Customer> customerKafkaListnerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(customerConsumerFactory());
//		return factory;
//	}

}
