package app.backend.configserver;

import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
			.directory("./")
			.load();

		String base64Key = dotenv.get("GIT_PRIVATE_KEY");

		String privateKey = new String(Base64.getDecoder().decode(base64Key));

		System.setProperty("GIT_PRIVATE_KEY", privateKey);

		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
