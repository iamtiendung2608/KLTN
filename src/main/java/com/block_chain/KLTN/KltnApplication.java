package com.block_chain.KLTN;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class KltnApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/ICT"));
	}

	public static void main(String[] args) {
		SpringApplication.run(KltnApplication.class, args);
	}

}
