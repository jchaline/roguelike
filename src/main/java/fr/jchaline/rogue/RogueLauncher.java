package fr.jchaline.rogue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import fr.jchaline.rogue.service.FactoryService;

@SpringBootApplication
public class RogueLauncher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RogueLauncher.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(RogueLauncher.class, args);
		
		FactoryService factory = ctx.getBean(FactoryService.class);
		factory.generateData();

		LOGGER.info("RogueLauncher Start successfull, wait for http layer ...");
	}
}
