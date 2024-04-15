package com.netwin.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {


@Bean
AESExample aesExample() {
	return new AESExample();
}

@Bean 
PnNetwinDecrypt pnNetwinDecrypt() {
	return new PnNetwinDecrypt();
	
}

}
