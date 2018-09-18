package cn.anytec.anguan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AnguanApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnguanApplication.class, args);
	}
}
