package com.toplabs.bazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.TopLabBazaar2909.TLBnew2909")
@EnableJpaRepositories(basePackages = "com.TopLabBazaar2909.TLBnew2909")
@SpringBootApplication(scanBasePackages = "com.TopLabBazaar2909.TLBnew2909")
public class TlBnew2909Application {

	public static void main(String[] args) {
		SpringApplication.run(TlBnew2909Application.class, args);
	}

}
