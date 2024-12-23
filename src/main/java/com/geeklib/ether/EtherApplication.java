package com.geeklib.ether;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;



@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class EtherApplication {

	public static void main(String[] args) throws SQLException {
		
		SpringApplication.run(EtherApplication.class, args);
	}

}
