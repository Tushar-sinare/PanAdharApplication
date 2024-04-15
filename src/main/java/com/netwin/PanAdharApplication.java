package com.netwin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.netwin")
public class PanAdharApplication {

	public static void main(String[] args) {
		SpringApplication.run(PanAdharApplication.class, args);
	}

}
