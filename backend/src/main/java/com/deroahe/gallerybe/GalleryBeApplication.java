package com.deroahe.gallerybe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GalleryBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalleryBeApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner setup(UserService userService) {
//		return (args) -> {
//			log.info("Generating sample data");
//			userService.deleteAllUsers();
//			List<String> users = Arrays.asList("Bob", "Peter", "Gus", "John", "David");
//			users.forEach(user ->
//					userService.saveUser(User.builder()
//							.username(user)
//							.password(LocalDateTime.now().toString())
//							.build()) );
//
//			userService.getAllUsers().forEach(user ->
//					log.info("USER --> " + user.getUsername() + " ID: " + user.getId() + " PASSWORD: " + user.getPassword()));
//		};
//	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
