package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@SpringBootApplication
public class PaiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaiBackendApplication.class, args);
	}

}

