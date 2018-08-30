package fr.jchaline.roguelike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RogueLauncher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RogueLauncher.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(RogueLauncher.class, args);
		
		LOGGER.info("RogueLauncher Start successfull, wait for http layer ...");
	}
}
