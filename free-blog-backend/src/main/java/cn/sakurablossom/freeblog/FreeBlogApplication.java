package cn.sakurablossom.freeblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FreeBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreeBlogApplication.class, args);
	}

}
