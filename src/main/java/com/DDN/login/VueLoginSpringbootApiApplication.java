package com.DDN.login;

import com.DDN.login.config.StorageProperties;


import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(StorageProperties.class)
public class VueLoginSpringbootApiApplication {

	@Value("${cloudinary.cloud_name}")
	private String cloudName;

	@Value("${cloudinary.api_key}")
	private String apiKey;

	@Value("${cloudinary.api_secret}")
	private String apiSecret;

	public static void main(String[] args) {
		SpringApplication.run(VueLoginSpringbootApiApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", cloudName);
		config.put("api_key", apiKey);
		config.put("api_secret", apiSecret);
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	// @Autowired
	// private EmailSenderService senderService;
	//
	// @EventListener(ApplicationReadyEvent.class)
	// public void triggerMail() throws MessagingException {
	// senderService.sendSimpleEmail("duynghia10099@gmail.com",
	// "This is email body",
	// "This is email subject");
	//
	// }

}
