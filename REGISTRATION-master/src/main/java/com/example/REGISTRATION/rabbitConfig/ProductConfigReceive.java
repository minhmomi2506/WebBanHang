//package com.example.REGISTRATION.rabbitConfig;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ProductConfigReceive {
////	@Value("${rabbitmq.queue.product}")
////	public static String QUEUE_NAME1;
////
////	@Value("${rabbitmq.exchange.product}")
////	public static String EXCHANGE_NAME1;
////
////	@Value("${rabbitmq.key.product}")
////	public static String ROUTING_KEY1;
//	
//	public static final String QUEUE_NAME1 = "INSERT_PRODUCT";
//	public static final String EXCHANGE_NAME1 = "EXCHANGE_INSERT_PRODUCT";
//	public static final String ROUTING_KEY1 = "INSERT_KEY_PRODUCT";
//
//	@Bean
//	Queue queue() {
//		return new Queue(QUEUE_NAME1, false);
//	}
//
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange(EXCHANGE_NAME1);
//	}
//
//	@Bean
//	Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY1);
//	}
//
//	@Bean
//	public MessageConverter converter() {
//		return new Jackson2JsonMessageConverter();
//	}
//
//	@Bean
//	public AmqpTemplate template(ConnectionFactory connectionFactory) {
//		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(converter());
//		return rabbitTemplate;
//	}
//}
