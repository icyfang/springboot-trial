package com.example.demo;

import com.example.demo.bean.BookBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.context.ConfigurableApplicationContext;

@EnableSwagger2Doc
@SpringBootApplication
@EnableConfigurationProperties(BookBean.class)
public class DemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
