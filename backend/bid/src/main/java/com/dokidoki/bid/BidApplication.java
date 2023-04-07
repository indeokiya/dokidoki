package com.dokidoki.bid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BidApplication {
	public static void main(String[] args) {
		SpringApplication.run(BidApplication.class, args);
	}

}
