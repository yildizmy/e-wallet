package com.github.yildizmy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j(topic = "EWalletApplication")
@SpringBootApplication
public class EWalletApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EWalletApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = (env.getProperty("server.ssl.key-store") != null) ? "https" : "http";
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path", "/");
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info("\n" +
				"-------------------------------------------------------\n" +
				"Application '" + env.getProperty("spring.application.name") + "' is running! Access URLs:\n" +
				"Local:      " + protocol + "://localhost:" + serverPort + contextPath + "\n" +
				"External:   " + protocol + "://" + hostAddress + ":" + serverPort + contextPath + "\n" +
				"Profile(s): " + String.join(",", env.getActiveProfiles()) + "\n" +
				"-------------------------------------------------------");
	}
}
