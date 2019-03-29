package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@EntityScan
@SpringBootApplication
@EnableFeignClients
public class PaiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaiBackendApplication.class, args);

	}

	/**
	 * 文件上传临时路径
	 */
//	@Bean
//	MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		factory.setLocation("/pai-backend");
//		return factory.createMultipartConfig();
//	}

}

