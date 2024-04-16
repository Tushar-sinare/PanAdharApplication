package com.netwin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netwin.util.AESExample;
import com.netwin.util.PnNetwinDecrypt;


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
