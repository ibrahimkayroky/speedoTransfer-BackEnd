package com.example.speedoTransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class SpeedoTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeedoTransferApplication.class, args);
	}

}
