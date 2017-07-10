package com.neusoft.hs.platform.mq;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.neusoft.hs.platform.mq.model.Bar;
import com.neusoft.hs.platform.mq.model.Foo;
import com.neusoft.hs.platform.mq.sender.SenderService;

@SpringBootApplication
@ComponentScan(basePackages = "com.neusoft.hs.platform.mq")
public class RabbitmqdemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqdemoApplication.class, args);
	}

	@Autowired
	SenderService senderService;

	@Override
	public void run(String... strings) throws Exception {
		Random random = new Random();
		while (true){
			senderService.sendBar2Rabbitmq(new Bar(random.nextInt()));
			senderService.sendFoo2Rabbitmq(new Foo(UUID.randomUUID().toString()));
		}
	}
}
