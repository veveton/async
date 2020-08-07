package br.com.surubim.estudo.geracaolog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.surubim.endpoint")
@EntityScan("br.com.surubim.model")
@EnableJpaRepositories("br.com.surubim.repositorio")
@EnableAsync
public class GeracaoLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeracaoLogApplication.class, args);
	}

}
